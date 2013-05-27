package com.broadsoft.xmeeting.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.htmldata.MeetingGuideHtmlDataSupport;

public class MeetingGuideCatalogWeatherInfoActivity extends Activity {
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.meetingguide_catalog_webview);
		//标题
		TextView textView=(TextView)this.findViewById(R.id.systemlabel);
		textView.setText("天气情况"); 
		//内容 
		WebView webView = (WebView)findViewById(R.id.wvInfo);
		webView.setBackgroundColor(0x00000000);
		webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		webView.setWebViewClient(new WebViewClient());
		String htmlBasicInfo="<html><body style='font-size:30px'>" ;
		htmlBasicInfo+=MeetingGuideHtmlDataSupport.genWeatherInfo(null);
		htmlBasicInfo+="</body></html>"; 
		webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null); 
		 
	}
  
	
	
	
	

	
}
