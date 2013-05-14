package com.broadsoft.xmeeting.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.adapter.MemberInfoAdapter;
import com.broadsoft.xmeeting.htmldata.MeetingGuideHtmlDataSupport;

public class MeetingGuideActivity extends BaseActivity {

//	private static String url = "http://172.29.135.149/operation/rs/svrEboardnotice?pageNum=1&status=&etype=&lname=&equipmentno=&sname=&roadname=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingguide_activity_main);

		meetingcontent = (LinearLayout) findViewById(R.id.meetingcontent);
		initMeetingBusInfo();
		initMeetingWeatherInfo();
		initMeetingContactInfo();
		initScheduleInfo();
//		initMealsHotel();
		initMemberInfo();
		
		initBtnBackgroundColor();
	}

	private LinearLayout meetingcontent;
	//top navigation button
	private Button btnnavmeetingbusinfo;
	private Button btnnavmeetingweatherinfo;
	private Button btnnavmeetingcontactinfo;
	private Button btnnavmeetingschedule;
//	private Button btnnavmeetingmealshotel;
	private Button btnnavmeetingmember;
	
	
	
	public void initBtnBackgroundColor(){
		btnnavmeetingbusinfo.setBackgroundResource(R.drawable.button_shape_normal);
		btnnavmeetingweatherinfo.setBackgroundResource(R.drawable.button_shape_normal);
		btnnavmeetingcontactinfo.setBackgroundResource(R.drawable.button_shape_normal);
		btnnavmeetingschedule.setBackgroundResource(R.drawable.button_shape_normal);
//		btnnavmeetingmealshotel.setBackgroundResource(R.drawable.button_shape_normal);
		btnnavmeetingmember.setBackgroundResource(R.drawable.button_shape_normal);
	}



	public void initMeetingBusInfo() { 
		btnnavmeetingbusinfo = (Button) findViewById(R.id.btnnavmeetingbusinfo);
		btnnavmeetingbusinfo.setOnClickListener(new Button.OnClickListener() { 
			
			@Override
			public void onClick(View view) {  
				initBtnBackgroundColor(); 
				btnnavmeetingbusinfo.setBackgroundResource(R.drawable.button_shape_pressed); 
				Log.d("Button--->onClick","btnnavmeetingbusinfo"); 
//				LinearLayout personalinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.meetingguide_businfo, null);
//				meetingcontent.removeAllViewsInLayout();
//				meetingcontent.addView(personalinfo); 
				

				WebView webView = new WebView(MeetingGuideActivity.this);
				webView.setWebViewClient(new WebViewClient());
				String htmlBasicInfo="<html><body style='font-size:30px'>  " ;
				htmlBasicInfo+=MeetingGuideHtmlDataSupport.genBus(null);
				htmlBasicInfo+="</body></html>"; 
				webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
				//  
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(webView); 
			}
 
		});
		 
	}

	public void initMeetingWeatherInfo() { 
		btnnavmeetingweatherinfo = (Button) findViewById(R.id.btnnavmeetingweatherinfo);
		btnnavmeetingweatherinfo.setOnClickListener(new Button.OnClickListener() { 
			
			@Override
			public void onClick(View view) {  
				initBtnBackgroundColor(); 
				btnnavmeetingweatherinfo.setBackgroundResource(R.drawable.button_shape_pressed); 
				Log.d("Button--->onClick","btnnavmeetingweatherinfo"); 
//				LinearLayout weatherinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.meetingguide_weatherinfo, null);
//				meetingcontent.removeAllViewsInLayout();
//				meetingcontent.addView(weatherinfo); 
				
				WebView webView = new WebView(MeetingGuideActivity.this);
				webView.setWebViewClient(new WebViewClient());
				String htmlBasicInfo="<html><body style='font-size:30px'>" ;
				htmlBasicInfo+=MeetingGuideHtmlDataSupport.genWeatherInfo(null);
				htmlBasicInfo+="</body></html>"; 
				webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
				//  
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(webView); 
			}
 
		});
		 
	}
	public void initMeetingContactInfo() { 
		btnnavmeetingcontactinfo = (Button) findViewById(R.id.btnnavmeetingcontactinfo);
		btnnavmeetingcontactinfo.setOnClickListener(new Button.OnClickListener() { 
			
			@Override
			public void onClick(View view) {  
				initBtnBackgroundColor(); 
				btnnavmeetingcontactinfo.setBackgroundResource(R.drawable.button_shape_pressed); 
				Log.d("Button--->onClick","btnnavmeetingcontactinfo");  
				
				

				WebView webView = new WebView(MeetingGuideActivity.this);
				webView.setWebViewClient(new WebViewClient());
				String htmlBasicInfo="<html><body style='font-size:30px'>联系方式: <br/> " ;
				htmlBasicInfo+=MeetingGuideHtmlDataSupport.genContact(null);
				htmlBasicInfo+="</body></html>"; 
				webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
				//  
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(webView); 
				
			}
		});
		 
	}
	
	public void initScheduleInfo() { 
		btnnavmeetingschedule = (Button) findViewById(R.id.btnnavmeetingschedule);
		btnnavmeetingschedule.setOnClickListener(new Button.OnClickListener() { 
			@Override
			public void onClick(View view) { 
				initBtnBackgroundColor(); 
				btnnavmeetingschedule.setBackgroundResource(R.drawable.button_shape_pressed); 
		
				Log.d("Button--->onClick","btnnavmeetingschedule"); 
//				LinearLayout meetingschedule = (LinearLayout) getLayoutInflater().inflate(R.layout.meetingguide_schedule, null);
//				meetingcontent.removeAllViewsInLayout();
//				meetingcontent.addView(meetingschedule);
				

				WebView webView = new WebView(MeetingGuideActivity.this);
				webView.setWebViewClient(new WebViewClient());
				String htmlBasicInfo="<html><body style='font-size:30px'>" ;
				htmlBasicInfo+=MeetingGuideHtmlDataSupport.genSchedule(null);
				htmlBasicInfo+="</body></html>"; 
				webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
				//  
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(webView); 
				
			}
		});
	}
	
