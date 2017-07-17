$(document).ready(function() {
	console.log('ready......');
	$('#submitOrder').click(function() {
		console.log('submit order');
		var orderNumber = $('#orderNumber').val();
		var patientName = $('#patientName').val();
		var nricFinNumber = $('#nricFinNumber').val();
		var params = {orderNumber: orderNumber,
				patientName: patientName,
				nricFinNumber: nricFinNumber};
		console.log(params);
		$.ajax({
			type: 'POST',
			contentType: 'application/json',
			url: 'submitOrder',
			data: JSON.stringify(params),
			dataType: 'text',
			cache: false,
			timeout: 600000,
			success: function (data) {
				console.log(data);
				var json = "<h4>Ajax Response</h4><pre>"
	                + JSON.stringify(data, null, 4) + "</pre>";
	            $('#feedback').html(json);
			},
			error: function(e) {
				console.log(e)
			}
				
		});
	});
});