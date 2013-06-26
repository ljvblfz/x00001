package com.broadsoft.xmeeting.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.broadsoft.common.MulitPointTouchListener;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmeeting.R;

/**
 * http://blog.csdn.net/sunboy_2050/article/details/7420567 ViewFlipper
 * http://blog.csdn.net/roadog2006/article/details/5326057 anim
 * 
 * @author lu.zhen
 * 
 */
public class ImageGallaryViewPopupActivity extends Activity {

	// private MyDialog dialog;
	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagegallary_picview);
		//
//		layout = (LinearLayout) findViewById(R.id.exit_layout2);
//		layout.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) { 
//				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
//						Toast.LENGTH_SHORT).show();
//			}
//		});
//		Bitmap bmImage = (Bitmap)getIntent().getParcelableExtra("image");   
//		Toast.makeText(this,bmImage + "------>bmImage", Toast.LENGTH_LONG).show();
		String fileName = this.getIntent().getStringExtra("fileName");

    	String extStorageDirectory=SDCardSupport.getSDCardDirectory(); 
    	
    	
    	Bitmap bmImage = BitmapFactory.decodeFile(extStorageDirectory+fileName);  
    	
    	
		ImageView imageView=(ImageView) this.findViewById(R.id.ivLeader);
		imageView.setImageBitmap(bmImage);
		imageView.setOnTouchListener(new MulitPointTouchListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

//	public void exitbutton1(View v) {
//		this.finish();
//	}
//
//	public void exitbutton0(View v) {
//		this.finish();
//	}

}
