<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.founder.sipbus.syweb.au.util.*"%>
<%@ page import="com.founder.sipbus.syweb.au.po.SysUser"%>


<%
	SysUser sysUser = SsoUtil.getLoginUser(request);
	String userid = sysUser.getUserid();
	System.out.println("userid------>" + userid);
%>

<h2 class="contentTitle" name="showTitle">实时服务</h2>
<form name="pageForm" method="post" class="required-validate pageForm">
	<div class="pageFormContent" layoutH="400">
		<p>
			<label>类型:</label> <select    name="msgtype" class="required"> 
				<option value="01">呼叫服务</option>
				<option value="02">消息服务</option>
				<option value="03">投票服务</option>
				<option value="04">控制服务</option>
			</select>
		</p> 
		<p><label>接收者:</label> <input type="text" name="to" class="required" size="50" maxlength="50" /></p>
	 
		<p>
			<label>内容:</label> <input type="text" name="msgcontent"
				class="required" size="50" maxlength="50" />
		</p>
	</div>

	<div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="sendMsg()">发送</button>
					</div>
				</div></li>
			<li><div class="button">
					<div class="buttonContent">
						<button type="button" class="close">关闭</button>
					</div>
				</div></li>
		</ul>
	</div>
</form>

<!-- class="pageContent" -->
<div id="xmWebSocketDemo">
	<div name="chatrecord"
		style="height: 350px; width: 100%; overflow-y: scroll;"></div>

</div>
<script type="text/javascript">

	var memberId='<%=userid%>';
	var meetingId='000000000XMMEETINGINFO13041820484043';
	var ws = null;
	$(function() {
		if (ws) {
			ws.close();
			ws = null;
		}
		ws = createWebSocket(meetingId,memberId);
		startWebSocket(memberId);
	});

	function createWebSocket(meetingId,memberId) {
		var ws = null;
		if ('WebSocket' in window) {
			ws = new WebSocket(configuration.wshostprefix
					+ "ws/controller?meetingId="+meetingId+"&memberId=" + memberId);
		} else if ('MozWebSocket' in window) {
			ws = new MozWebSocket(configuration.wshostprefix
					+ "ws/controller?meetingId="+meetingId+"&memberId=" + memberId);
		} else {
			alert("not support");
		}
		return ws;
	}

	
	var responseCount=0;
	function startWebSocket(name) {
		ws.onmessage = function(evt) {
			//alert(evt.data); 
			var obj=JSON.parse(evt.data);
			var content=obj.from+">>>>"+obj.msgcontent;
			responseCount++;
			findNameWithParentID("chatrecord", "xmWebSocketDemo").append( responseCount+"***"+content + "<br/>");
		};

		ws.onclose = function(evt) {
			//alert("close");
			findNameWithParentID("chatrecord", "xmWebSocketDemo").append( "<span style='color:red'>"+name + ",你已退出呼叫服务系统!!!" + "</span><br/>");
		};

		ws.onopen = function(evt) {
			//alert("open");
			findNameWithParentID("chatrecord", "xmWebSocketDemo").append("<span style='color:red'>"+name + "你好,欢迎进入呼叫服务系统!!!" + "</span><br/>");
		};
	}

	function close() {
		ws.close();
	}

	function sendMsg() {
		var message=new Object();
		var msgtype = findName("msgtype").val();
		logger.info("sendMsg msgtype--1-->" + msgtype);
		if (msgtype.length == 0) {
			return;
		}
		var msgcontent = findName("msgcontent").val();
		logger.info("sendMsg msgcontent--2-->" + msgcontent);
		if (msgcontent.length == 0) {
			return;
		} 
		var to = findName("to").val(); 
		if (to.length == 0) {
			return;
		} 
		//
		message.meetingid=meetingId;
		message.msgtype=msgtype;
		message.msgcontent=msgcontent;
		message.from=memberId;
		message.to=to;
		logger.info("sendMsg content---->" + JSON.stringify(message));
		ws.send( JSON.stringify(message));
	}
</script>