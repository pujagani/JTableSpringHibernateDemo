function callAjax(url, postData) {
	var self = this;
	if (!postData)
		postData = {};

	var jqxhr = $.ajax({
			url : url,
			data : postData,
			type : 'POST',
			dataType : 'json'
		});

	jqxhr.fail(function (jqXHR, textStatus, errorThrown) {
		alert("Server Communication Error" + "<br><br>" + "Error Code: " + jqXHR.status + "<br>" + "Error: " + jqXHR.statusText);
	});

	return jqxhr;
}

function showMsg(msg) {
	$div = $('#MsgDiv');
	$div.empty();
	$div.html(msg);

	var dialogOptions = {
		title : 'Message',
		modal : true,
	};
	$div.dialog(dialogOptions);
}

