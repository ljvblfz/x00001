package com.broadsoft.xmeeting.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.broadsoft.common.MulitPointTouchListener;
import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmeeting.R;

public class CompanyInfoOrg extends Activity {
 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_org); 
		ImageView ivOrg=( (ImageView) this.findViewById(R.id.ivOrg) );
		


    	String extStorageDirectory=SDCardSupport.getSDCardDirectory(); 
    	String fileName=getOrgFilePath();
    	Bitmap bmImage = BitmapFactory.decodeFile(extStorageDirectory+fileName);  
    	ivOrg.setImageBitmap(bmImage);
		
		ivOrg.setOnTouchListener(new MulitPointTouchListener ());

		 
	}

	
	
	public String getOrgFilePath(){
//		String jsonData=EntityInfoHolder.getInstance().getCompanyInfoEntity().getJsonData();
// 
//		String leaderFilePath="";
//		try {
//			JSONObject jsonObject=new JSONObject(jsonData);
//			JSONObject companyInfo=jsonObject.getJSONObject("orginfo"); 
//			String compinfo=companyInfo.getString("xmciAttachment");
//			if(null!=compinfo&&!"".equals(compinfo)){
//				leaderFilePath=compinfo;
//			}
//		} catch (JSONException e) { 
//			e.printStackTrace();
//		}  
		String jsonData=EntityInfoHolder.getInstance().getDownloadInfoEntity().getJsonData();
		String orgFilePath="";
		try {  
			JSONObject downloadJsonObject=new JSONObject(jsonData);
			JSONObject jsonMeetingInfo=downloadJsonObject.getJSONObject("meetingInfo");
			JSONArray listOfXmCompanyInfo=jsonMeetingInfo.getJSONArray("listOfXmCompanyInfo");  
			for(int i=0;i<listOfXmCompanyInfo.length();i++){ 
				JSONObject companyInfo=listOfXmCompanyInfo.getJSONObject(i);
				String xmciType=companyInfo.getString("xmciType");
				if(xmciType.equals("3")){
					String xmciAttachment=companyInfo.getString("xmciAttachment");
					orgFilePath= xmciAttachment;
					continue;
				} //end of if on xmciType
			}//end of for
		} catch (JSONException e) { 
			e.printStackTrace();
		}   
		Log.d(TAG, "[getOrgFilePath]filePath "+orgFilePath);
		return orgFilePath;
	}//end of getLeaderFilePath
	
	
	private final static String TAG="CompanyInfoOrg";
	 
	
	 
}
