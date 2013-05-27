package com.broadsoft.xmeeting.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.broadsoft.common.MulitPointTouchListener;
import com.broadsoft.xmeeting.R;

public class CompanyInfoLeader extends Activity {
 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_leader); 
		( (ImageView) this.findViewById(R.id.ivLeader) ).setOnTouchListener(new MulitPointTouchListener ());

		 
	}
	 
	
	 
	
	 
}
