package com.broadsoft.xmeeting.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.htmldata.MeetingGuideHtmlDataSupport;

public class MeetingGuideCatalogActivity extends BaseActivity {

//	private static String url = "http://172.29.135.149/operation/rs/svrEboardnotice?pageNum=1&status=&etype=&lname=&equipmentno=&sname=&roadname=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingguide_activity_main_catalog);
 
		initMeetingBusInfo();
		initMeetingWeatherInfo();
		initMeetingContactInfo();
		initScheduleInfo(); 
		initMemberInfo();
		 
	}
 
	//  navigation button
	private Button btnnavmeetingbusinfo;
	private Button btnnavmeetingweatherinfo;
	private Button btnnavmeetingcontactinfo;
	private Button btnnavmeetingschedule; 
	private Button btnnavmeetingmember;
	
 



	public void initMeetingBusInfo() { 
		btnnavmeetingbusinfo = (Button) findViewById(R.id.btnnavmeetingbusinfo);
		btnnavmeetingbusinfo.setOnClickListener(new Button.OnClickListener() { 
			
			@Override
			public void onClick(View view) {   
				Log.d("Button--->onClick","btnnavmeetingbusinfo");   
//				WebView webView = new WebView(MeetingGuideCatalogActivity.this);
//				webView.setWebViewClient(new WebViewClient());
//				String htmlBasicInfo="<html><body style='font-size:30px'>  " ;
//				htmlBasicInfo+=MeetingGuideHtmlDataSupport.genBus(null);
//				htmlBasicInfo+="</body></html>"; 
//				webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
				//   
				Intent intent = new Intent();
				intent.setClass(MeetingGuideCatalogActivity.this, MeetingGuideCatalogBusInfoActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity 
				
				
			}
 
		});
		 
	}

	public void initMeetingWeatherInfo() { 
		btnnavmeetingweatherinfo = (Button) findViewById(R.id.btnnavmeetingweatherinfo);
		btnnavmeetingweatherinfo.setOnClickListener(new Button.OnClickListener() { 
			
			@Override
			public void onClick(View view) {   
				Log.d("Button--->onClick","btnnavmeetingweatherinfo");  
//				
//				WebView webView = new WebView(MeetingGuideCatalogActivity.this);
//				webView.setWebViewClient(new WebViewClient());
//				String htmlBasicInfo="<html><body style='font-size:30px'>" ;
//				htmlBasicInfo+=MeetingGuideHtmlDataSupport.genWeatherInfo(null);
//				htmlBasicInfo+="</body></html>"; 
//				webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
				//   
				Intent intent = new Intent();
				intent.setClass(MeetingGuideCatalogActivity.this, MeetingGuideCatalogWeatherInfoActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity 
			}
 
		});
		 
	}
	public void initMeetingContactInfo() { 
		btnnavmeetingcontactinfo = (Button) findViewById(R.id.btnnavmeetingcontactinfo);
		btnnavmeetingcontactinfo.setOnClickListener(new Button.OnClickListener() { 
			
			@Override
			public void onClick(View view) {   
				Log.d("Button--->onClick","btnnavmeetingcontactinfo");  
				
				
//
//				WebView webView = new WebView(MeetingGuideCatalogActivity.this);
//				webView.setWebViewClient(new WebViewClient());
//				String htmlBasicInfo="<html><body style='font-size:30px'>联系方式: <br/> " ;
//				htmlBasicInfo+=MeetingGuideHtmlDataSupport.genContact(null);
//				htmlBasicInfo+="</body></html>"; 
//				webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
				//   
				Intent intent = new Intent();
				intent.setClass(MeetingGuideCatalogActivity.this, MeetingGuideCatalogContactInfoActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity 
				
			}
		});
		 
	}
	
	public void initScheduleInfo() { 
		btnnavmeetingschedule = (Button) findViewById(R.id.btnnavmeetingschedule);
		btnnavmeetingschedule.setOnClickListener(new Button.OnClickListener() { 
			@Override
			public void onClick(View view) {  
		
				Log.d("Button--->onClick","btnnavmeetingschedule");  
				

//				WebView webView = new WebView(MeetingGuideCatalogActivity.this);
//				webView.setWebViewClient(new WebViewClient());
//				String htmlBasicInfo="<html><body style='font-size:30px'>" ;
//				htmlBasicInfo+=MeetingGuideHtmlDataSupport.genSchedule(null);
//				htmlBasicInfo+="</body></html>"; 
//				webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
				//   
				Intent intent = new Intent();
				intent.setClass(MeetingGuideCatalogActivity.this, MeetingGuideCatalogScheduleInfoActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity 
				
			}
		});
	}
	 
	
	
	public void initMemberInfo() { 
		btnnavmeetingmember = (Button) findViewById(R.id.btnnavmeetingmember);
		btnnavmeetingmember.setOnClickListener(new Button.OnClickListener() { 
			@Override
			public void onClick(View view) {   
				Log.d("Button--->onClick","btnnavmeetingmember");    
				Intent intent = new Intent();
				intent.setClass(MeetingGuideCatalogActivity.this, MeetingGuideCatalogMemberInfoActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity 
			}  
		});
	}
	
	
	
	
	

	
}
