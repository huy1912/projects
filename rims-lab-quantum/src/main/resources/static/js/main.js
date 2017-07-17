$(document).ready(function() {
	console.log('ready......');
	$('#submitOrder').click(function() {
		console.log('submit order');
		var orcOrderNumber = $('#orcOrderNumber').val();
		var obrOrderNumber = $('#obrOrderNumber').val();
		var patientId = $('#patientId').val();
		//var patientName = $('#patientName').val();
		var nricFinNumber = $('#nricFinNumber').val();
		var visitNumber = $('#visitNumber').val();
		var params = {orcOrderNumber: orcOrderNumber,
				obrOrderNumber: obrOrderNumber,
//				patientName: patientName,
				nricFinNumber: nricFinNumber,
				patientId: patientId,
				visitNumber: visitNumber};
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
	
	$('#getReport').click(function() {
		console.log('get report');
		var orcOrderNumber = $('#orcOrderNumber').val();
		$.ajax({
			type: 'POST',
			contentType: 'application/json',
			url: 'getReport',
			data: orcOrderNumber,
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
	
	$('#getResult').click(function() {
		console.log('get report');
		var orcOrderNumber = $('#orcOrderNumber').val();
		$.ajax({
			type: 'POST',
			contentType: 'application/json',
			url: 'getResult',
			data: orcOrderNumber,
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