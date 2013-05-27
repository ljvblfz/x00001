package com.broadsoft.xmeeting;

import com.broadsoft.common.Constants;
import com.broadsoft.xmcommon.appsupport.AppInitSupport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast; 

/**
 * http://blog.csdn.net/Class_Raito/article/details/3390737
 * http://blog.csdn.net/tody_guo/article/details/8164728
 * @author lu.zhen
 * 
 */
public class LoginActivity extends Activity  {
	
	private final static boolean flag=true;
	
	private String TAG="LoginActivity";
	private static int REQUEST_CODE = 2;
//	View contentView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate onCreate");
		super.onCreate(savedInstanceState);
		
		if(!Constants.enableAutoBoot){
			AppInitSupport.initApp(this.getApplicationContext(), this.getAssets()); 
		}else{ 
			AppInitSupport.debugAppData();
		}
		
		// 应用程序开机不锁屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//		getActionBar().hide();
		//
		setContentView(R.layout.login_activity_main);

		registerNavButtonForLogin();

//		contentView = findViewById(R.id.login_fullscreen);
//		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
//				HIDER_FLAGS);
//		mSystemUiHider.setup();
//		contentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); 
		Log.d(TAG, "onCreate end");
 
	}//end of onCreate

	@Override
	protected void onDestroy(){
		Log.d(TAG, "onDestroy begin");
		super.onDestroy();
		Log.d(TAG, "onDestroy end");
	}//end of onDestroy

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
	}//end of onPostCreate
	
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
//		return false;
	}
	
	
 
	
	//


	
	
	/**
	 * toast  demo: http://www.cnblogs.com/salam/archive/2010/11/10/1873654.html
	 * dialog demo: http://www.oschina.net/question/54100_32486
	 * listview中点击按键弹出对话框
	 */
	protected void showExitAppConfirmDialog() { 
		final View dialogView = getLayoutInflater().inflate(R.layout.common_exitapp, null);
 
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
		builder.setTitle("确认退出系统?");
		builder.setView(dialogView);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) { 
				EditText pwdView =(EditText)dialogView.findViewById(R.id.exitapp_password);
				String strPwd=pwdView.getText().toString();
				Log.d("Dialog Button--->onClick","Password is: "+strPwd); 
				if("123456".equals(strPwd)){
					finish(); 
					finish(); 
					android.os.Process.killProcess(android.os.Process.myPid());
//					Intent startMain = new Intent(LoginActivity.this,LauncherActivity.class);
//					Intent startMain = new Intent();
//					startMain.setClass(LoginActivity.this, LauncherActivity.class);// 指定了跳转前的Activity和跳转后的Activity
//					Intent startMain = new Intent(Intent.ACTION_MAIN);
//					startMain.addCategory(Intent.CATEGORY_HOME);
//					startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					startActivity(startMain);
//					startActivityForResult(startMain, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity
				}else{
					Toast toast=Toast.makeText(LoginActivity.this, "密码不准确!",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) { 
				dialog.dismiss();
			}
		});
		builder.show(); 
	}

	//
	public void registerNavButtonForLogin() {
		Button btnlogin = (Button) findViewById(R.id.btnlogin);
		btnlogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LoginActivity.this, WelcomeActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}// end of on click
		});

	}//


}
