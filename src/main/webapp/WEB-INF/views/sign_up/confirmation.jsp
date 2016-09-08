<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <c:choose>
			<c:when test="${not empty user}">
				<div class="alert alert-success">
			    	<spring:message code="confirmation.success" arguments="${user.username}"/>
			    </div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-danger">
					<spring:message code="confirmation.error" />
				</div>
			</c:otherwise>
		</c:choose>
    </tiles:putAttribute>
</tiles:insertDefinition>