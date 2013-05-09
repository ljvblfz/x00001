<script type="text/javascript" src="smartfwk/smartdatacontrol/fwk_dict.page?dataControlId=<%=request.getParameter("dataControlId")%>"> 
</script>
  
 
<table id='<%=request.getParameter("dataControlId")%>_list' width="100%"></table>
<div id='<%=request.getParameter("dataControlId")%>_pager'></div> 
 
<script>

<% 
String dataControlId=request.getParameter("dataControlId");
%>
$(function(){
	var datatype=$("#<%=dataControlId%>_list").jqGrid('getGridParam','datatype'); 
	//if('json'!=datatype){
	//	$("#<%=dataControlId%>_list").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid'); 
	//}
});

</script>