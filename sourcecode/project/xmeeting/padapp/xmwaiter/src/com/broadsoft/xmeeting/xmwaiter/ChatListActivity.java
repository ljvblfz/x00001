package com.broadsoft.xmeeting.xmwaiter;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import com.broadsoft.waiter.R;

public class ChatListActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chat);
        //����activityʱ���Զ�����������
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 

    }
	    
}