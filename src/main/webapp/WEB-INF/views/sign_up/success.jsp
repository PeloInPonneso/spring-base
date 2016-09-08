<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <div class="alert alert-success">
	    	<spring:message code="signup.success" arguments="${user.username},${user.email}"/>
	    </div>
    </tiles:putAttribute>
</tiles:insertDefinition>