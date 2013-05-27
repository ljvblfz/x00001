package com;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.broadsoft.xmeeting.DesktopActivity;

public class Test {

	private String meetingId = "000000000XMMEETINGINFO13041820484043";
	private String serverIP = "http://192.168.1.103:80/meeting/a.jsp?";

	private void initMeetingData() {
		// 第一个执行方法
		HttpGet request = new HttpGet(serverIP
				+ "/xmeeting/rs/open/meetingallinfo/download/xmmiGuid/"
				+ meetingId);
		// 先封装一个 JSON 对象
		JSONObject param = new JSONObject();
		// param.put("name", "rarnu");
		// param.put("password", "123456");
		// 绑定到请求 Entry
		// StringEntity se = new StringEntity(param.toString());
		// request.setEntity(se);
		// 发送请求
		try {
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(request);
			// 得到应答的字符串，这也是一个 JSON 格式保存的数据
			String retSrc = EntityUtils.toString(httpResponse.getEntity());
			// 生成 JSON 对象
			JSONObject result = new JSONObject(retSrc);
		} catch (Exception ae) {
			ae.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Test s = new Test();
		s.initMeetingData();

	}

}
