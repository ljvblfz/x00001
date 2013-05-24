var wsDownload = null;

function downloadLoginWS(padId) {
	//padId = "system"; // 用户名
	logger.info("downloadLoginWS begin");
	var roleName = "personnel";// 人员
	if (wsDownload) {
		wsDownload.close();
		wsDownload = null;
	}
	wsDownload = downloadCreateWebSocket(padId,roleName);
	downloadStartWebSocket(padId, roleName);
	logger.info("downloadLoginWS end");
	
}

function downloadLogoutWS() {
	logger.info("downloadLogoutWS begin");
	downloadClose();
	logger.info("downloadLogoutWS end");
}

function downloadCreateWebSocket(padId, roleName) {
	var url = configuration.wshostprefix + "ws/download?padId=" + padId
			+ "&roleName=" + roleName;
	var wsDownload = null;
	if ('WebSocket' in window) {
		wsDownload = new WebSocket(url);
	} else if ('MozWebSocket' in window) {
		wsDownload = new MozWebSocket(url);
	} else {
		alert("not support");
	}
	return wsDownload;
}

var downloadResponseCount = 0;
function downloadStartWebSocket(padId, roleName) {
	wsDownload.onmessage = function(evt) {
		// 在服务器端向客户端发送消息时调用 
		downloadResponseCount++;
		logger.info("onmessage>>>>" + evt.data);
	};
	
	wsDownload.onerror = function(evt) { 
	    // 在出现错误时调用，例如在连接断掉时 
		logger.info("onerror>>>>" + evt.data);
	}; 
	
	wsDownload.onclose = function(evt) {
		 // 在连接被关闭时调用 
		logger.info("onclose>>>>退出系统"+ evt.data);
	};

	wsDownload.onopen = function(evt) {
		// 连接被打开时调用
		logger.info("onopen>>>>进入系统"+ evt.data);
	};
}// end of downloadStartWebSocket

function downloadClose() {
	wsDownload.close();
}

function downloadSendMsg(meetingId,to) {
	var message = new Object(); 
	//
	message.meetingid = meetingId;
	//下载
	message.msgtype = '01';  
	message.to = to;
	logger.info("downloadSendMsg content: " + JSON.stringify(message));
	wsDownload.send(JSON.stringify(message));
}



function activateSendMsg(meetingId,to) {
	var message = new Object(); 
	//
	message.meetingid = meetingId;
	//下载
	message.msgtype = '02';  
	message.to = to;
	logger.info("activateSendMsg content: " + JSON.stringify(message));
	wsDownload.send(JSON.stringify(message));
}



function padinfoSendMsg(to) {
	var message = new Object(); 
	// 
	//下载
	message.msgtype = '03';  
	message.to = to;
	logger.info("padinfoSendMsg content: " + JSON.stringify(message));
	wsDownload.send(JSON.stringify(message));
}




function companyinfoSendMsg(to) {
	var message = new Object();  
	//下载
	message.msgtype = '04';  
	message.to = to;
	logger.info("companyinfoSendMsg content: " + JSON.stringify(message));
	wsDownload.send(JSON.stringify(message));
}