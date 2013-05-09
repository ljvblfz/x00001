<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script>
var processTask_bizKey;
var defId;
var instId;
var taskId;
var processGraphicsLoaded=false;
var processTextLoaded=false;
var unfinishInstTasks;

<%
//out.println(request.getParameter("formUri"));
//out.println(request.getParameter("taskId"));
//out.println(request.getParameter("bizKey"));
//out.println(request.getParameter("instId"));
//out.println(request.getParameter("nodeId"));
//out.println(request.getParameter("bizKey"));
//out.println(request.getParameter("defId"));
String formURI=request.getParameter("formUri");
String uri=formURI+"?bizKey="+request.getParameter("bizKey");
out.println("processTask_bizKey='"+uri+"';");
out.println("defId='"+request.getParameter("defId")+"';");
out.println("instId='"+request.getParameter("instId")+"';");
out.println("taskId='"+request.getParameter("taskId")+"';");
%>   
$(function(){
	var originalPanelHeight=$("#founderNavTabPanel").height();  
	findName("processTaskFormDIV","workflowprocess").parent().height(originalPanelHeight-250);
	findName("processFlowTextTrTemplate","workflowprocess").parent().parent().attr("customHeight",originalPanelHeight-248);
	doGetAjax("/syWeb/rs/wfinst/"+instId+"/task/notend",{taskId:taskId},function(data){
		unfinishInstTasks=data.jsonData;
		var processTask_procName=findName("processTask_procName","workflowprocess");
		var processTask_createTime=findName("processTask_createTime","workflowprocess");
		var processTask_currentNode=findName("processTask_currentNode","workflowprocess");
		//var processTask_currentAssignee=findName("processTask_currentAssignee","workflowprocess");
		$.each(unfinishInstTasks, function(index, json) {
			processTask_procName.html(json.PI_SUBJECT);
			processTask_createTime.html(json.createTime);
			processTask_currentNode.html(json.nodeName);
			//processTask_currentAssignee.html(json.assignee);
	     });
	});
	
	
	initTaskToolbar();
});
   
   
	function initTaskToolbar(){
		var formDIV = findName("processTaskFormDIV","workflowprocess");
		doGetAjax("/syWeb/rs/workflow/task/"+taskId+"/toolbar",{},function(data){
			toolbarlist=data.jsonData;
<%--			var processTask_procName=findName("processTask_procName","workflowprocess");--%>
<%--			var processTask_createTime=findName("processTask_createTime","workflowprocess");--%>
<%--			var processTask_currentNode=findName("processTask_currentNode","workflowprocess");--%>
			//var processTask_currentAssignee=findName("processTask_currentAssignee","workflowprocess");
			$.each(toolbarlist, function(index, json) {
				if(json.type!="processStatus"){
					$("<li><a class=\"add\" name=\""+json.id+"\" onclick=\"doProcessTask(this,'"+json.name+"','"+json.id+"','"+json.type+"')\" title=\"新增\"><span>"+json.name+"</span></a></li>").appendTo(findName("workflowtoolbar","workflowprocess"));
				}
				//processTask_procName.html(json.PI_SUBJECT);
				//processTask_createTime.html(json.createTime);
				//processTask_currentNode.html(json.nodeName);
				//processTask_currentAssignee.html(json.assignee);
		     });
			
			formDIV.load(processTask_bizKey);
		});
	}

	
   
  function doProcessTask(obj,name,commandId,commandType){
	  var formName=$(obj).attr("dataform");
	  if(!formName){
		  processTask(name,commandId,commandType);
	  }else{
		  if (findName(formName).valid()) {
		  	processTask(name,commandId,commandType,findName(formName).serializeJson());
		  }
	  }
  }	

  
   function processTask(name,commandId,commandType,formData){
		alertMsg.confirm('确认进行'+name+'操作？', {
				okCall: function(){
				   doPutAjax("/syWeb/rs/workflow/task/"+taskId,$.extend(formData,{commandId:commandId,commandType:commandType,processDesc:findName("taskProcessDesc","workflowprocess").val()}),function(data){
					   alertMsg.info('处理成功');
					   navTab.closeTab("workflowprocess");
					   getMyTasks();
				   });
			}});
   }	
	
   function loadProcessFlowGraphics(){
	   if(!processGraphicsLoaded){
		   doGetAjax("/syWeb/rs/workflow/"+defId+"/graphics",{returnType:"text"},function(data){
			   findName("processTaskProcessPicDIV","workflowprocess").html(data);
			   doGetAjax("/syWeb/rs/wfinst/"+instId+"/task/notend",{},function(data){
				   markRed();
				   processGraphicsLoaded=true;
			   });
		   });
	   }
	   
   }
   function loadProcessFlowText(){
	   if(!processTextLoaded){
		   doGetAjax("/syWeb/rs/wfinst/"+instId+"/task/end",{},function(data){
				var sampleRow=findName("processFlowTextTrTemplate","workflowprocess");
				data2Table(sampleRow,data.jsonData);
			   processTextLoaded=true;
		   });
	   }
	   
   }
   
 //标红
	function markRed() {
		for ( var i = 0; i < unfinishInstTasks.length; i++) {

			var token = unfinishInstTasks[i];
			var nodeId = token.nodeId;
			var svgNodeId = "sid-" + nodeId + "bg_frame";

			var svgElement = document.getElementById(svgNodeId);
			if (svgElement) {
				// 			assignessName: "王志伟"
				// 				createTime: "2011-8-26 1:45:48"
				//var title = task.assignessName + "(" + task.createTime + ")";

				svgElement.setAttribute("stroke", "red");
				svgElement.setAttribute("stroke-width", "2");
				//svgElement.setAttribute("title", title);
			}
			
		}
	}

