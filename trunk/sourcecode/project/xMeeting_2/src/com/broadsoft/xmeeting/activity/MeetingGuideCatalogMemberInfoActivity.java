package com.broadsoft.xmeeting.activity;
 
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListView;

import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.adapter.MemberInfoAdapter;

public class MeetingGuideCatalogMemberInfoActivity extends Activity {

	private String TAG="MeetingGuideCatalogMemberInfoActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingguide_catalog_memberinfo);
		ExpandableListView mExpandableListView = (ExpandableListView)findViewById(R.id.m_list); 
		
		
		JSONObject jsonInfo=getJSONInfo();
        MemberInfoAdapter memberInfoAdapter=new MemberInfoAdapter(this,jsonInfo);
        memberInfoAdapter.initializeData();
        mExpandableListView.setAdapter(memberInfoAdapter);
        mExpandableListView.setCacheColorHint(0);  //设置拖动列表的时候防止出现黑色背景 
//        mExpandableListView.setDivider(null);
        mExpandableListView.setGroupIndicator(this.getResources().getDrawable(R.drawable.expandlist_group_indicator));
//        mExpandableListView.expandGroup(0);   
		 
	}
	
	
	private JSONObject getJSONInfo() {
		DownloadInfoEntity downloadInfoEntity=EntityInfoHolder.getInstance().getDownloadInfoEntity();
		JSONObject downloadJsonObject=createJSONObject(downloadInfoEntity.getJsonData());
		JSONObject jsonMeetingInfo=null;
		try {
			jsonMeetingInfo=downloadJsonObject.getJSONObject("personnelInfo");
		} catch (JSONException e) { 
			e.printStackTrace();
			Log.e(TAG, "[getJSONInfo] e--->"+e.toString());
		}
		return jsonMeetingInfo;
	}
  
	private   JSONObject createJSONObject(String strJson) {
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(strJson);
		} catch (JSONException e) { 
			Log.e(TAG, "[createJSONObject] e--->"+e.toString());
		}
		return jObj;
	}
  
	
	
	
	

	
}
