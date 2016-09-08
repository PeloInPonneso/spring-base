<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="page-header">
	<security:authorize access="isAuthenticated()">
		<h6><spring:message code="welcome.message" arguments="${username}" /></h6>
	</security:authorize>
</div>
<div class="container-fluid">
	<c:if test="${param.error != null}">
	    <div class="alert alert-danger">
	        <spring:message code="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
	    </div>
	</c:if>
	<c:if test="${param.logout != null}">
	    <div class="alert alert-success">
	        <spring:message code="logout.success" />
	    </div>
	</c:if>
</div>
