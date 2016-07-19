<html>
<head>
  <title>Spring JQuery</title>
  <%@ include file="JavascriptAndCSS.jsp" %>
</head>
<body>
<script>
var rows = ${RowCount};

$(document).ready(function () {
    var $table = $('<table />').attr({ width : '100%', border : 1}).css({'border-collapse' : 'collapse'})
    var $tr = $('<tr />').appendTo($table).addClass('ui-widget-header ui-widget');
    $('<td />').css({'font-size' : '1.05em' }).html('Table').appendTo($tr);
    $('<td />').css({'font-size' : '1.05em' }).html('Row Count').appendTo($tr);
    $('<td />').appendTo($tr);
    for( var i=0; i<rows.length; i++)
    {
        var row = rows[i];
        $tr = $('<tr />').appendTo($table);
        $('<td />').css({'font-size' : '1.05em' }).html(row[0]).appendTo($tr);
        $('<td />').css({'font-size' : '1.05em' }).html(row[1]).appendTo($tr);
        var $td = $('<td />').css({'font-size' : '1.05em' }).appendTo($tr);
        $a = $('<a />').attr({href : 'DatabaseSetup?table=' + row[0]}).html('Reset').appendTo($td);
    }
    $table.appendTo('body');
});

</script>
${Error}
</body>
</html>
