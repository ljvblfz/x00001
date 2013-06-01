package com.broadsoft.xmeeting.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.common.MulitPointTouchListener;
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
		String position = this.getIntent().getStringExtra("position");
		ImageView imageView=(ImageView) this.findViewById(R.id.ivLeader);
		imageView.setImageResource(Integer.parseInt(position));
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
