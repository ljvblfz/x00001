package com.broadsoft.xmeeting.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.R;

public class CompanyInfoActivity extends TabActivity{

	private TabHost _tabHost;
	private TabActivity act = this;
	private int grayColor = Color.parseColor("#666666");
	private int writeColor = Color.parseColor("#ffffff");
	private int blackColor = Color.parseColor("#000000");
	

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		DesktopActivity.releaseLoading(hasFocus);
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		initTopbarAndBack();
		initTabHost();
	
	}
	
	private void initTabHost()
	{
		_tabHost = getTabHost();

        initAllTabSpec();
        
        
        TabWidget widget = act.getTabHost().getTabWidget();
        widget.setBackgroundColor(writeColor);
        
        
        _tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() { 
			@Override
			public void onTabChanged(String tabId) { 
	    		//获得当前所有的TabWidget
	    		TabWidget widget = act.getTabHost().getTabWidget() ;
	    		int num = widget.getChildCount() ;
	    		for(int i=0;i<num;i++){
	    			LinearLayout linearLayout = (LinearLayout)widget.getChildAt(i);
	    			TextView textView=(TextView)widget.getChildAt(i).findViewById(R.id.tab_text);
					if (Integer.valueOf(tabId) == i) {
						// 如果某个tab被选中，则更换背景图片
						linearLayout.setBackgroundResource(R.drawable.v5_0_1_tabsbar_selected_bg);
						textView.setTextColor(blackColor);
					} else {
						// 未选中的，则使用默认背景图
						linearLayout.setBackgroundResource(R.drawable.v5_0_1_tabsbar_bg);
						textView.setTextColor(grayColor);
					}
	    		}//end of for
			}//end of onTabChanged
		});
	}

	private void initAllTabSpec() {

		String jsonData=EntityInfoHolder.getInstance().getDownloadInfoEntity().getJsonData();
		try {
			JSONObject downloadJsonObject=new JSONObject(jsonData);
			JSONObject jsonMeetingInfo=downloadJsonObject.getJSONObject("meetingInfo");
			JSONArray listOfXmCompanyInfo=jsonMeetingInfo.getJSONArray("listOfXmCompanyInfo"); 
			
			for(int i=0;i<listOfXmCompanyInfo.length();i++){ 
				JSONObject companyInfo=listOfXmCompanyInfo.getJSONObject(i);
				String xmciType=companyInfo.getString("xmciType"); 
				String isDisplay=companyInfo.getString("isDisplay"); 
				if("1".equals(xmciType)&&"1".equals(isDisplay)){
					createBasicTabSpec(i);
					
				}else if("2".equals(xmciType)&&"1".equals(isDisplay)){
			        createLeaderTabSpec(i);
					
				}else if("3".equals(xmciType)&&"1".equals(isDisplay)){
			        createOrgTabSpec(i);
					
				}
			}//end of for
			
		} catch (JSONException e) { 
			e.printStackTrace();
		} 
		//  
        
        
        
	}

	private void createOrgTabSpec(int index) {
		TabSpec tabSpec=initTabSpec(CompanyInfoOrg.class,String.valueOf(index),"组织架构", index);
        _tabHost.addTab(tabSpec);
	}

	private void createLeaderTabSpec(int index) {
		TabSpec  tabSpec=initTabSpec(CompanyInfoLeader.class,String.valueOf(index),"领导风采", index);
        _tabHost.addTab(tabSpec);
	}

	private void createBasicTabSpec(int index) {
		TabSpec tabSpec=initTabSpec(CompanyInfoBasic.class,String.valueOf(index),"公司介绍", index);
        _tabHost.addTab(tabSpec);
	}
	
	public TabSpec initTabSpec(Class<?> switchclass, String tag, String title, int index) {
    	Intent intent1 = act.getIntent();
        String sType = intent1 .getStringExtra("Type");
    	
    	Intent intent2 = new Intent(this,switchclass);
    	intent2.putExtra("Type", sType);
    	
    	//
        View tabView=LayoutInflater.from(this).inflate(R.layout.company_activity_main_tabitem, null);
        TextView textView=(TextView)tabView.findViewById(R.id.tab_text);
        
		if (index == 0) {
			tabView.setBackgroundResource(R.drawable.v5_0_1_tabsbar_selected_bg);
			textView.setTextColor(blackColor);
		} else {
			tabView.setBackgroundResource(R.drawable.v5_0_1_tabsbar_bg);
			textView.setTextColor(grayColor);
		}
        
        textView.setText(title);  
        TabSpec tempSpec= _tabHost.newTabSpec(tag);
        tempSpec.setIndicator(tabView);
        tempSpec.setContent(intent2);
        return tempSpec;
    }
	

	private int backCount=0;
	private void initTopbarAndBack()
	{
		( (Button) this.findViewById(R.id.btnBack) ).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backCount++; 
				if(backCount==1){
					finish();	
				} 
			}
		});
	}

 

}
