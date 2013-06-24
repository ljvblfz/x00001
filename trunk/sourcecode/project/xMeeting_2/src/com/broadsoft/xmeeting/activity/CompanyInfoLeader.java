package com.broadsoft.xmeeting.activity;

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

public class CompanyInfoLeader extends Activity {
 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_leader); 
		ImageView ivLeader=( (ImageView) this.findViewById(R.id.ivLeader) );
		

    	String extStorageDirectory=SDCardSupport.getSDCardDirectory(); 
    	String fileName=getLeaderFilePath();
    	Bitmap bmImage = BitmapFactory.decodeFile(extStorageDirectory+fileName);  
		ivLeader.setImageBitmap(bmImage);
		
		ivLeader.setOnTouchListener(new MulitPointTouchListener ());

		 
	}
	
	
	public String getLeaderFilePath(){
		String jsonData=EntityInfoHolder.getInstance().getCompanyInfoEntity().getJsonData();
 
		String leaderFilePath="";
		try {
			JSONObject jsonObject=new JSONObject(jsonData);
			JSONObject companyInfo=jsonObject.getJSONObject("leaderinfo"); 
			String compinfo=companyInfo.getString("xmciAttachment");
			if(null!=compinfo&&!"".equals(compinfo)){
				leaderFilePath=compinfo;
			}
		} catch (JSONException e) { 
			e.printStackTrace();
		}  
		Log.d(TAG, "[getLeaderFilePath]filePath "+leaderFilePath);
		return leaderFilePath;
	}//end of getLeaderFilePath
	 

	private final static String TAG="CompanyInfoLeader";
	 
	
	 
}
