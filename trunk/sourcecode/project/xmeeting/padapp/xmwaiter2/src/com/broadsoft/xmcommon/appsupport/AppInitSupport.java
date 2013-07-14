package com.broadsoft.xmcommon.appsupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.AppConfig;
import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
import com.broadsoft.xmeeting.rsservice.RsServiceOnMeetingPersonnelInfoSupport;
import com.broadsoft.xmeeting.rsservice.RsServiceOnWaiterInfoSupport;
import com.broadsoft.xmeeting.wsservice.WsControllerServiceSupport;
import com.xmeeting.WaiterMainActivity;

public class AppInitSupport {
	private static final String TAG="AppInitSupport"; 

	private static WaiterMainActivity activity ;
	
	public static void initApp(Context ctx,AssetManager assetManager ) {
		//初始化配置
		DomAppConfigFactory.init(assetManager);
		AppConfig appConfig=DomAppConfigFactory.getAppConfig();
		Log.d(TAG, "[Config]AppConfig---->"+appConfig);
		//初始化设备ID
		AndroidIdSupport.init(ctx);
		Log.d(TAG, "[AndroidI]AndroidID---->"+AndroidIdSupport.getAndroidID()); 
		//SD card
		String sdcardDir = SDCardSupport.getSDCardDirectory();
		Log.d(TAG, "[SDCard]sdcardDir---->"+sdcardDir);   
//		activity=activitys;
		//WS service
		new RequestWaiterInfoTask().execute();

	}//end of initApp
 

    private static JSONObject jsobj ;
    
    private static String meetingId;
    
    private static String xmmiName;
    
    private static String memberId;
    
    private static String memberDisplayName;
    
    
	 private static class RequestWaiterInfoTask extends AsyncTask<Void, Void, String[]> { 
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
			}
			@Override
			protected String[] doInBackground(Void... params) { 
				//Get waiter infomation
				try {
					jsobj = RsServiceOnWaiterInfoSupport.requestWaiterInfo().getJSONObject("jsonData");
					meetingId = jsobj.getJSONObject("xervicePersonnelPadIVO").getJSONArray("list").getJSONObject(0).getString("xmmiGuid");
					xmmiName = jsobj.getJSONObject("xervicePersonnelPadIVO").getJSONArray("list").getJSONObject(0).getString("xmmiName");
					memberId = jsobj.getJSONObject("xervicePersonnelPadIVO").getJSONArray("list").getJSONObject(0).getString("xmpiGuid");
					memberDisplayName = jsobj.getJSONObject("xervicePersonnelPadIVO").getJSONArray("list").getJSONObject(0).getString("xmpiName");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				loadMemberList(RsServiceOnMeetingPersonnelInfoSupport.requestMeetingPersonnelInfo(meetingId));
				
				WsControllerServiceSupport.getInstance().initData(meetingId, memberId, memberDisplayName);
				return null;
			}

			
			private void loadMemberList(JSONObject jsobj){
				
				try {
					JSONArray memberArray = jsobj.getJSONObject("jsonData").getJSONArray("listOfXmMeetingPersonnelSeatPadIVO");
					l = new ArrayList<Map<String, Object>>();
					for (int i = 0 ; i< memberArray.length(); i ++){
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("position", memberArray.getJSONObject(i).getString("xmridSeatno"));
						m.put("company",  memberArray.getJSONObject(i).getString("xmpiDeptinfo"));
						m.put("name_title", memberArray.getJSONObject(i).getString("xmpiName")+"("+memberArray.getJSONObject(i).getString("xmpiTitle")+")");
						l.add(m);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			private List<Map<String, Object>> l ;
			
			@Override
			protected void onPostExecute(String[] result) { 
//				activity.setMemberList(l);
	  		}   
	    }//end of RequestWaiterInfoTask
	    

	public static void destroyApp(Context ctx,AssetManager assetManager) { 
		WsControllerServiceSupport.getInstance().disconnect();
	}
}
