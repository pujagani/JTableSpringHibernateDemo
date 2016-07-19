<html>
<head>
  <title>City List</title>
  <%@ include file="JavascriptAndCSS.jsp" %>
</head>
<body>
<script src="../js/jtable/city.js"></script>
<script>
    $(document).ready(function () {
        $('#JTable').jtable(CityJTable);
        $('#JTable').jtable('load');
    });
</script>
</body>
</html>