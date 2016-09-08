<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="controlPanelTemplate">
    <tiles:putAttribute name="body">
    	<div class="well well-sm text-center"><spring:message code="table.role.list" /></div>
		<div class="table-responsive">
 			<table class="table table-bordered">
 				<thead> 
					<tr>  
	   					<th class="text-center">
							<a class="btn btn-xs btn-primary" role="button" href="/spring-base/control_panel/roles/add">
					    		<span class="glyphicon glyphicon-plus" />
					    	</a>
						</th>  
	   					<th>Role</th> 
	   					<th>Users</th>
	   					<th class="text-center">Actions</th>
	  				</tr>
	  			</thead>
  				<tbody>
  					<c:if test="${!empty roles}">
						<c:forEach items="${roles}" var="role">  
		 				<tr>
		   					<td class="text-center"><c:out value="${role.id}"/></td>  
		    				<td><c:out value="${role.authority}"/></td>  
		    				<td><c:out value="${fn:length(role.users)}"/></td>
		    				<td class="text-center">
		    					<div class="btn-group">
			    					<a class="btn btn-xs btn-warning" role="button" href="/spring-base/control_panel/roles/edit/${role.id}">
			    						<span class="glyphicon glyphicon-edit"/>
			    					</a>
			    					<a class="btn btn-xs btn-danger" role="button" href="/spring-base/control_panel/roles/delete/${role.id}">
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