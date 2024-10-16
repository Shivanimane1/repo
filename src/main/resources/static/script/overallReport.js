$(document).ready(function() {
	$('#sendButton').click(function() {
		const operatorId = $('#operatorId').val();
		const date = $('#date').val();
		const boothNo = $('#booth').val();
		const returnType = $('#returnType').val();

		console.log("Operator ID: ", operatorId);
		console.log("Date is: ", date);
		console.log("Booth No: ", boothNo);
		console.log("Return Type: ", returnType);

		let url = '';
		let params = [];

		// Check which fields are selected and construct the query URL
		if(operatorId && date && boothNo && returnType){
			// If all operator id, date, boothNo and return type  are selected
			url=`/vehiclesByOperatorDateBoothReturnType/${operatorId}/${date}/${boothNo}/${returnType}`;
		}
		else if(operatorId && date && boothNo){
			// If both operator id, date and boothNo  are selected
			url=`/vehiclesByOperatorIdDateAndBooth/${operatorId}/${date}/${boothNo}`;
		}else if(operatorId && date && returnType){
			// If both operator id, date and return type  are selected
			url=`/vehiclesByOperatorIdDateAndReturnType/${operatorId}/${date}/${returnType}`;
		}				
		else if(date && boothNo && returnType){
			// If both date ,boothNo and returnType are selected
			url = `/getVehiclesData/${date}/${boothNo}/${returnType}`;
		}else if(operatorId && boothNo && returnType){
			// If both operator id ,boothNo and returnType are selected
			url=`/vehiclesByOperatorBoothReturnType/${operatorId}/${boothNo}/${returnType}`;
		}
		else if(operatorId && date){
			// If both date and operator id and date  are selected
			url=`/vehiclesByOperatorIdAndDate/${operatorId}/${date}`;
		}
		else if(operatorId && returnType){
			// If both date and operator id and return type  are selected
			url=`/vehiclesByOperatorIdAndReturnType/${operatorId}/${returnType}`
		}
		else if(operatorId && boothNo){
			// If both date and operator idand booth  are selected
			url=`vehiclesByOperatorAndBooth/${operatorId}/${boothNo}`
		}
		else if (date && boothNo) {
			// If both date and boothNo are selected
			url = `/vehiclesByDateAndBooth/${date}/${boothNo}`;
		} else if (date && returnType) {
			// If both date and returnType are selected
			url = `/vehiclesByDateAndReturnType/${date}/${returnType}`;
		} else if (boothNo && returnType) {
			// If both  booth and  returnType are selected
			url = `/vehiclesByBoothAndReturnType/${boothNo}/${returnType}`;
		} else if (operatorId){
			// If only operatorId is selected
			url = `/getVehiclesByOperator/${operatorId}`;

		} else if (date) {
			       $.ajax({
            url: '/vehiclesByDate/' + date,  // Use the correct API
            type: 'GET',
            success: function(response) {
                console.log("Response received:", response); 

                if (response.length === 0) {
                    alert("No data available for the selected date.");
                    return;
                }

                const vehicleData = response;

                // Build table rows dynamically
                let tableBody = '';

                vehicleData.forEach(vehicle => {
                    tableBody += `<tr>
                        <td>${vehicle.vehicleType}</td>
                        <td>${vehicle.singleRate}</td>
                        <td>${vehicle.singleCount}</td>
                        <td>${vehicle.singleAmount}</td>
                        <td>${vehicle.returnRate}</td>
                        <td>${vehicle.returnCount}</td>
                        <td>${vehicle.returnAmount}</td>
                    </tr>`;
                });

                // Populate the table with new data
                $('#dailyCollectionTable').html(tableBody);
            },
            error: function(e) {
                console.error('Error fetching report data:', e);
            }
        });     
		} else if (boothNo) {
			// If only boothNo is selected
			url = `/vehiclesByBooth/${boothNo}`;
		} else if (returnType) {
			// If only returnType is selected
			url = `/returnType/${returnType}`;
		}
		
		// Make sure the URL is built correctly before making an AJAX call
		if (url) {
			// Make the AJAX request
			$.ajax({
				url: url,
				type: 'GET',
				success: function(response) {
					console.log("Response received from server:", response);

					if (response.length === 0) {
						alert("No data available for the selected filters.");
						return;
					}

					const vehicleData = response.reduce((acc, vehicle) => {
						const type = vehicle.vehicleType;
						const isReturn = vehicle.returnType === 'return';
						const rate = vehicle.amount;
						//const rate = vehicle.amount ? vehicle.amount : 0;
						
						// Skip null or empty vehicle types
						if (!type || type === 'null') {
							return acc;
						}

						if (!acc[type]) {
							acc[type] = {
								single: { rate: 0, count: 0, amount: 0 },
								return: { rate: 0, count: 0, amount: 0 }
							};
						}


						if (isReturn) {
							acc[type].return.rate = rate;
							acc[type].return.count += vehicle.count || 1;
							//acc[type].return.count++;
							acc[type].return.amount += rate;
						} else {
							acc[type].single.rate = rate;
							acc[type].single.count += vehicle.count || 1;
							//acc[type].single.count++;
							acc[type].single.amount += rate;
						}

						return acc;
					}, {});

					// Build table rows dynamically
					let tableBody = '';
					for (const [type, data] of Object.entries(vehicleData)) {
						tableBody += `<tr>
                            <td>${type}</td>
                            <td>${data.single.rate}</td>
                            <td>${data.single.count}</td>
                            <td>${data.single.amount}</td>
                            <td>${data.return.rate}</td>
                            <td>${data.return.count}</td>
                            <td>${data.return.amount}</td>
                        </tr>`;
					}

					// Populate the table with new data
					$('#dailyCollectionTable').html(tableBody);
				},
				error: function(e) {
					console.error('Error fetching data:', e);
				}
			});
		} else {
			//alert('Please select at least one filter.');
		}
	});
});


