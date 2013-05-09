<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script>
	findName("processUserName","workflowprocess").val('<%=request.getParameter("bizKey")%>  ');
</script>
<div>
			您的bizKey为<input name="processUserName"></input>
			...........
</div>