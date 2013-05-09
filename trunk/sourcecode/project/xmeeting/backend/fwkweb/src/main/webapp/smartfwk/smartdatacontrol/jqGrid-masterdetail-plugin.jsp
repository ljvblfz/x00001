<script type="text/javascript" src="smartfwkfwk/smartdatacontrol/fwk_dict.page?dataControlId=<%=request.getParameter("masterDataControlId")%>"> 
</script>
主表 
<table id='<%=request.getParameter("masterDataControlId")%>_list' width="100%"></table>
<div id='<%=request.getParameter("masterDataControlId")%>_pager'></div> 


<script type="text/javascript" src="smartfwk/smartdatacontrol/fwk_dict.page?dataControlId=<%=request.getParameter("detailDataControlId")%>"> 
</script>
  

从表 
<table id='<%=request.getParameter("detailDataControlId")%>_list' width="100%"></table>
<div id='<%=request.getParameter("detailDataControlId")%>_pager'></div> 


<script>


<% 
String masterDataControlId=request.getParameter("masterDataControlId");
%>
$(function(){
	//$("#<%=masterDataControlId%>_list").jqGrid('setGridParam',{datatype:'json'}).trigger('reloadGrid');
});

</script>
 
 