/*$(document).ready(function() {
    $('#sendButton').click(function() {
        const operatorId = $('#operatorId').val();
      //  const operatorName = $('#operatorName').val(); // Not used in AJAX but required
        const date = $('#date').val();
        const boothNo = $('#booth').val();
        const returnType = $('#returnType').val();

        console.log("Operator ID: ", operatorId); 
        console.log("Date is: ", date);
        console.log("Booth No: ", boothNo);
        console.log("Return Type: ", returnType);
       
        
        
       
        
        --------------------------------------------------------------------------------------------------------

        // Make AJAX request to get vehicle data by operator ID
        $.ajax({
            url: '/getVehiclesByOperator/' + operatorId,  // Modify this URL as per your API
            type: 'GET',
           
            success: function(response) {
                // Log the full response for debugging
                console.log("Response received from server:", response); 

                if (response.length === 0) {
                    alert("No data available for the selected operator id.");
                    return;
                }

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

                // Build table rows dynamically
                let tableBody = '';
             
                for (const [type, data] of Object.entries(vehicleData)) {
                    tableBody += `<tr>
                        <td>${type}</td>
                        <td>${data.single.rate}</td>
                        <td>${data.single.count}</td>
                        <td>${data.single.amount}</td>
                        <td>${data.return.rate}</td>
                        <td>${data.return.count}</td>
                        <td>${data.return.amount}</td>
                    </tr>`;

                   
                }

                // Populate the table with new data
                $('#dailyCollectionTable').html(tableBody);

              
            },
            error: function(e) {
                // Log the error for debugging
                console.error('Error fetching report data:', e);
            }
        }); 
        
        
        -------------------------------------------------------------------------------------
      
      const today = new Date().toISOString().split('T')[0];  // Get today's date in YYYY-MM-DD format
    $('#date').attr('max', today);
      // Convert the selected date to a Date object
        const selectedDate = new Date(date);
        // Get today's date
        
        // Check if the selected date is in the future
        if (selectedDate > today) {
            alert("You cannot select a future date.");
            return;  // Exit function, do not proceed with AJAX request
        }
      
      
        
         // Make AJAX request to get vehicle data by selected date
        $.ajax({
            url: '/vehiclesByDate/' + date,  // Use the correct API
            type: 'GET',
            success: function(response) {
                console.log("Response received:", response); 

                if (response.length === 0) {
                    alert("No data available for the selected date.");
                    return;
                }

                const vehicleData = response;

                // Build table rows dynamically
                let tableBody = '';

                vehicleData.forEach(vehicle => {
                    tableBody += `<tr>
                        <td>${vehicle.vehicleType}</td>
                        <td>${vehicle.singleRate}</td>
                        <td>${vehicle.singleCount}</td>
                        <td>${vehicle.singleAmount}</td>
                        <td>${vehicle.returnRate}</td>
                        <td>${vehicle.returnCount}</td>
                        <td>${vehicle.returnAmount}</td>
                    </tr>`;
                });

                // Populate the table with new data
                $('#dailyCollectionTable').html(tableBody);
            },
            error: function(e) {
                console.error('Error fetching report data:', e);
            }
        });     
        
        ----------------------------------------------------------------------------------
        
        // Make AJAX request to get vehicle data by boothNo
        $.ajax({
            url: '/vehiclesByBooth/' + boothNo,  // New API URL
            type: 'GET',
            success: function(response) {
                // Log the full response for debugging
                console.log("Response received from server (by Booth No):", response); 

                if (response.length === 0) {
                    alert("No data available for the selected booth.");
                    return;
                }

                // Process the response to build vehicle data
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

                // Build table rows dynamically
                let tableBody = '';
                for (const [type, data] of Object.entries(vehicleData)) {
                    tableBody += `<tr>
                        <td>${type}</td>
                        <td>${data.single.rate}</td>
                        <td>${data.single.count}</td>
                        <td>${data.single.amount}</td>
                        <td>${data.return.rate}</td>
                        <td>${data.return.count}</td>
                        <td>${data.return.amount}</td>
                    </tr>`;
                }

                // Populate the table with new data
                $('#dailyCollectionTable').html(tableBody);
            },
            error: function(e) {
                // Log the error for debugging
                console.error('Error fetching data for booth:', e);
            }
        });

        -----------------------------------------------------------------------
        
          // Make AJAX request to fetch data by returnType
        $.ajax({
            url: '/returnType/' + returnType,  // API endpoint for returnType
            type: 'GET',
            success: function(response) {
                console.log("Response received from server (by Booth No):", response); 

                if (response.length === 0) {
                    alert("No data available for the selected returntype.");
                    return;
                }

                // Process the response to build vehicle data
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

                // Build table rows dynamically
                let tableBody = '';
                for (const [type, data] of Object.entries(vehicleData)) {
                    tableBody += `<tr>
                        <td>${type}</td>
                        <td>${data.single.rate}</td>
                        <td>${data.single.count}</td>
                        <td>${data.single.amount}</td>
                        <td>${data.return.rate}</td>
                        <td>${data.return.count}</td>
                        <td>${data.return.amount}</td>
                    </tr>`;
                }

                // Populate the table with new data
                $('#dailyCollectionTable').html(tableBody);
            },
            error: function(e) {
                console.error('Error fetching data for returnType:', e);
            }
        });
        
       --------------------------------------------------------------------------------------------- 
        
       

        // AJAX request to fetch vehicle data by date and boothNo
         if (date && boothNo) {
        $.ajax({
            url: '/vehiclesByDateAndBooth/' + date + '/' + boothNo,
            method: 'GET',
            success: function (response) {
                // Process and display the response in your table
                console.log(response);
                // Update your table here based on the response data
                $('#dailyCollectionTable').empty();
                response.forEach(function (vehicle) {
                    $('#dailyCollectionTable').append(
                        '<tr>' +
                        '<td>' + vehicle.vehicleType + '</td>' +
                        '<td>' + vehicle.returnType + '</td>' +
                        '<td>' + vehicle.amount + '</td>' +
                        '</tr>'
                    );
                });
            },
            error: function (xhr) {
                console.error('Error fetching data:', xhr);
            }
        });
    } else {
        alert('Please select both date and booth.');
    }

       
       
                       
    });   
});*/