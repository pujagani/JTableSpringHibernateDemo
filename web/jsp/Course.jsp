<html>
<head>
  <title>Course List</title>
  <%@ include file="JavascriptAndCSS.jsp" %>
</head>
<body>
<script src="../js/jtable/course.js"></script>
<script>
    $(document).ready(function () {
        $('#JTable').jtable(CourseJTable);
        $('#JTable').jtable('load');
    });
</script>
</body>
</html>