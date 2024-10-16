$(document).ready(function() {
    // Fetch category data on page load
   

    // Clear form fields on page load
    $("#name").val("");
    $("#booth").val("");
    $("#email").val("");
    $("#password").val("");

    // Handle form submission
    $("#form1").on("submit", function(event) {
        event.preventDefault(); // Prevent default form submission
        ajaxPost();
    });

   

    // Function to handle form submission
    function ajaxPost() {
		alert("do you want to save...")
        var formData = {
            name: $("#name").val(),
            booth_no: $("#booth").val(),
            email: $("#email").val(),
            password: $("#password").val()
        };

		console.log("New Operator Added :", formData);
        $.ajax({
            type: "POST",
            url: "/save",
            data: formData,
            contentType: "application/x-www-form-urlencoded",
            success: function(response) {
                alert(response);
                // Clear form fields after successful submission
                $("#name").val("");
                $("#booth").val("");
                $("#email").val("");
                $("#password").val("");
                // Fetch updated data after successful addition
                ajaxGet();
            },
            error: function(xhr, status, error) {
                alert("Failed to add employee: " + error);
            }
        });
    }
});




    
   function ajaxGet() {
    alert("Getting operators all data....");
    
    $.ajax({
        type: "GET",
        url: "/existing_operators",
        dataType: "json",
        success: function(data) {
            console.log("Response Data: ", data); // Log the entire response data
            var d = '';
            if (Array.isArray(data)) {
                for (var i = 0; i < data.length; i++) {
                    d += '<tr>' +
                         '<td>' + data[i].opId + '</td>' +
                         '<td>' + data[i].name + '</td>' +
                         '<td>' + data[i].booth_no + '</td>' +
                         '<td>' + data[i].email + '</td>' +
                         '<td>' + data[i].password + '</td>' +
                         '<td><button data-bs-toggle="modal" data-bs-target="#editData" data-operator-id="' + data[i].opId + '" id="editBtn-' + data[i].opId + '" class="btn-rounded  btn btn-info  mdi mdi-lead-pencil"></button> ' +
                        '<button data-operator-id="' + data[i].opId + '" id="deleteBtn-' + data[i].opId + '" class="btn-rounded  btn btn-danger mdi mdi-delete"></button></td>' +
                        '</tr>';
                }
                $('#table').html(d);
            } else {
                console.error("Unexpected data format:", data);
            }
        },
        error: function(xhr, status, error) {
            alert("Error loading data...");
            console.error("Error details:", status, error);
            console.error("Response text:", xhr.responseText);
        }
    });
}



/*-------------------------------------------------------------------------------------------------------------------*/

$(document).on('click', '[id^="editBtn-"]', function() {
	alert("Do you want to change data ?");
	let opId = $(this).data('operator-id');
	console.log("opId is : " + opId);
	
	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: "/get_operator/" + opId,
		dataType: 'json',
		success: function(data) {
			console.log("Data received:", data);
			if (data) {
				$("#id").val(data.opId); // Ensure this matches your Operators entity field name
				$("#name1").val(data.name);
				$("#booth1").val(data.booth_no);
				$("#email1").val(data.email);
				$("#password1").val(data.password);
			}
		},
		error: function(e) {
			console.log("Error in fetching data for Id:", e);
		}
	});
});

/*---------------------------------------------------------------------------------------------------------------------*/

// Update data

$("#saveForm").on('click', function(e){
	alert("Updating data ...");
	e.preventDefault();

	let id = $("#id").val();
	let name = $("#name1").val();
	let booth_no = $("#booth1").val();
	let email = $("#email1").val();
	let password = $("#password1").val();

	let updatedData = {
		opId: id, // Ensure this matches your Operators entity field name
		name: name,
		booth_no: booth_no,
		email: email,
		password: password
	};

	console.log("Updated Data: ", updatedData);

	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: "/editData",
		data: JSON.stringify(updatedData),
		success: function(response) {
			alert("Updated Successfully...");
			ajaxGet();
		},
		error: function(e) {
			alert("Not Working...");
			console.log("Error: ", e);
		}
	});
});



/*-------------------------------------------------------------------------------------------------------------------*/
$(document).on('click', '[id^="deleteBtn-"]', function() {
        let opId = $(this).data('operator-id');
        
        if (confirm("Do you want to delete this record?")) {
            $.ajax({
                type: "DELETE",
                url: "/deleteData/" + opId,
                success: function(response) {
                    alert(response);
                    console.log(response);
                    ajaxGet(); // Refresh the table
                },
                error: function(e) {
                    //alert("Details not deleted: " + e.responseText);
                    //console.log("ERROR: ", e);
                      alert("Delete failed: " + e.responseText);
                }
            });
        }
    });

/*-------------------------------------------------------------------------------------------------------------------- */
 