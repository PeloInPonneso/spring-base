<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
 
<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">
        <h1>${user.username} presentation</h1>
    </tiles:putAttribute>
</tiles:insertDefinition>