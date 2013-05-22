package com.broadsoft.xmdownload;

import android.app.Activity;
import android.os.Bundle;

import com.broadsoft.xmcommon.androiddao.DaoHolder;
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
		DaoHolder.getInstance().init(getApplicationContext());
	}
 

}
