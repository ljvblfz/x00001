package com.broadsoft.xmeeting.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.R.id;
import com.broadsoft.xmeeting.R.layout;


/**
 * http://rayleung.iteye.com/blog/422972
 * @author lu.zhen
 *
 */
public class TimerServiceActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_activity_main);

		initBeginTimer();
		initEndTimer();
	}

	private Button btnnavbegintimer;
	private Button btnnavendtimer;

	public void initBeginTimer() {
		btnnavbegintimer = (Button) findViewById(R.id.btnnavendtimer);
		btnnavbegintimer.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {

			}

		});
	}

	public void initEndTimer() {
		btnnavendtimer = (Button) findViewById(R.id.btnnavendtimer);
		btnnavendtimer.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) { 

			} 
		});
	}
	
	
	
	
	

	// bottom navigation button=========================================>
//	private Button btnnavhome;
//	private Button btnnavreturn;
//
//	public void initHomeInfo() {
//		btnnavhome = (Button) findViewById(R.id.btnnavhome);
//		btnnavhome.setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Log.d("Button--->onClick", "btnnavhome");
//
//			}
//		});
//	}
//
//	public void initReturnInfo() {
//		btnnavreturn = (Button) findViewById(R.id.btnnavreturn);
//		btnnavreturn.setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Log.d("Button--->onClick", "btnnavreturn");
//
//			}
//		});
//	}
}
