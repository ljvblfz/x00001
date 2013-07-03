package com.broadsoft.xmeeting.activity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmdownload.wsservice.WsControllerServiceSupport;
import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.R;

public class CallOutActivity extends Activity {
	
	private final static String TAG="CallOutActivity";
	
	//private MyDialog dialog;
	private LinearLayout layout;
	private Activity act = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_out);
		//dialog=new MyDialog(this);
		layout=(LinearLayout)findViewById(R.id.exit_layout2);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		initButton();
		
//		new Thread(notifyRunnable).start();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		DesktopActivity.releaseLoading(hasFocus);
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}
	
	
	
	private void initButton()
	{
		ImageButton btnWater = (ImageButton) this.findViewById(R.id.btnWater);
		btnWater.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				Log.d(TAG, "请求茶水.");
				finish();
				String tos=getTo();
				WsControllerServiceSupport.getInstance().sendCallServiceMessage("请求茶水", tos); 
				Toast toast=Toast.makeText(CallOutActivity.this, "请求茶水成功.",Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
//				startActivity(new Intent(act, DialogActivity.class));
			}
		});
		
		ImageButton btnPaper = (ImageButton) this.findViewById(R.id.btnPaper);
		btnPaper.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				Log.d(TAG, "请求纸笔.");
				String tos=getTo();
				WsControllerServiceSupport.getInstance().sendCallServiceMessage("请求纸笔", tos); 
				Toast toast=Toast.makeText(CallOutActivity.this, "请求纸笔成功.",Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				finish();
//				startActivity(new Intent(act, DialogActivity.class));
			}
		});
		
		ImageButton btnVoice = (ImageButton) this.findViewById(R.id.btnVoice);
		btnVoice.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				Log.d(TAG, "请求话筒.");
				//
				String tos=getTo();
				WsControllerServiceSupport.getInstance().sendCallServiceMessage("请求话筒", tos); 
				Toast toast=Toast.makeText(CallOutActivity.this, "请求话筒成功.",Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				//
				finish();
//				startActivity(new Intent(act, DialogActivity.class));
			}
		});
		
		ImageButton btnServer = (ImageButton) this.findViewById(R.id.btnServer);
		btnServer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				Log.d(TAG, "请求服务人员.");  
				String tos=getTo();
				WsControllerServiceSupport.getInstance().sendCallServiceMessage("请求服务人员", tos); 
				Toast toast=Toast.makeText(CallOutActivity.this, "请求服务人员成功.",Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				finish();
//				startActivity(new Intent(act, DialogActivity.class));
			}
		});
	}
	
	
	public final String getTo(){
		StringBuilder tos=new StringBuilder();
		DownloadInfoEntity downloadInfoEntity=EntityInfoHolder.getInstance().getDownloadInfoEntity();
		try {
			JSONObject downloadJsonObject=new JSONObject(downloadInfoEntity.getJsonData());
			JSONObject personnelInfoJson=downloadJsonObject.getJSONObject("personnelInfo");
			JSONArray  jsonArrayPersonnel=personnelInfoJson.getJSONArray("listOfXmMeetingServicePersonnelIVO");
			for(int i=0;i<jsonArrayPersonnel.length();i++){
				JSONObject jsonPersonnel=jsonArrayPersonnel.getJSONObject(i);
				String xmpiGuid= jsonPersonnel.getString("xmpiGuid");
				tos.append(xmpiGuid);
				if(i<jsonArrayPersonnel.length()-1){
					tos.append(","); 
				}
			}
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		return tos.toString();
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void exitbutton1(View v) {  
    	this.finish();    	
      }  
	public void exitbutton0(View v) {  
    	this.finish();
      }  
	
}
