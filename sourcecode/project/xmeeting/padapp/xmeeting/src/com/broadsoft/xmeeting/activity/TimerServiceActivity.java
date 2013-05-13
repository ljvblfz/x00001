package com.broadsoft.xmeeting.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;


/**
 * http://rayleung.iteye.com/blog/422972
 * @author lu.zhen
 *
 */
public class TimerServiceActivity extends BaseActivity {

	private String TAG="TimerServiceActivity";
	
	private TextView mTimeLabel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timer_activity_main);

		
		mTimeLabel=(TextView)this.findViewById(R.id.timingdisplaycontent);
//		initBeginTimer();
//		initEndTimer();

		timer.schedule(task,  1000, 3);  
	}
	private Timer timer = new Timer();  
	TimerTask task = new TimerTask(){  
		  	
	        public void run() {  
	        	mTimeLabel.setText("--->"+System.currentTimeMillis());  
	        }  
	          
	};  
	//===============>
	private Button btnnavbegintimer;
	private Button btnnavendtimer;

	public void initBeginTimer() {
		btnnavbegintimer = (Button) findViewById(R.id.btnnavendtimer);
		btnnavbegintimer.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {
				timer.schedule(task, 1);  
			}

		});
	}

	public void initEndTimer() {
		btnnavendtimer = (Button) findViewById(R.id.btnnavendtimer);
		btnnavendtimer.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {   
				timer.cancel();
			} 
		});
	}
	
	
	
	
	 
}

 
