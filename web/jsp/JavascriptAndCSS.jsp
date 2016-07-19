<script src="../js/jquery-2.1.4.js"></script>
<script src="../js/jquery-2.1.4.js"></script>
<script src="../js/jquery-ui.js"></script>
<script src="../js/jquery.jtable.js"></script>
<script src="../js/jtable_form_extension.js"></script>
<script src="../js/formWidget.js"></script>
<%
// Same as jTableSpring Demo.
// Obtaining attribute values that were set in LoginController.
// These values are used to display user name when he logs in and gives the logout option.
// Once the user logs out, the login option is changed to log out.
// Loginurl is the appropriate url based on the logindisplay text.
// Theme is used to set the appropriate theme else default theme is displayed.

String loginuser = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
if ("anonymousUser".equals(loginuser)) loginuser = null;

String logindisplay="";
String loginurl="";
if (loginuser == null)
{
    loginuser = "";
    logindisplay = "Login";
    loginurl = "Login";
}
else
{
     logindisplay = "Logout";
     loginurl = "Login/Logout";
}
String theme=(String)session.getAttribute("Theme");
if (theme==null) theme="blitzer";
%>

<link rel="stylesheet" type="text/css" href="../css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="../css/jquery-ui.<%=theme%>.css" />
<link rel="stylesheet" type="text/css" href="../css/jtable/jtable_jqueryui.css" />

<style>
body, td, th {
font-size:12px;
}
</style>
<script>
// Cookie-to-Header Token section of https://en.wikipedia.org/wiki/Cross-site_request_forgery.
// .ajaxSetup is a jQuery method.
// header is passed to the method along withe csrf.token
// Server sets a token. The token is copied to the header.
// With ever request the header along with token is sent. Token is checked by the server.
// If the token matches then request is allowed to process.
$.ajaxSetup({ headers : { '${_csrf.headerName}' : '${_csrf.token}' } });

var blurTimer;
var blurTimeAbandoned = 200;

function createMenu() {
	var $menuContainer = $('#menuContainer');
	var pos = $menuContainer.position();
	var height = $menuContainer.outerHeight();
	var width = $menuContainer.outerWidth();

	var $menu = $('<ul />')
		.css({
			position : 'absolute',
			'z-index' : 300,
			left : pos.left + 'px',
			top : (pos.top + height + 2) + 'px',
			width : width + 'px'
		});
	$('<li />').attr({
		url : 'Student'
	}).html('Student').appendTo($menu);
	$('<li />').attr({
		url : 'City'
	}).html('City').appendTo($menu);
	$('<li />').attr({
		url : 'Course'
	}).html('Course').appendTo($menu);
	$('<li />').attr({
		url : 'Registration'
	}).html('Registration').appendTo($menu);
	$('<li />').attr({
		url : 'DatabaseSetup'
	}).html('Database Setup').appendTo($menu);

	$menu.appendTo($menuContainer);
	$menu.menu({
		select : function (event, ui) {
			var item = ui.item;
			var url = item.attr('url');
			location.href = url;
		},

		focus : function (event, ui) {
			clearTimeout(blurTimer);
		},

		blur : function (event, ui) {
			blurTimer = setTimeout(function () {
					$menu.remove();
				}, blurTimeAbandoned);
		}
	});
}
$(document).ready(function () {
    var opts = $.hik.jtable.prototype.options;
    opts.defaultDateFormat = "dd/mm/yy";
    opts.openChildAsAccordion = true;
    opts.jqueryuiTheme = true;
    opts.paging = true;
    opts.pageSize = 10;
    opts.sorting = true;


    var $table = $('<table />')
        .attr({ width : '100%', cellPadding : '5'})
        .addClass('ui-widget-header ui-widget');
    var $tr = $('<tr />').appendTo($table);
    var $td = $('<td />').attr({align : 'center'}).css({width : '25px'}).appendTo($tr);
    var $a = $('<a href="Home" />');
    $a.appendTo($td);
    var $img = $('<img src="../css/img/Home.png" title="Home" />');
    $img.appendTo($a);
    var $td = $('<td />')
        .html('Menu')
        .click(createMenu)
        .attr({id : 'menuContainer', width:'10%'})
        .css({ cursor : 'pointer'})
        .appendTo($tr);
    var $td = $('<td />')
        .html('Spring JQuery Hibernate')
        .attr({align : 'center', width: '90%'})
        .css({'font-size': '1.1em'})
        .appendTo($tr);
  var $td = $('<td />')
                 .attr({align : 'center'})
                 .css({'font-size': '1.1em'});


     var $a= $("<a/>").html('<%= logindisplay %>').attr({href:'<%= loginurl %>'});
      $a.appendTo($td);
      $td.appendTo($tr);
     var $td = $('<td />')
             .html(' <%= loginuser %>')
             .attr({align : 'center'})
             .css({'font-size': '1.1em'})
             .appendTo($tr);


    $table.appendTo('body');

    $div = $('<div />').attr({ id : 'JTable'}).appendTo('body');

});
</script>