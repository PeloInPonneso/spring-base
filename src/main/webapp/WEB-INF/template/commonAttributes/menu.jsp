<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<span class="navbar-brand"><spring:message code="application.name" /></span>
      		<button type="button" class="navbar-toggle glyphicon glyphicon-list" data-toggle="collapse" data-target="#cabsb-navbar" />
    	</div>
		<div id="cabsb-navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="/spring-base">Home</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
		      			Lingue 
		      			<span class="caret"></span>
	    			</a>
	    			<ul class="dropdown-menu">
	    				<c:forEach items="${langs}" var="lang">
							<li><a href="?lang=${lang}">${lang}</a></li>
						</c:forEach>
	    			</ul>
				</li>
				<security:authorize access="isAnonymous()">
					<li><a href="/spring-base/sign-up">Sign Up</a></li>
					<li><a href="/spring-base/login">Login</a></li>
				</security:authorize>
				<security:authorize access="isAuthenticated()">
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="/spring-base/control_panel">
			      			Control Panel 
			      			<span class="caret"></span>
		    			</a>
		    			<ul class="dropdown-menu">
		    				<li><a href="/spring-base/control_panel/users">Users</a></li>
							<li><a href="/spring-base/control_panel/roles">Roles</a></li>
							<li><a href="/spring-base/control_panel/rules">Rules</a></li>
		    			</ul>
					</li>
					<li><a href="${logoutUrl}">Logout</a></li>
				</security:authorize>
			</ul>
		</div>
	</div>
</div>
