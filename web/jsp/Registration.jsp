<html>
<head>
  <title>Register User</title>
  <%@ include file="JavascriptAndCSS.jsp" %>
  <script src="../js/project.js"></script>
</head>
<body>

<script>
$( document ).ready(function() {
// Registration form is painted as soon as the document is ready.
$('#FormDiv').formWidget(Registration);
});


function saveForm()
{
// When login button is clicked, AJAX call is made to the appropriate controller.
// form.serialize() sends the values of form fields as postData to the controller.
var $form = $('#FormDiv').formWidget("instance").$form;
    var postData = $form.serialize();
    callAjax("Registration/Save", postData).done(function (data) {
        if (data.Result!="OK") {
            showMsg(data.Message);
        }
        else {
            location.href='Login';
        }
    });
}
// Defining values of parameters to be passed to formWidget.
var Registration = {
	fields : {
		username : {
		    label: 'Username',
            name : 'user_name',
            type : 'text',
		},
		password : {
		    label: 'Password',
            name : 'password',
            type : 'password',
		},
		full_name : {
		    label: 'Full Name',
            name : 'full_name',
            type : 'text',
		},
		email : {
		    label: 'Email Id',
            name : 'email',
            type : 'text',
		},

		dialogOpts :
		 {title:'Registration', modal: true,buttons: [
                           {
                             text: "Register",
                             click: function() {
                               saveForm();
                             }

                           }
                         ]}


	}
}
</script>
<div id="MsgDiv"> </div>
<div id="FormDiv"> </div>
</body>
</html>