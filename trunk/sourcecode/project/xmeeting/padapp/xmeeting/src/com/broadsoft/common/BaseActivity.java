package com.broadsoft.common;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.WindowManager;
import android.widget.Button;

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

//	protected SystemUiHider mSystemUiHider;
//	protected static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

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
		registerBackButton();
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
	
	
	public void registerBackButton() { 
		Object objView= findViewById(R.id.btnnavback);  
		if(objView==null){
			return;
		}
		btnnavback = (Button) objView;
		btnnavback.setOnClickListener(new Button.OnClickListener() { 
			@Override
			public void onClick(View view) { 
				Log.d("System Nav Button--->onClick","btnnavback"); 
				finish(); 
				execBackButton();
			}
		});
	}
	
	public void execBackButton(){
		
	}

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