//	public void initMealsHotel() { 
//		btnnavmeetingmealshotel = (Button) findViewById(R.id.btnnavmeetingmealshotel);
//		btnnavmeetingmealshotel.setOnClickListener(new Button.OnClickListener() { 
//			@Override
//			public void onClick(View view) { 
//				initBtnBackgroundColor(); 
//				btnnavmeetingmealshotel.setBackgroundResource(R.drawable.button_shape_pressed);
//				Log.d("Button--->onClick","btnnavmeetingmealshotel"); 
//				LinearLayout meetingmealshotel = (LinearLayout) getLayoutInflater().inflate(R.layout.meetingguide_mealshotel, null);
//				meetingcontent.removeAllViewsInLayout();
//				meetingcontent.addView(meetingmealshotel);
//				
//			}
//		});
//	}
	
	
	public void initMemberInfo() { 
		btnnavmeetingmember = (Button) findViewById(R.id.btnnavmeetingmember);
		btnnavmeetingmember.setOnClickListener(new Button.OnClickListener() { 
			@Override
			public void onClick(View view) {  
				initBtnBackgroundColor(); 
				btnnavmeetingmember.setBackgroundResource(R.drawable.button_shape_pressed);
				Log.d("Button--->onClick","btnnavmeetingmealshotel");   

//		        mExpandableListView = (ExpandableListView)findViewById(R.id.m_list);

				LinearLayout memberCompnyList = (LinearLayout) getLayoutInflater().inflate(R.layout.meetingguide_member_companylist, null);
				ExpandableListView mExpandableListView = (ExpandableListView)memberCompnyList.findViewById(R.id.m_list); 
		        MemberInfoAdapter memberInfoAdapter=new MemberInfoAdapter(MeetingGuideActivity.this);
		        memberInfoAdapter.initializeData();
		        mExpandableListView.setAdapter(memberInfoAdapter);
		        mExpandableListView.setCacheColorHint(0);  //设置拖动列表的时候防止出现黑色背景 
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(memberCompnyList); 
				
				
//				ListView listView = new ListView(MeetingGuideActivity.this);
//		        listView.setAdapter(new ArrayAdapter<String>(MeetingGuideActivity.this, android.R.layout.simple_expandable_list_item_1,getData()));
//		        setContentView(listView);
//		        
//		        
//		        listView.setOnItemClickListener(new OnItemClickListener() {
//		            public void onItemClick(AdapterView<?> parent, View view,
//		                    int position, long id) { 
//			           Object o = parent.getItemAtPosition(position);
//			           String str=(String)o;//As you are using Default String Adapter
//			           Toast.makeText(MeetingGuideActivity.this,str,Toast.LENGTH_SHORT).show();
//		            }
//		        });
				
				
//				WebView memberinfo1 = new WebView(MeetingGuideActivity.this);
//				memberinfo1.setWebViewClient(new WebViewClient());
//				String htmlBasicInfo="<html><body style='font-size:30px'>" ;
//				htmlBasicInfo+=MeetingGuideHtmlDataSupport.genMemberInfo(null);
//				htmlBasicInfo+="</body></html>"; 
//				memberinfo1.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
//				//   
//				 
//				
//
//				WebView memberinfo2 = new WebView(MeetingGuideActivity.this);
//				memberinfo2.setWebViewClient(new WebViewClient());
//				String htmlBasicInfo2="<html><body style='font-size:30px'>" ;
//				htmlBasicInfo2+=MeetingGuideHtmlDataSupport.genMemberInfo(null);
//				htmlBasicInfo2+="</body></html>"; 
//				memberinfo2.loadDataWithBaseURL(null, htmlBasicInfo2, "text/html", "utf-8", null);
//				
//		        //江苏电力
//				TabHost tabHost = (TabHost) getLayoutInflater().inflate(R.layout.meetingguide_member_tab,null);
//				tabHost.setup();
//				TabHost.TabSpec  tabSpec1=tabHost.newTabSpec("tab_1");
//				tabSpec1.setContent(R.id.LinearLayout1);
//				tabSpec1.setIndicator("江苏电力");
//				LinearLayout linearLayout1=(LinearLayout)tabHost.findViewById(R.id.LinearLayout1);
//				linearLayout1.removeAllViewsInLayout();
//				linearLayout1.addView(memberinfo1);
//				tabHost.addTab(tabSpec1);
//				//外地
//				TabHost.TabSpec  tabSpec2=tabHost.newTabSpec("tab_2"); 
//				tabSpec2.setContent(R.id.LinearLayout2);
//				tabSpec2.setIndicator("外地"); 
//				LinearLayout linearLayout2=(LinearLayout)tabHost.findViewById(R.id.LinearLayout2);
//				linearLayout2.removeAllViewsInLayout();
//				linearLayout2.addView(memberinfo2);
//				tabHost.addTab(tabSpec2);
//				tabHost.setCurrentTab(0);
//				meetingcontent.removeAllViewsInLayout();
//				meetingcontent.addView(tabHost); 
				
			} 
			
			
//			private List<String> getData(){
//		        
//		        List<String> data = new ArrayList<String>();
//		        data.add("江苏省电力公司");
//		        data.add("上海市电力公司"); 
//		         
//		        return data;
//		    }
		});
	}
	
	
	
	
	

	
}
