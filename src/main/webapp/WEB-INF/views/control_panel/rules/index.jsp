<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<tiles:insertDefinition name="controlPanelTemplate">
    <tiles:putAttribute name="body">
    	<div class="well well-sm text-center"><spring:message code="table.rule.list" /></div>
		<div class="table-responsive">
 			<table class="table table-bordered">
 				<thead> 
					<tr>  
	   					<th class="text-center">
							<a class="btn btn-xs btn-primary" role="button" href="/spring-base/control_panel/rules/add">
					    		<span class="glyphicon glyphicon-plus" />
					    	</a>
						</th>  
	   					<th>Name</th>
	   					<th class="text-center">Actions</th>
	  				</tr>
	  			</thead>
  				<tbody>
  					<c:if test="${!empty rules}">
						<c:forEach items="${rules}" var="rule">  
		 				<tr>
		   					<td class="text-center"><c:out value="${rule.id}"/></td>  
		    				<td><c:out value="${rule.name}"/></td>
		    				<td class="text-center">
		    					<div class="btn-group">
			    					<a class="btn btn-xs btn-success" role="button" href="/spring-base/control_panel/rules/run/${rule.id}">
			    						<span class="glyphicon glyphicon-play"/>
			    					</a>
			    					<a class="btn btn-xs btn-warning" role="button" href="/spring-base/control_panel/rules/edit/${rule.id}">
			    						<span class="glyphicon glyphicon-edit"/>
			    					</a>
			    					<a class="btn btn-xs btn-danger" role="button" href="/spring-base/control_panel/rules/delete/${rule.id}">
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
		<c:if test="${not empty message}">
			<div class="container-fluid">
				<div class="alert alert-warning"><spring:message code="${message}" /></div>
			</div>
		</c:if>
    </tiles:putAttribute>
</tiles:insertDefinition>