$(document).ready(function() {
    // Function to calculate total manual collection amount
    function calculateTotal() {
        let total = 0;
        $('.multiplier').each(function() {
            const baseValue = parseFloat($(this).data('base'));
            const multiplier = parseFloat($(this).val());
            if (!isNaN(baseValue) && !isNaN(multiplier)) {
                total += baseValue * multiplier;
            }
        });
        $('#total').text(total);
        $('#totalManualAmount').text('Rs: ' + total);
        updateAmounts(); // Update amounts after manual total calculation

        // Save the total to local storage for persistence
        localStorage.setItem('manualAmount', total);
    }

    // Function to display the current date and operator ID
    function displayDateAndOperatorId() {
        const currentDate = new Date().toISOString().split('T')[0];
        const operatorId = localStorage.getItem('operatorId') || 'N/A';

        document.getElementById('currentDate').textContent = currentDate;
        document.getElementById('operatorId').textContent = operatorId;
    }

    // Function to fetch daily collection data
    function getDailyCollection(operatorId) {
        $.ajax({
            url: '/getTodayVehiclesByOperator/' + operatorId,
            type: 'GET',
            success: function(response) {
                console.log(response); // Debugging: log the data to check its structure

                if (response.length === 0) {
                    alert("No data available for the selected operator.");
                    return;
                }

                // Process data to group by vehicle type and calculate totals
                const vehicleData = response.reduce((acc, vehicle) => {
                    const type = vehicle.vehicleType;
                    const isReturn = vehicle.returnType === 'return';
                    const rate = vehicle.amount;

                    if (!acc[type]) {
                        acc[type] = {
                            single: { rate: 0, count: 0, amount: 0 },
                            return: { rate: 0, count: 0, amount: 0 }
                        };
                    }

                    if (isReturn) {
                        acc[type].return.rate = rate;
                        acc[type].return.count++;
                        acc[type].return.amount += rate;
                    } else {
                        acc[type].single.rate = rate;
                        acc[type].single.count++;
                        acc[type].single.amount += rate;
                    }

                    return acc;
                }, {});

                // Build table rows
                let tableBody = '';
                let totalSystemCollectionAmount = 0;
                for (const [type, data] of Object.entries(vehicleData)) {
                    tableBody += '<tr>' +
                        '<td>' + type + '</td>' +
                        '<td>' + data.single.rate + '</td>' +
                        '<td>' + data.single.count + '</td>' +
                        '<td>' + data.single.amount + '</td>' +
                        '<td>' + data.return.rate + '</td>' +
                        '<td>' + data.return.count + '</td>' +
                        '<td>' + data.return.amount + '</td>' +
                        '</tr>';

                    totalSystemCollectionAmount += data.single.amount + data.return.amount;
                }

                $('#dailyCollectionTable').html(tableBody);
                $('#totalSystemCollectionAmount').text('Rs: ' + totalSystemCollectionAmount);
                updateAmounts(); // Update amounts after loading vehicle data
            },
            error: function(e) {
                console.error('Error loading vehicles:', e);
            }
        });
    }

    // Function to calculate and update the short and access amounts
    function updateAmounts() {
        const manualTotal = parseFloat($('#totalManualAmount').text().replace('Rs: ', '')) || 0;
        const systemTotal = parseFloat($('#totalSystemCollectionAmount').text().replace('Rs: ', '')) || 0;
        const shortAmount = Math.max(0, systemTotal - manualTotal);
        const accessAmount = Math.max(0, manualTotal - systemTotal);

        $('#shortAmount').text('Rs: ' + shortAmount.toFixed(2));
        $('#accessAmount').text('Rs: ' + accessAmount.toFixed(2));

        // Save the amounts to local storage for persistence
        localStorage.setItem('shortAmount', shortAmount.toFixed(2));
        localStorage.setItem('accessAmount', accessAmount.toFixed(2));

        // Send the amounts to the backend
        saveCollectionData(manualTotal, shortAmount, accessAmount);
    }

    // Function to save collection data to the backend
    function saveCollectionData(manualTotal, shortAmount, accessAmount) {
        const operatorId = localStorage.getItem('operatorId') || 'N/A';
        const data = {
            totalManualAmount: manualTotal,
            shortAmount: shortAmount,
            accessAmount: accessAmount,
            operatorId: operatorId
        };

        $.ajax({
            url: '/api/saveDailyCollection',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(response) {
                console.log('Data saved successfully:', response);
            },
            error: function(error) {
                console.error('Error saving data:', error);
            }
        });
    }

    // Event listener for multiplier inputs
    $('.multiplier').on('input', function() {
        calculateTotal();
    });

    // Fetch local storage data and initiate the daily collection fetch
    const operatorId = localStorage.getItem('operatorId');
    if (operatorId) {
        displayDateAndOperatorId();
        getDailyCollection(operatorId);

        // Fetch stored amounts from local storage
        const manualAmount = localStorage.getItem('manualAmount') || 0;
        const shortAmount = localStorage.getItem('shortAmount') || 0;
        const accessAmount = localStorage.getItem('accessAmount') || 0;

        $('#totalManualAmount').text('Rs: ' + manualAmount);
        $('#shortAmount').text('Rs: ' + shortAmount);
        $('#accessAmount').text('Rs: ' + accessAmount);
    } else {
        alert("Operator ID not found in local storage.");
    }

    // Handle click event for Next button
    $('#nextButton').click(function() {
        window.location.href = '/receipt';
    });
    
    document.getElementById("nextButton").addEventListener("click", function() {
    this.disabled = true;  // Disable the button
    // Your existing logic to add the data to the table
});

});


