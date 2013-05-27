package com.broadsoft.xmeeting.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.adapter.MemberInfoAdapter;

public class MeetingGuideCatalogMemberInfoActivity extends Activity {
 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meetingguide_catalog_memberinfo);
		ExpandableListView mExpandableListView = (ExpandableListView)findViewById(R.id.m_list); 
        MemberInfoAdapter memberInfoAdapter=new MemberInfoAdapter(this);
        memberInfoAdapter.initializeData();
        mExpandableListView.setAdapter(memberInfoAdapter);
        mExpandableListView.setCacheColorHint(0);  //设置拖动列表的时候防止出现黑色背景 
        mExpandableListView.expandGroup(0);  
		 
	}
  
	
	
	
	

	
}
