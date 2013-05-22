<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.net.URLEncoder"%>
<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.founder.sipbus.common.util.RsaKey"%>
<%@page import="com.founder.sipbus.syweb.au.util.SsoUtil"%>
<%@page import="com.founder.sipbus.syweb.au.util.MemcachedUtil"%>
<%@page import="com.founder.sipbus.syweb.au.util.ISSOManager"%>  
<%@ page import="com.founder.sipbus.syweb.au.po.SysUser"%>

<%
	SysUser sysUser =null;
	String userid ="undefined";
	try{
		sysUser = SsoUtil.getLoginUser(request);
		
		if(null!=sysUser){
			userid = sysUser.getUserid(); 
		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="chrome=1" />
<title>方正智能公交</title>


<link  href="styles/jquery-ui-1.8.18/themes/base/jquery.ui.all.css" rel="stylesheet">
<link  href="styles/js/jquery.autocomplete.css" rel="stylesheet">

<link href="styles/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/> 
<link href="styles/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="styles/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>

<link href="styles/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="styles/jquerywindow/css/jquery.window.css" rel="stylesheet" type="text/css" media="screen"/> 

<!--jquery.jqGrid-4.3.1--> 
<link href="styles/jquery.jqGrid-4.3.1/plugins/ui.multiselect.css" rel="stylesheet" type="text/css"> 
<link href="styles/jquery.jqGrid-4.3.1/css/ui.jqgrid.css" rel="stylesheet" type="text/css">  

<link href="styles/zTreev3.3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css">   
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
<style type="text/css">
	#header{height:50px}
	#leftside, #container, #splitBar, #splitBarProxy{top:52px}
</style>


<script type="text/javascript"> 
	var mxBasePath = 'styles/mxGraph_1_10_4_2/src';
</script>

<!-- <script type="text/javascript" src="styles/mxGraph_1_10_4_2/src/js/mxClient.js"></script> -->
<script type="text/javascript" src="styles/mxGraph_1_10_4_2/src/jsmin/mxClient.min.js"></script>


<!-- extend core js begin
<script src="styles/json/json2.js" type="text/javascript"></script>--> 
<script src="styles/xdate-0.7/xdate.js" type="text/javascript"></script>


<script src="/corejs/styles/jscore/jscore.config.js" type="text/javascript"></script>
<script src="/corejs/styles/jscore/jscore.common.js" type="text/javascript"></script>
<script src="/corejs/styles/jscore/jscore.array.extend.js" type="text/javascript"></script>
<script src="/corejs/styles/jscore/jscore.string.extend.js" type="text/javascript"></script>
<script src="/corejs/styles/jscore/util.table.js" type="text/javascript"></script>
<script src="/corejs/styles/json/json2.js" type="text/javascript"></script>


<script src="/corejs/styles/websocket/websocket_controller.js" type="text/javascript"></script>
<script src="/corejs/styles/websocket/websocket_download.js"   type="text/javascript"></script>


<script src="styles/util/cursorposition.js" type="text/javascript"></script>


<!-- extend core js end--> 

<script src="styles/js/speedup.js" type="text/javascript"></script>

<script src="styles/js/jquery-1.7.1.js" type="text/javascript"></script> 
<script src="styles/jquery-ui-1.8.18/jquery-ui.min.1.8.17.js" type="text/javascript" ></script>

<script src="styles/js/jquery.cookie.js" type="text/javascript"></script>
<script src="styles/js/jquery.rotate.1-1.js" type="text/javascript"></script>
<script src="styles/js/jquery.validate.js" type="text/javascript"></script>
<script src="styles/js/jquery.bgiframe.js" type="text/javascript"></script> 
<script src="styles/js/jquery.rotate.1-1.js" type="text/javascript"></script> 
<script src="styles/js/jquery.fullscreen.js" type="text/javascript"></script> 
<script src="styles/jquerywindow/jquery.window.js" type="text/javascript"></script>
<!-- <script src="styles/jquery.dform/dist/jquery.dform-1.0.1.min.js" type="text/javascript"></script> -->
<script src="styles/jquery.dform/src/jquery.dform-1.0.1.js" type="text/javascript"></script>
<script src="styles/js/jquery.autocomplete.pack.js" type="text/javascript"></script>



<script src="styles/jquery.jqGrid-4.3.1/plugins/ui.multiselect.js" type="text/javascript"></script>   
<script src="styles/jquery.jqGrid-4.3.1/js/i18n/grid.locale-cn.js" type="text/javascript"></script> 
<script src="styles/jquery.jqGrid-4.3.1/js/jquery.jqGrid.src.js" type="text/javascript"></script> 
<script src="styles/jquery-option-tree-1.3/jquery.optionTree.js" type="text/javascript"></script> 



<script src="styles/draw/raphael/2.1.0/raphael.js" type="text/javascript"></script> 
<script src="styles/draw/threejs/three.min.js" type="text/javascript"></script> 

<!-- smartdatacontrol -->

<script src="styles/smartfwk/smartdatacontrol/jcommon_jqGrid-basic.js" type="text/javascript"></script> 
	

<script src="styles/amcharts_2.8.2/amcharts/amcharts.js" type="text/javascript"></script>     
<!-- dwz -->
<script src="styles/xheditor/xheditor-1.1.12-zh-cn.min.js" type="text/javascript"></script>
<script src="styles/uploadify/scripts/swfobject.js" type="text/javascript"></script>
<script src="styles/uploadify/scripts/jquery.uploadify.v2.1.0.js" type="text/javascript"></script>

<script src="styles/js/founder.globalvariable.js" type="text/javascript"></script>
<script src="styles/js/populate.js" type="text/javascript"></script>
<script src="styles/js/jquery.populate.js" type="text/javascript"></script>
<script src="styles/js/dwz.core.js" type="text/javascript"></script>
<script src="styles/js/dwz.util.date.js" type="text/javascript"></script>
<script src="styles/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="styles/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="styles/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="styles/js/dwz.drag.js" type="text/javascript"></script>
<script src="styles/js/dwz.tree.js" type="text/javascript"></script>
<script src="styles/js/dwz.accordion.js" type="text/javascript"></script>
<script src="styles/js/dwz.ui.js" type="text/javascript"></script>
<script src="styles/js/dwz.theme.js" type="text/javascript"></script>
<script src="styles/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="styles/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="styles/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="styles/js/dwz.navTab.js" type="text/javascript"></script>
<script src="styles/js/dwz.tab.js" type="text/javascript"></script>
<script src="styles/js/dwz.resize.js" type="text/javascript"></script>
<script src="styles/js/dwz.dialog.js" type="text/javascript"></script>
<script src="styles/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="styles/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="styles/js/dwz.stable.js" type="text/javascript"></script>
<script src="styles/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="styles/js/dwz.ajax.js" type="text/javascript"></script>
<script src="styles/js/dwz.pagination.js" type="text/javascript"></script>
<script src="styles/js/dwz.database.js" type="text/javascript"></script>
<script src="styles/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="styles/js/dwz.effects.js" type="text/javascript"></script>
<script src="styles/js/dwz.panel.js" type="text/javascript"></script>
<script src="styles/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="styles/js/dwz.history.js" type="text/javascript"></script>
<script src="styles/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="styles/js/dwz.combox.js" type="text/javascript"></script>

<script src="styles/jshashtable-2.1/jshashtable_src.js" type="text/javascript"></script>
<script src="styles/jshashtable-2.1/jshashset_src.js" type="text/javascript"></script>
 

  
 
 
<script src="styles/js/createTree.js" type="text/javascript"></script>

<!--<script src="styles/js/xjs.min.js" type="text/javascript"></script>-->

<script src="styles/js/xjs.regional.zh.js" type="text/javascript"></script>  


<script src="styles/smartfwk/cck/cck.form.js" type="text/javascript"></script>
<script src="styles/smartfwk/cck/cck.grid.js" type="text/javascript"></script>
<script src="styles/subwayMap_Plugin_0.5.0/jquery.subwayMap-0.5.0.js" type="text/javascript"></script> 
<script src="styles/zTreev3.3/js/jquery.ztree.all-3.3.min.js" type="text/javascript"></script> 


<!--<canvas name="cvss" style="display:none"></canvas> -->
<script type="text/javascript">
$(function(){
	DWZ.init("styles/xjs.frag.xml", {
		//loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			navTab._scrollTab(0,0);
			$("#themeList").theme({themeBase:"styles/themes"}); // themeBase 相对于index页面的主题base路径
		}		
	});
	
	var resizing=false;
     document.onkeydown = function(e){
         var oEvent = window.event;   
         if(!oEvent)
         	oEvent=e;
         if (oEvent.keyCode == 38 && oEvent.altKey) {   
         	if(resizing) return;
         	resizing=true;
             if(!$("#header").is(":hidden")){
             	//var heightValue=$("div.gridScroller", this).height()+50;
             	$("#leftside").show().animate({top: 2 }, 500 ,function(){$("#header").hide();resizing=false;});
             	$("#container").show().animate({top: 2 }, 500);
             	//$("div.gridScroller", this).height(heightValue+"px");
             	
             	$("div.gridScroller", this).each(function(){
             		if(!$(this).attr('customHeight'))
             			$(this).height(($(this).height()+50)+"px");
             	});
             	
             	if(!$.browser.msie)
	             	$(".addHeight", this).each(function(){
	             		$(this).height(($(this).height()+50)+"px");
	          		});
             	
             	var h2=$("div.navTab-panel.tabsPageContent.layoutBox", this).height()+50;
             	$("div.navTab-panel.tabsPageContent.layoutBox", this).height(h2+"px");
             	
             	if(!$(".toggleCollapse div","#sidebar").is(":hidden"))
             		$(".toggleCollapse div","#sidebar").click();
				$(window).trigger("resizeGrid");
             }
             else{
             	var heightValue=$("div.gridScroller", this).height()-50;
             	$("#leftside").show().animate({top: 52}, 500);
             	$("#container").show().animate({top: 52}, 500,function(){$("#header").show();resizing=false;});
             	//$("#navTab").css('height',heightValue);
             	
             	if($(".toggleCollapse div","#sidebar").is(":hidden"))
             		$(".toggleCollapse div","#sidebar_s").click();
             		
             	//$("div.gridScroller", this).height(heightValue+"px");
             	
             	$("div.gridScroller", this).each(function(){
             		if(!$(this).attr('customHeight'))
             			$(this).height(($(this).height()-50)+"px");
             	});
             	if(!$.browser.msie)
	             	$(".addHeight", this).each(function(){
	             		$(this).height(($(this).height()-50)+"px");
	             	});
             	
             	var h2=$("div.navTab-panel.tabsPageContent.layoutBox", this).height()-50;
             	$("div.navTab-panel.tabsPageContent.layoutBox", this).height(h2+"px");
				$(window).trigger("resizeGrid");
				
             }
		 }   
     };
     if(configuration.workflow_enabled=='true'){
    	 $("#index_mytasktodo").show();
     	getMyTasks();
     }else{
    	 $("#index_home_page").html("<img src='img/"+configuration.homeimage+"' width='800' height='500' />");
     }
     
     initTopNav();
	 
	 
	 //websocket
	 var loginName='<%=userid%>';
	 downloadLoginWS(loginName);
});

	function doLogout(){
		$.ajax({
			type: 'DELETE',
			url:'rs/login',
			cache: false,
			success: logoutSuccess,
			error: DWZ.ajaxError
		});
	}
	
	function logoutSuccess(){
	 	//websocket logout
	 	downloadLogoutWS();
		//
	 	window.top.location="login.html";
	}

	$(function(){
		//ajax提交获取表单数据
		showLoading=false;
		doGetAjax(CPATH.domain_1 + "/rs/usermainmenu",{},
				function(data){
					var html = '';
					$.each(data,function(childIndex,child){
						if(childIndex === 0){
							html += '<li class="selected"><a href="javascript:CPATH.domain_1' + '+\'/rs/usermenu/' + child.menuId + '\'"><span>' + child.menuName + '</span></a></li>';
						}else {
							html += '<li><a href="javascript:CPATH.domain_1' + '+\'/rs/usermenu/' + child.menuId + '\'"><span>' + child.menuName + '</span></a></li>';
						}
					});
					$("#navMenu ul").append(html).navMenu();
					
					$("#navMenu ul").find("li.selected>a").click();
			});
	});
 
	
	
	function aboutDialog(){  
		//
		var title = "关于";
		var rel = "aboutdialog";
		var options = {}; 
		options.width = 400;
		options.height = 300;
		options.max =false;
		options.mask = true;
		options.maxable = false;
		options.minable = false;
		options.fresh = true;
		options.resizable = false;
		options.drawable = true;
		options.close = "";
		options.param = {};
		options.zIndex = "1000";

		var url = "/syWeb/pages/system/about/aboutdialog.html";
 
		$.pdialog.open(url, rel, title, options);
		
	}//end of showLineChooserDialog
	
function kaoqindaka(){
		doPostAjax("/syWeb/rs/hrAttendencerecord",{},function(data){
			//不是调度员
			if(data.isin=='noin'){
			
				alertMsg.correct("打卡成功");
			}
			//是调度员
			if(data.isin=='true'){
				alertMsg.correct("打卡成功");
			}
		if(data.isin == 'false'){
				
		var title = "替换登签人";
		var rel = "aboutdialog";
		var options = {}; 
		options.width = 550;
		options.height = 490;
		options.max =false;
		options.mask = true;
		options.maxable = false;
		options.minable = false;
		options.fresh = true;
		options.resizable = false;
		options.drawable = true;
		options.close = "";
		options.param = {};
		options.zIndex = "1000";

		var url = "/syWeb/pages/auth/hrattendancerecordreplace.html";
 
		$.pdialog.open(url, rel, title, options);	
			}
		
		});
		
	}
	
	
	function changePwdDialog(){
	
		var title = "修改密码";
		var rel = "changepwddialog";
		var options = {}; 
		options.width = 400;
		options.height = 200;
		options.max =false;
		options.mask = true;
		options.maxable = false;
		options.minable = false;
		options.fresh = true;
		options.resizable = false;
		options.drawable = true;
		options.close = "";
		options.param = {};
		options.zIndex = "1000";

		var url = "/syWeb/pages/system/user/changepwd.html";
 
		$.pdialog.open(url, rel, title, options);
	
	}

	function initTopNav(){
		var html="";
		if(configuration.topnav_enabled=='true'){
			html+=" <li><a href='/gisapp/pages/gisapp/gisapp.html'  target='_blank'>GIS应用</a></li> ";
			html+=" <li><a href='/dispatch/pages/selfservice/customerservice.html'  target='_blank'>便民服务</a></li> ";
			html+=" <li><a href='/dispatch/pages/showroom/ipadScreen.html'  target='_blank'>线路展示</a></li> ";
			html+=" <li><a href='/dispatch/pages/showroom/displayScreen.html'  target='_blank'>展示大厅</a></li> ";
		}
		// 
		html+=" <li><a href='javascript:aboutDialog()'  >关于</a></li> ";
		html+=" <li><a href='javascript:changePwdDialog()'  >修改密码</a></li> ";
		html+=" <li><a href='javascript:doLogout()'>退出</a></li> "; 
		$(".nav").html(html);
		$(".logo").css("background","url('styles/themes/default/images/"+configuration.logo_image+"') no-repeat scroll 0 0 transparent");
		$("title").html(configuration.app_title);
	}
</script>
</head>

<body scroll="no">
	<div id="layout">
	
		<div id="header" style="height: 50px">
			<div class="headerNav">
				<a class="logo" href="index.jsp">标志</a>
				<ul class="nav">
					<li><a href="/gisapp/pages/gisapp/gisapp.html"  target="_blank">GIS应用</a></li>
					<li><a href="/dispatch/pages/selfservice/customerservice.html"  target="_blank">便民服务</a></li>
					<li><a href="/dispatch/pages/showroom/ipadScreen.html"  target="_blank">线路展示</a></li>
					<li><a href="/dispatch/pages/showroom/displayScreen.html"  target="_blank">展示大厅</a></li>
					<li><a href="javascript:changePwdDialog()" >修改密码</a></li>
					<li><a href="javascript:aboutDialog()"  >关于</a></li>
					<li><a href="javascript:doLogout()">退出</a></li>
				</ul>
				<ul class="themeList" id="themeList">
				<!--
						<li>
					<input name="ImageButton2" title="请点击此处查看当天登签人"  type="image" src="styles/themes/default/images/user16.gif" border="0" 
					onclick="checkWorkMan();"
					/>
					&nbsp;&nbsp;&nbsp;
					</li>
					<li>
					<input name="ImageButton1" title="请点击此处进行考勤打卡"  type="image" src="styles/themes/default/images/flowico4.gif" border="0" 
					onclick="kaoqindaka();"
					/>
					&nbsp;&nbsp;&nbsp;
					</li>
					-->
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul>
                <!-- navMenu -->
                <div id="navMenu">
                    <ul>
                    </ul>
                </div>
			</div>

		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
				<div class="accordion" fillSpace="sidebar">
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab" >
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox" id="founderNavTabPanel" >
					<div class="page unitBox">
						<div class="pageContent" layouth="42"
							style="height: 670px; overflow: auto;"  id="index_home_page">
							
							
						<div style="width:60%;margin:2px;float:left;min-height:100px;display: none;" id="index_mytasktodo">
								<div class="panel" defH="300">
										<h1>我的待办<div title="刷新" style="padding-top:13px;padding-right:15px;float:right;background: url('/syWeb/styles/themes/default/images/(13,12).png') no-repeat center left;cursor:pointer" onclick="getMyTasks()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div></h1>
										
									<div  name="bbb">
										<table class="list" width="98%">
											<thead>
												<tr>
													<th width="15%">流程名称</th>
													<th width="50%">标题</th>
													<th width="20%">到达时间</th>
													<th width="15%">发起人</th>
												</tr>
											</thead>
											<tbody id="homeMyTask">
											</tbody>
										</table>
									</div>
								</div>
							</div> 
							<!-- <button name="" onclick="startTestProcess();">启动测试流程</button>	 -->
							<!-- <button name="" onclick="getMyTasks();">查找我的待办</button>	 -->
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	
 	<div id="opFooter">
 		<div id="time" style="float: right;padding-right: 20px;">加载中...</div>
<!-- 		<ul class="nav"> -->
<!-- 		<!--  -->
<!-- 			<li><a href="/dispatch/pages/showroom/gisScreen.html"  target="_blank">监控中心</a></li> -->
<!-- 		 -->  
<!-- 			<li><a href="/dispatch/pages/showroom/displayScreen.html"  target="_blank">展示大厅</a></li>  -->
<!-- 		</ul> -->
	</div> 
<!-- 	<div id="footer">苏州工业园区公共交通有限公司 版权所有© 1996-2012</div> -->

</body>
<script type="text/javascript">
	function startTestProcess(){
		doPostAjax(getURL(CPATH.domain_1,"/rs/workflow"),{businessKey:"602975",processKey:"process_1"},function(data){
			alert("启动成功");
		});
	}

	function openProcessTab(formUri,nodeId,instId,taskId,bizKey,defId){
		var url="pages/workflow/workflowprocess.jsp";
		var data={formUri:formUri,nodeId:nodeId,taskId:taskId,instId:instId,bizKey:bizKey,defId:defId};
		navTab.openTab("workflowprocess", url,
				{title:"流程处理", fresh:"false", external:false,data:data});
	}
		function checkWorkMan(){
		var title = "查看当日登签人";
		var rel = "aboutdialog";
		var options = {}; 
		options.height = 490;
		options.max =false;
		options.mask = true;
		options.maxable = false;
		options.minable = false;
		options.fresh = true;
		options.resizable = false;
		options.drawable = true;
		options.close = "";
		options.param = {};
		options.zIndex = "1000";

		var url = "/syWeb/pages/auth/hrattendanceCheckWorkMan.html";
 
		$.pdialog.open(url, rel, title, options);
	
	}
	
	function getMyTasks(){
		//查找我的待办
		doGetAjax(getURL(CPATH.domain_1,"/rs/workflowtasks"),{},
				function(respJson){
					var grid=$("#homeMyTask");
					grid.html("");
					var tr;
					$.each(respJson.jsonData,function(i,data){
						tr=$("<tr ><td>"+data.processDefinitionName+"</td><td onclick='openProcessTab(\""+data.formUri+"\",\""+data.nodeId+"\",\""+data.processInstanceId+"\",\""+data.taskInstanceId+"\",\""+data.bizKey+"\",\""+data.processDefinitionId+"\")'>"+data.PI_SUBJECT+"</td><td>"+data.createTime+"</td><td>"+data.PI_INITIATOR+"</td></tr>");
						tr.appendTo(grid);
					});
					if(tr){
						tr.pagnationStyle();
					}
				}
		);
	}
			var initTime = undefined;
			(function(){
				var innerHtml = '{0}:{1}:{2}';
				var beijingTimeZone = 8;
				function format(str, json){
					return str.replace(/{(\d)}/g, function(a, key) {
						return json[key];
					});
				}
				function p(s) {
					return s < 10 ? '0' + s : s;
				}
				window.server_time = 	function(time){	
					initTime = time;
					show();
					setInterval(function(){
						initTime += 1000;
						show();
					}, 1000);
					setInterval(function(){
						//半个小时同服务器同步一次时间
						showLoading=false;
						$.ajax({
							type: 'GET',
							url:'currenttime.jsp',
							cache: false,
							success: function(data){initTime=parseInt(data);},
							error: function(){}
						});
					}, 1800000);
				}
				function show(){
					var timeOffset = ((-1 * (new Date()).getTimezoneOffset()) - (beijingTimeZone * 60)) * 60000;
					var now = new Date(initTime - timeOffset);
					document.getElementById('time').innerHTML = '当前时间:<font color="0099ff" style="font-family:Verdana;color:0099ff;font-size:20px; ">'+format(innerHtml, [p(now.getHours()), p(now.getMinutes()), p(now.getSeconds())])+'</font>';
				}
				window.server_time(<%=System.currentTimeMillis()%>);
				
				//getMyTasks();
			})();
</script>
</html>
