<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<!DOCTYPE html>
<html>
    <tiles:insertAttribute name="head" />
	<body>
		<tiles:insertAttribute name="vars" />
		<tiles:insertAttribute name="menu" />
	    <div class="container">
	    	<div class="row">
	    		<tiles:insertAttribute name="header" />
		        <div class="container-fluid">
		            <tiles:insertAttribute name="body" />
		        </div>
		        <tiles:insertAttribute name="footer" />
	    	</div>
	    </div>
	</body>
</html>