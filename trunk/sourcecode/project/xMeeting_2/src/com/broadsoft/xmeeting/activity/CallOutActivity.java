package com.broadsoft.xmeeting.activity;


import com.broadsoft.common.DialogActivity;
import com.broadsoft.xmeeting.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CallOutActivity extends Activity {
	//private MyDialog dialog;
	private LinearLayout layout;
	private Activity act = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_out);
		//dialog=new MyDialog(this);
		layout=(LinearLayout)findViewById(R.id.exit_layout2);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		initButton();
	}

	private void initButton()
	{
		ImageButton btnWater = (ImageButton) this.findViewById(R.id.btnWater);
		btnWater.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				startActivity(new Intent(act, DialogActivity.class));
			}
		});
		
		ImageButton btnPaper = (ImageButton) this.findViewById(R.id.btnPaper);
		btnPaper.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				startActivity(new Intent(act, DialogActivity.class));
			}
		});
		
		ImageButton btnVoice = (ImageButton) this.findViewById(R.id.btnVoice);
		btnVoice.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				startActivity(new Intent(act, DialogActivity.class));
			}
		});
		
		ImageButton btnServer = (ImageButton) this.findViewById(R.id.btnServer);
		btnServer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				finish();
				startActivity(new Intent(act, DialogActivity.class));
			}
		});
	}
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void exitbutton1(View v) {  
    	this.finish();    	
      }  
	public void exitbutton0(View v) {  
    	this.finish();
      }  
	
}
