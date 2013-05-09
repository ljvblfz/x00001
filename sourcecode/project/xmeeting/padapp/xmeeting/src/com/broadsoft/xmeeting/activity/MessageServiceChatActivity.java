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
import com.broadsoft.common.androidwebsocket.WebSocketClient;
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
		String wspath="ws://172.29.135.63:8080/websocket/mywebsocket.chat?name=android";
		connect(wspath); 
		//
		registerExitButton();
 
	}

	@Override
	public void execBackButton(){
		this.disconnect();
	}
	
	
	//============WebSocketClient==============>
	protected WebSocketClient client;
	public void connect(String wspath){
		List<BasicNameValuePair> extraHeaders = Arrays.asList( new BasicNameValuePair("Cookie", "session=android") );
		
		URI uri=URI.create(wspath); 
		client = new WebSocketClient(uri, new WebSocketClient.Listener() {
		    @Override
		    public void onConnect() {
		        Log.d(TAG, "Listener=========>Connected!");
		    }

		    @Override
		    public void onMessage(String message) {
		        Log.d(TAG, String.format("Listener=========>Got string message! %s", message));
		    }

		    @Override
		    public void onMessage(byte[] data) {
//			        Log.d(TAG, String.format("Got binary message! %s", toHexString(data)));
		        Log.d(TAG, String.format("Listener=========>Got binary message! %s", data.toString()));
		    }

		    @Override
		    public void onDisconnect(int code, String reason) {
		        Log.d(TAG, String.format("Listener=========>Disconnected! Code: %d Reason: %s", code, reason));
		    }

		    @Override
		    public void onError(Exception error) {
		        Log.e(TAG, "Listener=========>Error!", error);
		    }
		}, extraHeaders);

		client.connect();
 
	}

	public void sendMessage(String msg){  
		client.send(msg); 
	}
	

	public void disconnect(){
		client.disconnect();
		
	}
	
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
				sendMessage(chatText.getText().toString());
			}
		}); 
	} 
	 
	
	 
}
