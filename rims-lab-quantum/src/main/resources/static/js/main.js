$(document).ready(function() {
	console.log('ready......');
	$('#lab').bootstrapValidator({
//		rules: {
//			orcOrderNumber: {
//				required: true
//			},
//			obrOrderNumber: {
//				required: true
//			}
//		},
		fields: {
			orcOrderNumber: {
				validators: {
					regexp: {
						message: 'Invalid ORC Order Number',
						regexp: /^[a-zA-Z0-9]{1,10}$/
					},
					notEmpty: {
                        message: 'The ORC Order Number is required and cannot be empty'
                    },
				}
			},
			patientId: {
                validators: {
                    notEmpty: {
                        message: 'The patient name is required and cannot be empty'
                    },
                    stringLength: {
                        max: 10,
                        message: 'The patient name must be less than 10 characters long'
                    }
                }
            },
            nricFinNumber: {
                validators: {
                    notEmpty: {
                        message: 'The patient NRIC is required and cannot be empty'
                    },
                    stringLength: {
                        max: 10,
                        message: 'The patient NRIC must be less than 10 characters long'
                    }
                }
            },
            visitNumber: {
            	validators: {
            		remote: {
            			url: 'valVisitNo',
            			message: 'Invalid visit number'
//            			data: function(validator) {
//            				
//            			}
            		}
            	}
            }
		},
		container: '#messages',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
		highlight: function(element) {
	        var id_attr = "#" + $( element ).attr("id") + "1";
	        $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
	        //$(id_attr).removeClass('glyphicon-ok').addClass('glyphicon-remove');
	        $(element).siblings('.input-group-addon').children('i').removeClass('glyphicon-ok').addClass('glyphicon-remove');
	    },
	    unhighlight: function(element) {
	        var id_attr = "#" + $( element ).attr("id") + "1";
	        $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
	        //$(id_attr).removeClass('glyphicon-remove').addClass('glyphicon-ok');
	        $(element).siblings('.input-group-addon').children('i').removeClass('glyphicon-remove').addClass('glyphicon-ok');
	    },
	    errorElement: 'span',
	        errorClass: 'help-block',
	        errorPlacement: function(error, element) {
	            if(element.length) {
	                error.insertAfter($(element).closest('.input-group'));
	            } else {
	            error.insertAfter(element);
	            }
	        } 
	});
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
		var valid = $('#orcOrderNumber').valid();
		console.log(valid);
		if (!valid) {
			return;
		}
		console.log('get report');
		var orcOrderNumber = $('#orcOrderNumber').val();
		/*
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
		*/
		window.location = 'getReport?orderNumber=' + orcOrderNumber;
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
	$('#lab').valid();
});