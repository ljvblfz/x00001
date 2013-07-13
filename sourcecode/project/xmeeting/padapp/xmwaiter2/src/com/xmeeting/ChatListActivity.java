package com.xmeeting;

import com.founder.enforcer.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class ChatListActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chat);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 

    }
	    
}
