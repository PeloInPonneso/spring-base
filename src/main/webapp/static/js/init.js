$(document).ready(function(){
	$('#users').dataTable( {
        "bSort": false,
        "iDisplayLength": 3,
		"sDom": "t<'text-center'p>",
		"sPaginationType": "bootstrap"
	});
});