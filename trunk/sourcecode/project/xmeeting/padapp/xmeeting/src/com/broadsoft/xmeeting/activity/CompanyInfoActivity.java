package com.broadsoft.xmeeting.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.R.drawable;
import com.broadsoft.xmeeting.R.id;
import com.broadsoft.xmeeting.R.layout;

public class CompanyInfoActivity extends BaseActivity {


	private LinearLayout companycontent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_activity_main);

		companycontent = (LinearLayout) findViewById(R.id.companycontent); 
		initBasicInfo();
		initLeader();
		initOrg();  
		initBtnBackgroundColor();
	}

	//top navigation button
	private Button btnnavcompanybasicinfo;
	private Button btnnavcompanyleader;
	private Button btnnavcompanyorg; 
	
	
	
	public void initBtnBackgroundColor(){
		btnnavcompanybasicinfo.setBackgroundResource(R.drawable.button_shape_normal);
		btnnavcompanyleader.setBackgroundResource(R.drawable.button_shape_normal);
		btnnavcompanyorg.setBackgroundResource(R.drawable.button_shape_normal); 
	}

	

	public void initBasicInfo() { 
		btnnavcompanybasicinfo = (Button) findViewById(R.id.btnnavcompanybasicinfo);
		btnnavcompanybasicinfo.setOnClickListener(new Button.OnClickListener() {  
			@Override
			public void onClick(View view) {  
				initBtnBackgroundColor(); 
				btnnavcompanybasicinfo.setBackgroundResource(R.drawable.button_shape_pressed);  
//				LinearLayout personalinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.meeting_personalinfo, null);
//				companycontent.removeAllViewsInLayout();
//				companycontent.addView(personalinfo); 
			}
 
		});
		 
	}
	
	public void initLeader() { 
		btnnavcompanyleader = (Button) findViewById(R.id.btnnavcompanyleader);
		btnnavcompanyleader.setOnClickListener(new Button.OnClickListener() {  
			@Override
			public void onClick(View view) {  
				initBtnBackgroundColor(); 
				btnnavcompanyleader.setBackgroundResource(R.drawable.button_shape_pressed);  
//				LinearLayout personalinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.meeting_personalinfo, null);
//				companycontent.removeAllViewsInLayout();
//				companycontent.addView(personalinfo); 
			} 
		}); 
	}
	
	public void initOrg() { 
		btnnavcompanyorg = (Button) findViewById(R.id.btnnavcompanyorg);
		btnnavcompanyorg.setOnClickListener(new Button.OnClickListener() {  
			@Override
			public void onClick(View view) {  
				initBtnBackgroundColor(); 
				btnnavcompanyorg.setBackgroundResource(R.drawable.button_shape_pressed);  
//				LinearLayout personalinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.meeting_personalinfo, null);
//				companycontent.removeAllViewsInLayout();
//				companycontent.addView(personalinfo); 
			} 
		}); 
	}
	
	 
	
	
	

//	//bottom navigation button
//	private Button btnnavhome;
//	private Button btnnavreturn;
//	
//	public void initHomeInfo() { 
//		btnnavhome = (Button) findViewById(R.id.btnnavhome);
//		btnnavhome.setOnClickListener(new Button.OnClickListener() { 
//			@Override
//			public void onClick(View view) { 
//				Log.d("Button--->onClick","btnnavhome"); 
//				
//			}
//		});
//	}
//	
//	
//	public void initReturnInfo() { 
//		btnnavreturn = (Button) findViewById(R.id.btnnavreturn);
//		btnnavreturn.setOnClickListener(new Button.OnClickListener() { 
//			@Override
//			public void onClick(View view) { 
//				Log.d("Button--->onClick","btnnavreturn"); 
//				
//			}
//		});
//	}
}
