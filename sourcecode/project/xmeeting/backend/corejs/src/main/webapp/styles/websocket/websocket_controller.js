var wsController = null;

function controllerLoginWS() {
	var memberId = findName("memberId").val();
	var memberDisplayName = findName("memberDisplayName").val();
	var meetingId = findName("meetingId").val();

	if (wsController) {
		wsController.close();
		wsController = null;
	}
	wsController = controllerCreateWebSocket(meetingId, memberId, memberDisplayName);
	controllerStartWebSocket(memberId, memberDisplayName);
}

function controllerLogoutWS() {
	controllerClose();
}

function controllerCreateWebSocket(meetingId, memberId, memberDisplayName) {
	var wsController = null;
	if ('WebSocket' in window) {
		wsController = new WebSocket(configuration.wshostprefix
				+ "ws/controller?meetingId=" + meetingId + "&memberId="
				+ memberId + "&memberDisplayName=" + memberDisplayName);
	} else if ('MozWebSocket' in window) {
		wsController = new MozWebSocket(configuration.wshostprefix
				+ "ws/controller?meetingId=" + meetingId + "&memberId="
				+ memberId + "&memberDisplayName=" + memberDisplayName);
	} else {
		alert("not support");
	}
	return wsController;
}

var controllerResponseCount = 0;
function controllerStartWebSocket(memberId, memberDisplayName) {
	wsController.onmessage = function(evt) {
		var obj = JSON.parse(evt.data);
		var content = obj.from + ">>>>" + obj.msgcontent;
		controllerResponseCount++;
		findNameWithParentID("chatrecord", "xmWebSocketDemo").append(
				controllerResponseCount + "***" + content + "<br/>");
	};

	wsController.onclose = function(evt) {
		findNameWithParentID("chatrecord", "xmWebSocketDemo").append(
				"<span style='color:red'>" + memberDisplayName
						+ ",你已退出呼叫服务系统!!!" + "</span><br/>");
	};

	wsController.onopen = function(evt) {
		findNameWithParentID("chatrecord", "xmWebSocketDemo").append(
				"<span style='color:red'>" + memberDisplayName
						+ "你好,欢迎进入呼叫服务系统!!!" + "</span><br/>");
	};
}// end of startWebSocket

function controllerClose() {
	wsController.close();
}

function controllerSendMsg() {
	var message = new Object(); 
	var meetingId = findName("meetingId").val();
	var memberId = findName("memberId").val();
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
	message.meetingid = meetingId;
	message.msgtype = msgtype;
	message.msgcontent = msgcontent;
	message.from = memberId;
	message.to = to;
	logger.info("sendMsg content---->" + JSON.stringify(message));
	wsController.send(JSON.stringify(message));
}