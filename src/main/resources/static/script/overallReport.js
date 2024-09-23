$(document).ready(function() {
    $("#sendButton").click(function() {
        var operatorId = $("#operatorId").val();

        if (!operatorId) {
            alert("Please select an operator ID");
            return;
        }

        console.log("Sending request for Operator ID: " + operatorId);

        $.ajax({
            url: "/login-details/" + operatorId,
            type: "GET",
            success: function(data, status, xhr) {
                console.log("Request successful, status: " + status);
                console.log("Received data: ", data);

                if (data && data.length > 0) {
                    var tableBody = $("#operatorReport");
                    tableBody.empty();  // Clear previous data

                    data.forEach(function(item, index) {
                        var row = "<tr>";
                        row += "<td>" + (index + 1) + "</td>"; // Add Sr No
                        row += "<td>" + item.loginOperatorId + "</td>";
                        row += "<td>" + item.loginOperatorName + "</td>";
                        row += "<td>" + item.loginDate + "</td>";
                        row += "<td>" + item.loginOperatorBooth + "</td>";
                        row += "<td>" + item.returnType + "</td>"; // Ensure returnType is fetched
                        row += "</tr>";
                        tableBody.append(row);
                    });
                } else {
                    alert("No data found for the selected operator.");
                }
            },
            error: function(xhr, status, error) {
                console.log("Error status: " + status);
                console.log("XHR status code: " + xhr.status);
                console.log("Error details: " + error);
                alert("Failed to fetch data. Please check the console for details.");
            }
        });
    });
    
    
     // Handle Date selection
    $("#sendDateButton").click(function() {
        var selectedDate = $("#date").val();

        if (!selectedDate) {
            alert("Please select a date");
            return;
        }

        console.log("Sending request for Date: " + selectedDate);

        $.ajax({
            url: "/login-details/date/" + selectedDate, // Update this URL to match your endpoint
            type: "GET",
            success: function(data, status, xhr) {
                console.log("Request successful for date, status: " + status);
                console.log("Received data for date: ", data);
                populateTable(data);
            },
            error: function(xhr, status, error) {
                console.log("Error status: " + status);
                alert("Failed to fetch data. Please check the console for details.");
            }
        });
    });
    
    // Function to populate the report table
    function populateTable(data) {
        var tableBody = $("#operatorReport");
        tableBody.empty();  // Clear previous data

        if (data && data.length > 0) {
            data.forEach(function(item, index) {
                var row = "<tr>";
                row += "<td>" + (index + 1) + "</td>"; // Add Sr No
                row += "<td>" + item.loginOperatorId + "</td>";
                row += "<td>" + item.loginOperatorName + "</td>";
                row += "<td>" + item.loginDate + "</td>";
                row += "<td>" + item.loginOperatorBooth + "</td>";
                row += "<td>" + item.returnType + "</td>"; // Ensure returnType is fetched
                row += "</tr>";
                tableBody.append(row);
            });
        } else {
            alert("No data found for the selected date.");
        }
    }
});




