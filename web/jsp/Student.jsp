<html>
<head>
  <title>Student List</title>
<%@ include file="JavascriptAndCSS.jsp" %>
</head>
<body>
<script src="../js/jtable/student.js"></script>
<script src="../js/jtable/student_phone.js"></script>
<script src="../js/jtable/student_result.js"></script>
<script src="../js/jtable/city.js"></script>
<script src="../js/jtable/course.js"></script>
<script>
// Getting error if any.
// .js files for every class is defined in jTable directory.
// These files contain the parameters as required by jTable.
var error = '${Error}';

$(document).ready(function () {
    if (error) {
    	alert(error);
    	return;
    }

    function studentPhone(studentData) {
    // Setting the parameters as required.
    	StudentPhoneJTable.title = studentData.record.name + ' - Phone numbers';
    	StudentPhoneJTable.actions.listAction = 'StudentPhone/List?student_id=' + studentData.record.id;
    	StudentPhoneJTable.fields.student_id.defaultValue = studentData.record.id;
    	var $img = $('<img src="../css/img/phone.png" title="Edit phone numbers" />');
    	$img.css({
    		cursor : 'pointer'
    	});
    	$img.click(function () {
    		$('#JTable').jtable('openChildTable',
    			$img.closest('tr'), StudentPhoneJTable, function (data) {
    			data.childTable.jtable('load');
    		});
    	});
    	return $img;
    }

    function studentResult(studentData) {
    // Setting parameters as required and also defining pop options.
    	StudentResultJTable.fields.course_name.popUpOpts = {
    		keyField : 'course_id',
    		displayField : 'course_name',
    		idField : 'id',
    		nameField : 'name',
    		selectionTable : CourseJTable
    	};
    	StudentResultJTable.title = studentData.record.name + ' - Exam Results';
    	StudentResultJTable.actions.listAction = 'StudentResult/List?student_id=' + studentData.record.id;
    	StudentResultJTable.fields.student_id.defaultValue = studentData.record.id;
    	var $img = $('<img src="../css/img/exam.png" title="Edit exam results" />');
    	$img.css({
    		cursor : 'pointer'
    	});
    	$img.click(function () {
    		$('#JTable').jtable('openChildTable',
    			$img.closest('tr'), StudentResultJTable, function (data) {
    			data.childTable.jtable('load');
    		});
    	});
    	return $img;
    }
    // Master Child tables.
    StudentJTable.fields.phones.display = studentPhone;
    StudentJTable.fields.exams.display = studentResult;
    // Setting layoutBreak true as user desires.
   StudentJTable.fields.city_name.layoutBreak = true;
     StudentJTable.fields.about.layoutBreak = true;
    // Defining popUp options as required.
    StudentJTable.fields.city_name.popUpOpts = {
    	keyField : 'city_id',
    	displayField : 'city_name',
    	idField : 'id',
    	nameField : 'name',
    	selectionTable : CityJTable
    };
    $('#JTable').jtable(StudentJTable);
    $('#JTable').jtable('load');
});
</script>
</body>
</html>