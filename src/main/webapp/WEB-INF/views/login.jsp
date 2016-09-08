<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <form role="form" name="login" action="${loginUrl}" method="post">
	    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
	            <label for="j_username">Username</label>
	            <input class="form-control" type="text" id="j_username" name="username" placeholder="Username" />
            </div>
            <div class="form-group">
	            <label for="j_password">Password</label>
	            <input class="form-control" type="password" id="j_password" name="password" placeholder="Password" />
	        </div>
            <button class="btn" type="submit">Login</button>
	    </form>
    </tiles:putAttribute>
</tiles:insertDefinition>