</script>            
<div class="panelBar">
	<ul class="toolBar" name="workflowtoolbar">
<%--		<li><a class="add" rel="syCodeTypeAdd" onclick="addWPLDetail()" title="新增"><span>同意</span></a></li>--%>
<%--		<li><a class="delete" rel="syCodeTypeDelete" onclick="removeWPLDetail()"  title="新增"><span>否决</span></a></li>--%>
<%--		<li>--%>
<%--			<a onclick="generateWPLDetail()" href="#"  > --%>
<%--				<span--%>
<%--					style="background: url('/dispatch/images/workplan/generate.png') no-repeat center left;">退回</span>--%>
<%--			</a>--%>
<%--		</li>--%>
	</ul>
</div>
	<div class="pageFormContent" defH="45" customHeight="45">
		<fieldset>
				<legend>审批意见</legend>
				<dl style="width: 100%;height: 40px;">
					<dd style="width: 100%;height: 40px" >
						<textarea name="taskProcessDesc" rows="" cols="" style="width: 99%;height: 40px" ></textarea>
					</dd>
				</dl> 
			</fieldset>
		<fieldset>
			<legend>流程信息</legend>
			<dl style="width: 32%">
				<dt style="width: 80px;">流程类型:</dt>
				<dd style="width: 48%" name="processTask_procName">
				</dd>
			</dl> 
			<dl style="width: 32%">
				<dt style="width: 80px">到达时间:</dt>
				<dd style="width: 48%" name="processTask_createTime">
				</dd>
			</dl> 
			<dl style="width: 32%">
				<dt style="width: 80px">当前节点:</dt>
				<dd style="width: 48%" name="processTask_currentNode">
				</dd>
			</dl> 
<%--			<dl style="width: 32%">--%>
<%--				<dt style="width: 80px">当前处理人:</dt>--%>
<%--				<dd style="width: 48%" name="processTask_currentAssignee">--%>
<%--					未处理--%>
<%--				</dd>--%>
<%--			</dl> --%>
			<dl style="width: 32%">
				<dt style="width: 80px">处理状态:</dt>
				<dd style="width: 48%" name="processTask_status">
					未处理
				</dd>
			</dl> 
		</fieldset>
	</div>
	
<div class="pageContent" layouth="42"
	style="height: 670px; overflow: auto;"> 
	<div class="tabs" currentIndex="0"  >
						<div class="tabsHeader">
							<div class="tabsHeaderContent">
								<ul>
									<li><a href="javascript:;"><span>表单</span></a></li>
									<li><a href="javascript:;" onclick="loadProcessFlowGraphics()"><span>流程图</span></a></li>
									<li><a href="javascript:;" onclick="loadProcessFlowText()"><span>处理过程</span></a></li>
								</ul>
							</div>
						</div>
						<div class="tabsContent"   style="overflow: auto; height: 560px;" >
							<div style="height: 100%" name="processTaskFormDIV">
								表单
							</div>
							<div style="height: 100% " >
								<fieldset style="background-color: white;" name="processTaskProcessPicDIV">
								</fieldset>
							</div>
							<div style="height: 100%" name="processTaskProcessFlowDIV">
								<table class="table" customHeight="450" width="100%">
									<thead>
										<tr>
											<th width="100">步骤名称</th>
											<th width="50">处理人</th>
											<th width="100">到底时间</th>
											<th width="100" >完成时间</th>
											<th width="60" >处理结果</th>
											<th width="150" >处理意见</th>
										</tr>
									</thead>
									<tbody>
										<tr name="processFlowTextTrTemplate">
											<td name="nodeName"></td>
											<td name="assignee"></td>
											<td name="createTime"></td>
											<td name="endTime"></td>
											<td name="commandMessage"></td>
											<td name="taskComment"></td>
										</tr>
									</tbody>
								</table>
							</div>
							
						</div>
						<div class="tabsFooter">
							<div class="tabsFooterContent"></div>
						</div>
	</div>
	</div>
