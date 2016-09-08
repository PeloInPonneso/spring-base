<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
	<title><spring:message code="application.name" /></title>
   	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="/spring-base/webjars/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet">
	<link href="/spring-base/webjars/bootstrap-datepicker/1.3.0/css/datepicker.css" rel="stylesheet">
	<link href="/spring-base/static/css/google-compatibily.css" rel="stylesheet">
	<link href="/spring-base/static/css/jquery-DT-pagination.css" rel="stylesheet">
	<link href="/spring-base/static/css/main.css" rel="stylesheet">
	<c:choose>
		<c:when test="${javascriptDebug}">
			<script src="/spring-base/webjars/jquery/1.10.2/jquery.js"></script>
			<script src="/spring-base/webjars/datatables/1.9.4/media/js/jquery.dataTables.js"></script>
			<script src="/spring-base/webjars/bootstrap/3.1.0/js/bootstrap.js"></script>
			<script src="/spring-base/webjars/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
			<script src="/spring-base/static/js/jquery-DT-pagination.js"></script>
			<script src="/spring-base/static/js/init.js"></script>
			<script src="/spring-base/static/js/main.js"></script>
			<!--[if lt IE 9]>
			<script src="/spring-base/webjars/html5shiv/3.7.0/html5shiv.js"></script>
			<script src="/spring-base/webjars/respond/1.4.2/dest/respond.src.js"></script>
			<![endif]-->
		</c:when>
		<c:otherwise>
			<script src="/spring-base/webjars/jquery/1.10.2/jquery.min.js"></script>
			<script src="/spring-base/webjars/datatables/1.9.4/media/js/jquery.dataTables.min.js"></script>
			<script src="/spring-base/webjars/bootstrap/3.1.0/js/bootstrap.min.js"></script>
			<script src="/spring-base/webjars/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
			<script src="/spring-base/static/js/jquery-DT-pagination.js"></script>
			<script src="/spring-base/static/js/init.js"></script>
			<script src="/spring-base/static/js/main.js"></script>
			<!--[if lt IE 9]>
			<script src="/spring-base/webjars/html5shiv/3.7.0/html5shiv.min.js"></script>
			<script src="/spring-base/webjars/respond/1.4.2/dest/respond.min.js"></script>
			<![endif]-->
		</c:otherwise>
	</c:choose>
</head>