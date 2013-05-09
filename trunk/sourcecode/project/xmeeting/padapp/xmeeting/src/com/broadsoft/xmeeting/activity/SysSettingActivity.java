package com.broadsoft.xmeeting.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.broadsoft.appsupport.ActivityHolder;
import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.R.id;
import com.broadsoft.xmeeting.R.layout;

public class SysSettingActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.syssetting_activity_main);

		initSysAbout();
//		initSysExit();
	}

	private Button btnnavsysexit;
	private Button btnnavsysabout;

	public void initSysAbout() {
		btnnavsysabout = (Button) findViewById(R.id.btnnavsyssave);
		btnnavsysabout.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View view) {

			}

		});
	}
//
//	public void initSysExit() {
//		btnnavsysexit = (Button) findViewById(R.id.btnnavsysexit);
//		btnnavsysexit.setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				AlertDialog.Builder builder = new AlertDialog.Builder(SysSettingActivity.this);
//				builder.setTitle("提示");
//				builder.setMessage("您确定要退出程序吗?");
//				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) { 
//						ActivityHolder.getInstance().destroyAllActivity();
//						android.os.Process.killProcess(android.os.Process.myPid());
//					}
//				});
//				builder.setNegativeButton("取消", null);
//				builder.show();
//
//			}
//
//		});
//	}
 
}
