<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="controlPanelTemplate">
    <tiles:putAttribute name="body">
        <h1>Add/Edit User</h1>
		<form:form role="form" name="user" modelAttribute="user" action="/spring-base/control_panel/users/save" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<form:hidden path="id" readonly="true"/>
			<div class="form-group">
				<form:label path="username">Username:</form:label>
				<form:input class="form-control" path="username" value="${user.username}"/>
				<form:errors path="username" element="div" cssClass="alert alert-danger"/>
			</div>
			<div class="form-group">
				<form:label path="email">Email:</form:label>
				<form:input class="form-control" path="email" value="${user.email}"/>
				<form:errors path="email" element="div" cssClass="alert alert-danger"/>
			</div>
			<div class="form-group">
				<form:label path="country">Country:</form:label>
				<form:select path="country" items="${countries}" itemValue="language" itemLabel="countryName" />
				<form:errors path="country" element="div" cssClass="alert alert-danger"/>
			</div>
			<div class="form-group">
				<form:label path="birthDay">Birth Day:</form:label>
				<fmt:formatDate value="${user.birthDay}" type="date" pattern="dd-MM-yyyy" var="birthDayFormatted" />
				<form:input class="form-control" data-provide="datepicker" data-date-format="dd-mm-yyyy" path="birthDay" value="${birthDayFormatted}" />
				<form:errors path="birthDay" element="div" cssClass="alert alert-danger"/>
			</div>
			<div class="form-group">
				<form:label path="formPasswordOne">Password:</form:label>
				<form:password class="form-control" path="formPasswordOne" />
				<form:errors path="formPasswordOne" element="div" cssClass="alert alert-danger"/>
			</div>
			<div class="form-group">
				<form:label path="formPasswordTwo">Confirm Password:</form:label>
				<form:password class="form-control" path="formPasswordTwo" />
				<form:errors path="formPasswordTwo" element="div" cssClass="alert alert-danger"/>
			</div>
			<div class="form-group">
				<form:label path="authorities"><form:checkboxes class="checkbox checkbox-inline" path="authorities" items="${roles}" itemLabel="authority" itemValue="id" /></form:label>
			</div>
			<div class="form-group">
				<form:label path="enabled"><form:checkbox class="checkbox checkbox-inline" path="enabled" />Enabled</form:label>
			</div>
			<form:button class="btn" type="submit">Submit</form:button>
		</form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>