$(document).ready(function() {
    let allVehiclesData = [];
    let opId; // Declare opId here

    // Function to fetch and log data from localStorage
    function fetchLocalStorageData() {
        const operatorId = localStorage.getItem('operatorId');
        const email = localStorage.getItem('email');
        const password = localStorage.getItem('password');

        console.log("Operator ID:", operatorId);
        console.log("Email:", email);
        console.log("Password:", password);
        
        return operatorId; // Return the operatorId to be used later
    }

    // Function to update current time on the page
    function updateTime() {
        const now = new Date();
        const date = now.toLocaleDateString();
        const time = now.toLocaleTimeString();

        $('#currentDate').text('Date: ' + date);
        $('#currentTime').text(' ' + time);
    }

    // Function to start countdown timer
    function startCountdown() {
        var duration = 1 * 60; // 1 minute for testing (adjust as needed)
        var timer = duration, hours, minutes, seconds;
        var countdownInterval = setInterval(function() {
            hours = parseInt(timer / 3600, 10);
            minutes = parseInt((timer % 3600) / 60, 10);
            seconds = parseInt(timer % 60, 10);

            hours = hours < 10 ? "0" + hours : hours;
            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            $('#timer').text(hours + ":" + minutes + ":" + seconds);

            if (--timer < 0) {
                clearInterval(countdownInterval);
                calculateTotalAmount(allVehiclesData); // Calculate total amount using all vehicle data
                window.location.href = '/dailyCollection'; // Redirect after countdown
            }
        }, 1000);
    }

    // Function to load vehicle types into dropdown
    function loadVehicleTypes() {
        $.ajax({
            url: '/vehicleTypes',
            type: 'GET',
            success: function(response) {
                var vehicleTypeDropdown = $('#vehicleType');
                vehicleTypeDropdown.empty();
                response.forEach(function(vehicleType) {
                    vehicleTypeDropdown.append(
                        $('<option></option>').val(vehicleType).text(vehicleType)
                    );
                });
            },
            error: function(e) {
                console.error('Error fetching vehicle types:', e);
            }
        });
    }

    $('#returnType').change(function() {
        var vehicleType = $('#vehicleType').val();
        var returnType = $(this).val();

        $.ajax({
            url: '/getAmount',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify({ vehicleType: vehicleType, returnType: returnType }),
            success: function(response) {
                $('#amount').val(response.amount);
                $('#amountContainer').text('Amount: ' + response.amount);
            },
            error: function(e) {
                console.error('Error fetching amount:', e);
            }
        });
    });

    // Function to load vehicles based on operatorId
    function loadVehicles(operatorId) {
        $.ajax({
            url: '/getTodayVehiclesByOperator/' + operatorId,
            type: 'GET',
            success: function(response) {
                allVehiclesData = response; // Store the response data for later use
                var recentVehicles = response.slice(-10).reverse();
                var rowCount = recentVehicles.length;
                $('#vehicleTableBody').empty();
                recentVehicles.forEach(function(vehicle, index) {
                    var srNo = rowCount - index;
                    $('#vehicleTableBody').append(
                        '<tr>' +
                        '<td>' + srNo + '</td>' +
                        '<td>' + vehicle.date + '</td>' +
                        '<td>' + vehicle.vehicleNumber + '</td>' +
                        '<td>' + vehicle.vehicleType + '</td>' +
                        '<td>' + vehicle.returnType + '</td>' +
                        '<td>' + vehicle.amount + '</td>' +
                        '</tr>'
                    );
                });
            },
            error: function(e) {
                console.error('Error loading vehicles:', e);
            }
        });
    }

    // Function to update form data
    function updateFormData() {
        const formData = {
            operatorId: $('#operatorId').val(),
            vehicleNumber: $('#vehicleNumber').val(),
            vehicleType: $('#vehicleType').val(),
            returnType: $('#returnType').val(),
            amount: $('#amount').val()
        };

        $.ajax({
            url: '/saveVehicleData',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log('Vehicle data saved successfully:', response);
                loadVehicles(formData.operatorId); // Reload the vehicles after saving
                resetForm(); // Clear the form for new data entry
                showModal(response); // Show confirmation modal with receipt info
            },
            error: function(e) {
                console.error('Error saving vehicle data:', e);
            }
        });
    }

    // Function to reset the form
    function resetForm() {
        $('#vehicleNumber').val('');
        $('#vehicleType').val('');
        $('#returnType').val('');
        $('#amount').val('');
    }

    // Function to show confirmation modal with receipt info
    function showModal(vehicleData) {
        $('#modalVehicleNumber').text(vehicleData.vehicleNumber);
        $('#modalVehicleType').text(vehicleData.vehicleType);
        $('#modalReturnType').text(vehicleData.returnType);
        $('#modalAmount').text(vehicleData.amount);
        $('#vehicleModal').modal('show');
    }

    // Form submission event
    $('#vehicleForm').submit(function(e) {
        e.preventDefault(); // Prevent the default form submission
        updateFormData(); // Call the updateFormData function
    });

    // Initialize the page
    opId = fetchLocalStorageData();
    if (opId) {
        loadVehicles(opId); // Load vehicles for the current operator
        loadVehicleTypes(); // Load vehicle types into dropdown
        startCountdown(); // Start countdown timer
    }

    setInterval(updateTime, 1000); // Update time every second
    updateTime(); // Initial call to set the time

   // Function to calculate total amount from table
function calculateTotalAmount(vehicles) {
    var totalAmount = 0;
    vehicles.forEach(function(vehicle) {
        var amount = parseFloat(vehicle.amount);
        totalAmount += isNaN(amount) ? 0 : amount;
    });
    
    // Store the total amount in localStorage
    localStorage.setItem('totalAmount', totalAmount.toFixed(2));
    
    // Update the total amount on the page
    $('#totalAmount').text(totalAmount.toFixed(2));
}


    // Function to handle vehicle form submission
    $('#vehicleForm').submit(function(event) {
        event.preventDefault();

        var vehicleNumber = $('#vehicleNumber').val();
        var vehicleType = $('#vehicleType').val();
        var returnType = $('#returnType').val();
        var amount = $('#amount').val();

        $('#modalVehicleNumber').text(vehicleNumber);
        $('#modalVehicleType').text(vehicleType);
        $('#modalReturnType').text(returnType);
        $('#modalAmount').text(amount);

        $('#vehicleModal').modal('show');
    });

    // Handle the modal show event
    $('#vehicleModal').on('shown.bs.modal', function() {
        opId = fetchLocalStorageData(); // Fetch and store the operatorId when the modal is shown

        $(document).on('keydown', function(event) {
            if (event.key === 'Enter') {
                event.preventDefault();
                resetForm();
                addDataToDatabase(opId); // Pass operatorId to addDataToDatabase
            }
        });
    });

    // Clean up the event when the modal is hidden
    $('#vehicleModal').on('hidden.bs.modal', function() {
        $(document).off('keydown'); // Remove the keydown event listener
    });

    // Function to add data to the database
    function addDataToDatabase(opId) { // Accept opId as a parameter
        const vehicleNumber = $('#modalVehicleNumber').text();
        const vehicleType = $('#modalVehicleType').text();
        const returnType = $('#modalReturnType').text();
        const amount = $('#modalAmount').text();

        // Get the current date and format it
        var date = new Date();
        var formattedDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);

        // Example AJAX request to add data (modify according to your backend API)
        $.ajax({
            url: '/saveVehicle/' + opId, // Use opId in the URL
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                vehicleNumber: vehicleNumber,
                vehicleType: vehicleType,
                returnType: returnType,
                amount: amount,
                date: formattedDate // Include the formatted date
            }),
            success: function(response) {
                // Handle success response
                console.log('Data added successfully:', response);
                $('#vehicleModal').modal('hide'); // Optionally hide the modal after success
                loadVehicles(opId); // Call loadVehicles here to refresh the vehicle list
            },
            error: function(xhr, status, error) {
                // Handle error response
                console.error('Error adding data:', error);
            }
        });
    }

    // Function to load table data
    function loadTableData() {
        $.ajax({
            url: '/getRecentVehicles',
            type: 'GET',
            success: function(response) {
                // Clear existing table rows
                $('#vehicleTableBody').empty();

                // Populate table with new data
                response.forEach(function(vehicle, index) {
                    $('#vehicleTableBody').append(
                        '<tr>' +
                        '<td>' + (index + 1) + '</td>' +
                        '<td>' + vehicle.date + '</td>' +
                        '<td>' + vehicle.vehicleNumber + '</td>' +
                        '<td>' + vehicle.vehicleType + '</td>' +
                        '<td>' + vehicle.returnType + '</td>' +
                        '<td>' + vehicle.amount + '</td>' +
                        '</tr>'
                    );
                });

                // Calculate total amount from the table
                var totalAmount = response.reduce(function(acc, vehicle) {
                    return acc + parseFloat(vehicle.amount);
                }, 0);

                // Display total amount in the table footer
                $('#totalAmount').text(totalAmount.toFixed(2));
            },
            error: function(xhr, status, error) {
                console.error('Error loading table data:', error);
            }
        });
    }
});

//shortcut keys

$(document).ready(function() {
    // Other code ...

    // Listen for keypress events
    $(document).keydown(function(event) {
        // Check which key was pressed
        if (event.key === 's' || event.key === 'S') {
            // Select 'Single' in the returnType dropdown
            $('#returnType').val('single').trigger('change');
        } else if (event.key === 'r' || event.key === 'R') {
            // Select 'Return' in the returnType dropdown
            $('#returnType').val('return').trigger('change');
        }
    });

   
});





/*// Handle click event for Finish button
$('#finishButton').click(function() {
    window.location.href = '/dailyCollection';
});
*/


$('#finishButton').click(function() {
    $(this).prop('disabled', true);
    // Your submit logic here
    setTimeout(function() {
		 window.location.href = '/dailyCollection';
        $('#finishButton').prop('disabled', false);
    }, 3000);  // Adjust the timeout as needed
});


