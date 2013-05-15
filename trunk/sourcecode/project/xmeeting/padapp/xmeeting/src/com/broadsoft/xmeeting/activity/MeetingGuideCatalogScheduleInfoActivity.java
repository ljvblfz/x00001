package com.broadsoft.xmeeting.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.adapter.MemberInfoAdapter;
import com.broadsoft.xmeeting.htmldata.MeetingGuideHtmlDataSupport;

public class MeetingGuideCatalogScheduleInfoActivity extends BaseActivity {
 

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
		String htmlBasicInfo="<html><body style='font-size:30px'>" ;
		htmlBasicInfo+=MeetingGuideHtmlDataSupport.genSchedule(null);
		htmlBasicInfo+="</body></html>"; 
		webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
		 
	}
  
	
	
	
	

	
}
