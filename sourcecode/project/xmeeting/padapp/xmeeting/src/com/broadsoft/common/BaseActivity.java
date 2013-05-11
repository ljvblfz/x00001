package com.broadsoft.common;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.broadsoft.xmeeting.R;

/**
 * http://blog.csdn.net/Class_Raito/article/details/3390737
 * 
 * @author lu.zhen
 * 
 */
public class BaseActivity extends Activity implements OnClickListener,
		OnSystemUiVisibilityChangeListener {
	protected static int REQUEST_CODE=2;

	private WindowManager wm = null; 
	private WindowManager.LayoutParams wmParams = null; 
//	private Button play1; 
	private Button floatBackButton; 
	private int mAlpha = 0; 
	private ViewFlipper viewFlipper = null; 
	
	private int x =40;
	private int y=20;
	
	protected void setxy(int x,int y){
		this.x=x;
		this.y=y;
	}

//	protected SystemUiHider mSystemUiHider;
//	protected static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	private void initFloatview() {
		wm = (WindowManager) getApplicationContext().getSystemService("window");
		wmParams = new WindowManager.LayoutParams();
		// wmParams=new WindowManager.LayoutParams();
		// wmParams.type=LayoutParams.TYPE_PHONE;
		// wmParams.format=PixelFormat.RGBA_8888;
		// wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL|LayoutParams.FLAG_NOT_FOCUSABLE;
		// wmParams.x=0;
		// wmParams.y=0;
		// wmParams.width=100;
		// wmParams.height=100;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// 启动的方法不用写在oncreate中
		super.onResume();			
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		initFloatview();
//		play1 = new Button(this);
		floatBackButton = new Button(this);
		createRightButton();
//		System.out.println("resume");
	}

	
	// 右悬浮键 
	private void createRightButton() {
		wmParams.type = LayoutParams.TYPE_PHONE;
		wmParams.format = PixelFormat.RGBA_8888;
		wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		floatBackButton.setBackgroundResource(R.drawable.back);
		floatBackButton.getBackground().setAlpha(180);
		floatBackButton.setPadding(10,10,10,10);
		floatBackButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					floatBackButton.setBackgroundResource(R.drawable.back);
					floatBackButton.getBackground().setAlpha(255);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					floatBackButton.setBackgroundResource(R.drawable.back);
					floatBackButton.getBackground().setAlpha(180);
				}
				return false;
			}
		});
		// cache1.setAlpha(0);
		//cache1.setText("缓存");
		floatBackButton.setOnClickListener(mBackListener);
		wmParams.width = 35;
		wmParams.height = 35;
		wmParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
		wmParams.x = this.x;
		wmParams.y = this.y;
		wm.addView(floatBackButton, wmParams);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		try{
			wm.removeView(floatBackButton);
		}catch(Exception e){
			e.printStackTrace();
		}
		super.onPause();
	}	
	
	private View.OnClickListener mBackListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) { 
//			wm.removeView(floatBackButton); 
			finish();
		}
	};
	
	/**
	 * Disable back key
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("BaseActivity--->onKeyDown", "onKeyDown  keyCode----->" + keyCode);
		Log.d("BaseActivity--->onKeyDown", "onKeyDown  KEYCODE_BACK----->" +  KeyEvent.KEYCODE_BACK);
		Log.d("BaseActivity--->onKeyDown", "onKeyDown  KEYCODE_HOME----->" +  KeyEvent.KEYCODE_HOME);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// do something
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_HOME) {
			// do something
			return false;
		}
		 
		return super.onKeyDown(keyCode, event); 
		// Disable all keys
//		return false;
	}

	// /**
	// * Try to disable the home key
	// */
	// @Override
	// protected void onUserLeaveHint() {
	// super.onUserLeaveHint();
	// Log.d("BaseActivity--->onUserLeaveHint", "onUserLeaveHint  begin");
	// Log.d("BaseActivity--->onUserLeaveHint", "onUserLeaveHint  end");
	// }

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setOnClickListener(this);
		getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(this);
		//
//		registerBackButton();
	}
	

	
	
	
	
	
	
	//bottom navigation button
	private Button btnnavhome;
	private Button btnnavback;
	
	public void registerHomeButton() { 
//		Object objView= findViewById(R.id.btnnavhome);  
//		if(objView==null){
//			return;
//		}
//		btnnavhome = (Button)objView;
//		btnnavhome.setOnClickListener(new Button.OnClickListener() { 
//			@Override
//			public void onClick(View view) { 
//				Log.d("System Nav Button--->onClick","btnnavhome"); 
//				
//			}
//		});
	}
	
//	
//	public void registerBackButton() { 
//		Object objView= findViewById(R.id.btnnavback);  
//		if(objView==null){
//			return;
//		}
//		btnnavback = (Button) objView;
//		btnnavback.setOnClickListener(new Button.OnClickListener() { 
//			@Override
//			public void onClick(View view) { 
//				Log.d("System Nav Button--->onClick","btnnavback"); 
//				finish(); 
//				execBackButton();
//			}
//		});
//	}
	
//	public void execBackButton(){
//		
//	}

	// ====================OnClickListener========================

	@Override
	public void onClick(View v) {
//		Log.d("Button--->onClick",
//				"onClick  begin----->" + v.getSystemUiVisibility());
//		getWindow().getDecorView().setSystemUiVisibility(
//				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//		Log.d("Button--->onClick", "onClick  end");
	}

	// ====================OnSystemUiVisibilityChangeListener========================
	@Override
	public void onSystemUiVisibilityChange(int visibility) {
//		Log.d("Button--->onSystemUiVisibilityChange",
//				"onSystemUiVisibilityChange  begin----->" + visibility);
//		Log.d("Button--->onSystemUiVisibilityChange",
//				"onSystemUiVisibilityChange  View.SYSTEM_UI_FLAG_VISIBLE's value----->"
//						+ View.SYSTEM_UI_FLAG_VISIBLE);
//		Log.d("Button--->onSystemUiVisibilityChange",
//				"onSystemUiVisibilityChange  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION's value----->"
//						+ View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//
//		Log.d("Button--->onSystemUiVisibilityChange",
//				"onSystemUiVisibilityChange  end");
	}
}
