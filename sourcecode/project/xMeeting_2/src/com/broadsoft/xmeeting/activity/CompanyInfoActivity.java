package com.broadsoft.xmeeting.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

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
		
		InitTopbarAndBack();
		InitTabHost();
	
	}
	
	private void InitTabHost()
	{
		_tabHost = getTabHost();

        TabSpec tabSpec=iniTabs(CompanyInfoBasic.class,"0","公司介绍", 0);
        _tabHost.addTab(tabSpec);
        
        tabSpec=iniTabs(CompanyInfoLeader.class,"1","领导风采", 1);
        _tabHost.addTab(tabSpec);
        
        
        tabSpec=iniTabs(CompanyInfoOrg.class,"2","组织架构", 2);
        _tabHost.addTab(tabSpec);
        
        
        TabWidget widget = act.getTabHost().getTabWidget();
        widget.setBackgroundColor(writeColor);
        
        
        _tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
	    		//获得当前所有的TabWidget
	    		TabWidget widget = act.getTabHost().getTabWidget() ;
	    		int num = widget.getChildCount() ;
	    		for(int i=0;i<num;i++){
	    			LinearLayout linearLayout = (LinearLayout)widget.getChildAt(i);
	    			TextView textView=(TextView)widget.getChildAt(i).findViewById(R.id.tab_text);
	    			if( Integer.valueOf(tabId) == i){
	    				//如果某个tab被选中，则更换背景图片
	    				linearLayout.setBackgroundResource(R.drawable.v5_0_1_tabsbar_selected_bg);
	    				textView.setTextColor(blackColor);
	    				
	    			}
	    			else
	    			{
	    				//未选中的，则使用默认背景图
	    				linearLayout.setBackgroundResource(R.drawable.v5_0_1_tabsbar_bg);
	    				textView.setTextColor(grayColor);
	    			}
	    		}
			}});
	}
	
	public TabSpec iniTabs(Class<?>switchclass,String tag,String title,int index)
    {  	
    	Intent intent1 = act.getIntent();
        String sType = intent1 .getStringExtra("Type");
    	
    	Intent intent2 = new Intent(this,switchclass);
    	intent2.putExtra("Type", sType);
        View tab=LayoutInflater.from(this).inflate(R.layout.company_activity_main_tabitem, null);
        TextView textView=(TextView)tab.findViewById(R.id.tab_text);
        
        if (index == 0)
        {
        	tab.setBackgroundResource(R.drawable.v5_0_1_tabsbar_selected_bg);
        	textView.setTextColor(blackColor);
        }
        else
        {
        	tab.setBackgroundResource(R.drawable.v5_0_1_tabsbar_bg);	
        	textView.setTextColor(grayColor);
        }
        
        
        textView.setText(title);  
        TabSpec tempSpec= _tabHost.newTabSpec(tag);
        tempSpec.setIndicator(tab);
        tempSpec.setContent(intent2);
        return tempSpec;
    }
	
	
	private void InitTopbarAndBack()
	{
		( (Button) this.findViewById(R.id.btnBack) ).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				//overridePendingTransition(R.anim.zoom_enter,android.R.anim.fade_out);
			}
		});
	}

 

}
