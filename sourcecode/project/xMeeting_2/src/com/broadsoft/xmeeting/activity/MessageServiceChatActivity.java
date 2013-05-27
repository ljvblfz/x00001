package com.broadsoft.xmeeting.activity;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.broadsoft.common.BaseActivity; 
import com.broadsoft.xmeeting.R;


/**
 * list view article http://www.vogella.com/articles/AndroidListView/article.html
 * @author lu.zhen
 *
 */
public class MessageServiceChatActivity extends BaseActivity {

	public final String TAG="MessageServiceChatActivity";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_chat);  
		// 
		//
		registerExitButton();
 
	}
//
//	@Override
//	public void execBackButton(){
//		this.disconnect();
//	}
//	
	
	//============WebSocketClient==============>
	  
	
	//=========================================
	protected Button btnChatSend;
	public void registerExitButton(){
		Object objView= findViewById(R.id.btnChatSend);  
		if(objView==null){
			return;
		}
		btnChatSend = (Button)objView;
		btnChatSend.setOnClickListener(new Button.OnClickListener() { 
			@Override
			public void onClick(View view) { 
				Log.d("MessageServiceChatActivity Send Button--->onClick","btnnavback"); 
				EditText chatText=(EditText)findViewById(R.id.editTextChatContent); 
			}
		}); 
	} 
	 
	
	 
}
