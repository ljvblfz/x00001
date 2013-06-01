package com.broadsoft.xmeeting.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.broadsoft.appsupport.ActivityHolder;
import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.R.id;
import com.broadsoft.xmeeting.R.layout;

public class SysSettingActivity extends Activity {

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		DesktopActivity.releaseLoading(hasFocus);
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.syssetting_activity_main);
		InitTopbarAndBack();

		}
		
		private void InitTopbarAndBack()
		{
			( (Button) this.findViewById(R.id.btnBack) ).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
					//overridePendingTransition(R.anim.zoom_enter,android.R.anim.fade_out);
				}
			});
		}
	
 
}
