<html>
<head>
  <title>Login User</title>
  <%@ include file="JavascriptAndCSS.jsp" %>
  <script src="../js/project.js"></script>
</head>
<body>

<script>
$( document ).ready(function() {
// Login form is painted as soon as the document is ready.
$('#FormDiv').formWidget(Login);
});

function loginForm()
{
// When login button is clicked, AJAX call is made to the appropriate controller.
// form.serialize() sends the values of form fields as postData to the controller.
var $form = $('#FormDiv').formWidget("instance").$form;
var postData = $form.serialize();
    callAjax("Login/Login", postData).done(function (data) {
        if (data.Result!="OK") {
            showMsg(data.Message);
        }
        else {
            location.href='Home';
        }
    });

}
// Click of login button makes a call to the loginform function and then the controller check
// if the entered credentials are correct.
// Defining values of parameters to be passed to formWidget.

var Login = {
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
		theme : {
		    label: 'Theme',
            name : 'theme',
			type: 'dropdown',
			options:["blitzer","frog","humanity","redmond","cupertino"],
		},
		dialogOpts :
		 {title:'Login', modal: true,buttons: [
                           {
                             text: "Login",
                             click: function() {
                               loginForm();
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