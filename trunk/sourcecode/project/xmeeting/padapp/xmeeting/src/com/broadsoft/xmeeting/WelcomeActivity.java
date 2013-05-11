package com.broadsoft.xmeeting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.broadsoft.common.BaseActivity;

/**
 * http://blog.csdn.net/Class_Raito/article/details/3390737
 * http://blog.csdn.net/tody_guo/article/details/8164728
 * @author lu.zhen
 * 
 */
public class WelcomeActivity extends BaseActivity  {
	
	private String TAG="WelcomeActivity";
	private static int REQUEST_CODE = 2;
//	View contentView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 应用程序开机不锁屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//
		setContentView(R.layout.welcome_activity_main);
 
 
		registerNavButtonForComeIn();

//		contentView = findViewById(R.id.login_fullscreen);
//		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
//				HIDER_FLAGS);
//		mSystemUiHider.setup();
//		contentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		 
 
	}

 
	
	//

  
	
	  

	//
	public void registerNavButtonForComeIn() {
		Button btnlogin = (Button) findViewById(R.id.btnComeIn);
		btnlogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(WelcomeActivity.this, DesktopActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}// end of on click
		});

	}//


}
