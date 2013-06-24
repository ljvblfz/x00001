package com.broadsoft.xmeeting.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.adapter.MemberInfoAdapter;
import com.broadsoft.xmeeting.htmldata.MeetingGuideHtmlDataSupport;

public class MeetingGuideCatalogScheduleInfoActivity extends Activity {
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.meetingguide_catalog_webview);
		//标题
		TextView textView=(TextView)this.findViewById(R.id.systemlabel);
		textView.setText("行程安排"); 
		//内容 
		WebView webView = (WebView)findViewById(R.id.wvInfo);
		webView.setBackgroundColor(0x00000000);
		webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		webView.setWebViewClient(new WebViewClient());
		String htmlBasicInfo = getHtml(); 
		webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null); 
	}

	private String getHtml() {
		String htmlBasicInfo="<html><body style='font-size:30px'>" ;
		

		JSONObject jsonMeetingInfo = getJSONInfo();
		htmlBasicInfo+=MeetingGuideHtmlDataSupport.genSchedule(jsonMeetingInfo);
		htmlBasicInfo+="</body></html>";
		return htmlBasicInfo;
	}

	private JSONObject getJSONInfo() {
		DownloadInfoEntity downloadInfoEntity=EntityInfoHolder.getInstance().getDownloadInfoEntity();
		JSONObject downloadJsonObject=createJSONObject(downloadInfoEntity.getJsonData());
		JSONObject jsonMeetingInfo=null;
		try {
			jsonMeetingInfo=downloadJsonObject.getJSONObject("meetingInfo");
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		return jsonMeetingInfo;
	}
  
	private static JSONObject createJSONObject(String strJson) {
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(strJson);
		} catch (JSONException e) {
//			Log.e(TAG, "[createJSONObject]Error parsing data " + e.toString());
		}
		return jObj;
	}

	
	
	

	
}
