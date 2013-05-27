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

public class VotingActivity extends BaseActivity {

 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vote_activity_main); 
		votingcontent = (LinearLayout) findViewById(R.id.votingcontent);
		
		initQuestion();
		initSetting();
	}
  
	
	
	

	private LinearLayout votingcontent;

	private Button btnnavvotingquestion;
	private Button btnnavvotingsetting;
	
	public void initBtnBackgroundColor(){
		btnnavvotingquestion.setBackgroundResource(R.drawable.button_shape_normal);
		btnnavvotingsetting.setBackgroundResource(R.drawable.button_shape_normal); 
	}
	
	
	public void initQuestion() { 
		btnnavvotingquestion = (Button) findViewById(R.id.btnnavvotingquestion);
		btnnavvotingquestion.setOnClickListener(new Button.OnClickListener() { 
			
			@Override
			public void onClick(View view) {  
				initBtnBackgroundColor(); 
				btnnavvotingquestion.setBackgroundResource(R.drawable.button_shape_pressed); 
				Log.d("Button--->onClick","btnnavvotingquestion"); 
				LinearLayout personalinfo = (LinearLayout) getLayoutInflater().inflate(R.layout.vote_question, null);
				votingcontent.removeAllViewsInLayout();
				votingcontent.addView(personalinfo); 
			}
 
		});
		 
	}
	public void initSetting() { 
		btnnavvotingsetting = (Button) findViewById(R.id.btnnavvotingsetting);
		btnnavvotingsetting.setOnClickListener(new Button.OnClickListener() { 
			
			@Override
			public void onClick(View view) {  
				initBtnBackgroundColor(); 
				btnnavvotingsetting.setBackgroundResource(R.drawable.button_shape_pressed); 
				Log.d("Button--->onClick","btnnavvotingsetting"); 
				LinearLayout meetingbasic = (LinearLayout) getLayoutInflater().inflate(R.layout.vote_setting, null);
				votingcontent.removeAllViewsInLayout();
				votingcontent.addView(meetingbasic);
				
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
