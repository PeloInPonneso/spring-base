<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="controlPanelTemplate">
    <tiles:putAttribute name="body">
        <h1>Add/Edit Rule</h1>
		<form:form role="form" name="rule" modelAttribute="rule" action="/spring-base/control_panel/rules/save" method="post" enctype="multipart/form-data">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<form:hidden path="id" readonly="true"/>
			<div class="form-group">
				<form:label path="name">Name:</form:label>
				<form:input class="form-control" path="name" value="${rule.name}"/>
				<form:errors path="name" element="div" cssClass="alert alert-danger"/>
			</div>
			<div class="form-group">
				<form:label path="file">File:</form:label>
				<form:input type="file" class="form-control" path="file" value="${rule.file}"/>
				<form:errors path="file" element="div" cssClass="alert alert-danger"/>
			</div>
			<form:button class="btn" type="submit">Submit</form:button>
		</form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>