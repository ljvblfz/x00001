package com.broadsoft.xmeeting.activity;
 

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.broadsoft.common.MulitPointTouchListener;
import com.broadsoft.xmeeting.R;


/**
 * http://blog.csdn.net/sunboy_2050/article/details/7420567    ViewFlipper
 * http://blog.csdn.net/roadog2006/article/details/5326057      anim
 * @author lu.zhen
 *
 */
public class ImageGallaryViewFlipperActivity extends Activity   {
 
	
	//private MyDialog dialog;
		private LinearLayout layout;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.imagegallary_picview);
			//dialog=new MyDialog(this);
			layout=(LinearLayout)findViewById(R.id.exit_layout2);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			layout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！", 
							Toast.LENGTH_SHORT).show();	
				}
			});
			String s = this.getIntent().getStringExtra("id");
			( (ImageView) this.findViewById(R.id.ivLeader) ).setImageResource(Integer.parseInt(this.getIntent().getStringExtra("id")));
			( (ImageView) this.findViewById(R.id.ivLeader) ).setOnTouchListener(new MulitPointTouchListener ());
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
