package com.broadsoft.common.websocket;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.broadsoft.common.androidwebsocket.WebSocketClient;




/**
 * http://publicstaticdroidmain.com/2012/01/real-time-android-messaging-with-pusher/
 * @author lu.zhen
 *
 */
public class WebSocketSupport {

	public final String TAG="WebSocketSupport";
	
	//============WebSocketClient==============>
		protected WebSocketClient client;
		public void connect(String wspath){
			wspath="ws://172.29.135.63:8080/websocket/mywebsocket.chat?memberId=android&meetingId=";
			List<BasicNameValuePair> extraHeaders = Arrays.asList( new BasicNameValuePair("Cookie", "session=abcd") );
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
//				        Log.d(TAG, String.format("Got binary message! %s", toHexString(data)));
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
}
