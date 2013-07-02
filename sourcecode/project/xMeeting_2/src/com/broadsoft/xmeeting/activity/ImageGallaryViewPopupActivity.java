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
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagegallary_picview);
		// 
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
 
}
