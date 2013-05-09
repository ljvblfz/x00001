package com.broadsoft.xmeeting.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.R.drawable;
import com.broadsoft.xmeeting.R.id;
import com.broadsoft.xmeeting.R.layout;
import com.broadsoft.xmeeting.adapter.DataGridAdapter;

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
				LinearLayout personalinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.meetingguide_businfo, null);
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(personalinfo); 
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
				LinearLayout personalinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.meetingguide_weatherinfo, null);
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(personalinfo); 
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
				LinearLayout meetingcontactinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.meetingguide_contactinfo, null);
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(meetingcontactinfo);
				
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
				LinearLayout meetingschedule = (LinearLayout) getLayoutInflater().inflate(R.layout.meetingguide_schedule, null);
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(meetingschedule);
				
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
				GridView memberinfo = (GridView) getLayoutInflater().inflate(R.layout.meetingguide_memberinfo, null); 
				meetingcontent.removeAllViewsInLayout();
				meetingcontent.addView(memberinfo);
		        memberinfo.setAdapter(new DataGridAdapter(meetingcontent.getContext()));
				
			}
		});
	}
	
	
	

	
}
