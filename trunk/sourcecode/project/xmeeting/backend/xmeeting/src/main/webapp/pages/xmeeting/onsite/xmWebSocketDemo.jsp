<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.founder.sipbus.syweb.au.util.*"%>
<%@ page import="com.founder.sipbus.syweb.au.po.SysUser"%>


<%
	//SysUser sysUser = SsoUtil.getLoginUser(request);
	//Sting userid = sysUser.getUserid();
	//System.out.println("userid------>" + userid);
%>

<h2 class="contentTitle" name="showTitle">实时监控</h2>


<form name="pageForm" method="post" class="required-validate pageForm">
	<div class="pageFormContent" layoutH="600"> 
		<p><label>用户ID:</label> <input type="text" name="memberId" class="required"   maxlength="50" value="system"/></p> 
		<p><label>用户名称:</label> <input type="text" name="memberDisplayName" class="required"  maxlength="50" value="system(服务员)"/></p>
		<p><label>会议ID:</label> <input type="text" name="meetingId" class="required"   maxlength="50" value="000000000XMMEETINGINFO13041820484043"/></p>
	</div>

	<div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="loginWS()">登陆</button>
					</div>
				</div>
			</li> 
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="logoutWS()">登出</button>
					</div>
				</div>
			</li> 
		</ul>
	</div>
</form>


<form name="pageForm" method="post" class="required-validate pageForm">
	<div class="pageFormContent" layoutH="450">
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
	<div name="chatrecord" style="height: 350px; width: 100%; overflow-y: scroll;"></div>

</div>
<script type="text/javascript">
 
	$(function() {
	});
	
	
	
</script>