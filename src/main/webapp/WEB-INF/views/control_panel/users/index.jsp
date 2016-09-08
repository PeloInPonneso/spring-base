<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="controlPanelTemplate">
    <tiles:putAttribute name="body">
    	<div class="well well-sm text-center"><spring:message code="table.user.list" /></div>
    	<div class="table-responsive">
 			<table id="users" class="table table-bordered">
 				<thead>
					<tr>  
	   					<th class="text-center">
							<a class="btn btn-xs btn-primary" role="button" href="/spring-base/control_panel/users/add">
					    		<span class="glyphicon glyphicon-plus" />
					    	</a>
						</th>  
	   					<th>Username</th>
	   					<th>Not Expired</th>
	   					<th>Not Locked</th>
	   					<th>Credentials Not Expired</th>
	   					<th>Enabled</th>
	   					<th class="text-center">Actions</th>
	  				</tr>  
	  			</thead>
	  			<tbody> 
	  				<c:if test="${!empty users}">
						<c:forEach items="${users}" var="user">  
		 				<tr>  
		   					<td class="text-center"><c:out value="${user.id}"/></td>  
		    				<td><c:out value="${user.username}"/></td>  
		    				<td><c:out value="${user.accountNonExpired}"/></td>
		    				<td><c:out value="${user.accountNonLocked}"/></td>  
		    				<td><c:out value="${user.credentialsNonExpired}"/></td>  
		    				<td><c:out value="${user.enabled}"/></td>
		    				<td class="text-center">
		    					<div class="btn-group">
			    					<a class="btn btn-xs btn-warning" role="button" href="/spring-base/control_panel/users/edit/${user.id}">
			    						<span class="glyphicon glyphicon-edit"/>
			    					</a>
			    					<a class="btn btn-xs btn-danger" role="button" href="/spring-base/control_panel/users/delete/${user.id}">
			    						<span class="glyphicon glyphicon-remove"/>
			    					</a>
		    					</div>
		    				</td>
		   				</tr>  
		  				</c:forEach>
		  			</c:if>
	  			</tbody> 
 			</table>  
 		</div>
    </tiles:putAttribute>
</tiles:insertDefinition>