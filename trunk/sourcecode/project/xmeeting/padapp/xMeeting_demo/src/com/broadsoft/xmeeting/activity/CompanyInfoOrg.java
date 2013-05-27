package com.broadsoft.xmeeting.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.broadsoft.common.MulitPointTouchListener;
import com.broadsoft.xmeeting.R;

public class CompanyInfoOrg extends Activity {
 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_org); 
		( (ImageView) this.findViewById(R.id.ivOrg) ).setOnTouchListener(new MulitPointTouchListener ());

		 
	}
	 
	
	 
	
	 
}
