<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="controlPanelTemplate">
    <tiles:putAttribute name="body">
        <h1>Add/Edit Role</h1>
		<form:form role="form" name="role" modelAttribute="role" action="/spring-base/control_panel/roles/save" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<form:hidden path="id" readonly="true"/>
			<div class="form-group">
				<form:label path="authority">Authority:</form:label>
				<form:input class="form-control" path="authority" value="${role.authority}"/>
			</div>
			<form:button class="btn" type="submit">Submit</form:button>
		</form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>