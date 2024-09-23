$(document).ready(function() {
    // Fetch vehicle data on page load
    ajaxGet();

    // Clear form fields on page load
    $("#vehicleType").val("");
    $("#single_amt").val("");
    $("#return_amt").val("");

    // Handle form submission
    $("#form").on("submit", function(event) {
        event.preventDefault(); // Prevent default form submission
        ajaxPost();
    });

    // Function to handle form submission
    function ajaxPost() {
        var formData = {
            vehicleType: $("#vehicleType").val(),
            single_amt: $("#single_amt").val(),
            return_amt: $("#return_amt").val()
        };

        $.ajax({
            type: "POST",
            url: "/save_vehicle",
            data: JSON.stringify(formData),
            contentType: "application/json",
            success: function(response) {
                alert(response);
                // Clear form fields after successful submission
                $("#vehicleType").val("");
                $("#single_amt").val("");
                $("#return_amt").val("");
                // Fetch updated data after successful addition
                ajaxGet();
            },
            error: function(xhr, status, error) {
                alert("Failed to add vehicle details: " + error);
            }
        });
    }

    // Function to fetch and display vehicle data
    function ajaxGet() {
        $.ajax({
            type: "GET",
            url: "/get_vehicles",
            dataType: "json",
            success: function(data) {
                var d = '';
                for (var i = 0; i < data.length; i++) {
                    d += '<tr>' +
                         '<td>' + data[i].id + '</td>' +
                         '<td>' + data[i].vehicleType + '</td>' +
                         '<td>' + data[i].single_amt + '</td>' +
                         '<td>' + data[i].return_amt + '</td>' +
                         '<td><button data-bs-toggle="modal" data-bs-target="#vehicleModal" data-vehicle-id="' + data[i].id + '" id="editBtn-' + data[i].id + '" class="btn-rounded  btn btn-info  mdi mdi-lead-pencil"></button> ' +
                         '<button data-vehicle-id="' + data[i].id + '" id="deleteBtn-' + data[i].id + '" class="btn-rounded  btn btn-danger mdi mdi-delete"></button></td>' +
                         '</tr>';
                }
                $('#table').html(d);
            },
            error: function() {
                alert("Error loading data...");
            }
        });
    }
/********************************************************************************************************************************/

$(document).on('click', '[id^="editBtn-"]', function() {
	alert("do you want to edit ???");
    var vehicleId = $(this).data('vehicle-id');
    $.ajax({
        url: '/get_vehicle/' + vehicleId,
        type: 'GET',
        success: function(data) {
			
			console.log("Vehicle id for editing : ", vehicleId);
            $('#vehicleId').val(data.id);
            $('#vehicleType').val(data.vehicleType);
            $('#single_amt').val(data.single_amt);
            $('#return_amt').val(data.return_amt);
            $('#vehicleModal').modal('show');
        },
        error: function() {
            alert('Error fetching vehicle details.');
        }
    });
});

// Save changes after editing
$('#saveVehicle').click(function() {
    var formData = {
        vehicleType: $('#vehicleType').val(),
        single_amt: $('#single_amt').val(),
        return_amt: $('#return_amt').val()
    };
    var vehicleId = $('#vehicleId').val();
    $.ajax({
        url: '/update_vehicle/' + vehicleId,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(formData),
        success: function(response) {
            alert(response);
            $('#vehicleModal').modal('hide');
            // Optionally, refresh the table to show updated details
        },
        error: function(err) {
            alert('Error updating vehicle.');
        }
    });
});





 /*   // Edit button click handler
    $(document).on('click', '[id^="editBtn-"]', function() {
        let id = $(this).data('vehicle-id');
        
        consle.log("Vehicle id is : "+ id)
        
        
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "/get_vehicle/" + id,
            dataType: 'json',
            success: function(data) {
                if (data) {
					
					console.log("Data is : ", data)
                    // Populate the modal with the fetched data
                    $("#vehicleId").val(data.id);
                    $("#vehicleType").val(data.vehicleType);
                    $("#single_amt").val(data.single_amt);
                    $("#return_amt").val(data.return_amt);
                    
                    // Open the modal
                    $('#vehicleModal').modal('show');
                }
            },
            error: function(e) {
                console.log("Error in fetching data for Id:", e);
            }
        });
    });

    // Update data
    $("#saveVehicle").on('click', function(e) {
        e.preventDefault();

        let id = $("#vehicleId").val();
        let vehicleType = $("#vehicleType").val();
        let single_amt = $("#single_amt").val();
        let return_amt = $("#return_amt").val();

        let updatedData = {
            vehicleType: vehicleType,
            single_amt: single_amt,
            return_amt: return_amt,
        };

        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "/update_vehicle/" + id,
            data: JSON.stringify(updatedData),
            success: function(response) {
                alert("Vehicle updated successfully!");
                // Close the modal
                $('#vehicleModal').modal('hide');
                // Refresh the table data
                ajaxGet();
            },
            error: function(xhr, status, error) {
                console.log("Error updating vehicle:", error);
            }
        });
    });
    */
    
 /***********************************************************************************************************************************/   

    // Delete button click handler
    $(document).on('click', '[id^="deleteBtn-"]', function() {
        let id = $(this).data('vehicle-id');
        if (confirm("Do you want to delete this record?")) {
            $.ajax({
                type: "DELETE",
                url: "/delete_vehicle/" + id,
                success: function(response) {
                    alert(response);
                    // Refresh the table data after deletion
                    ajaxGet();
                },
                error: function(xhr, status, error) {
                    console.log("Error deleting vehicle:", error);
                }
            });
        }
    });
});
