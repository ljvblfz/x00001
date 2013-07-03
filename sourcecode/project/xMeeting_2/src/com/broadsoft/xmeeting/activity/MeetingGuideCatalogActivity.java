package com.broadsoft.xmeeting.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.common.SlidingMenuView;
import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.LoginActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.htmldata.MeetingGuideHtmlDataSupport;

public class MeetingGuideCatalogActivity extends ActivityGroup {

//	private static String url = "http://172.29.135.149/operation/rs/svrEboardnotice?pageNum=1&status=&etype=&lname=&equipmentno=&sname=&roadname=";
	LinearLayout  slidingMenuView;
	
	private ArrayList<LinearLayout> lyList = new ArrayList<LinearLayout>();
	
	ViewGroup tabcontent;
	
	private boolean bsShow = true;
	private Activity act = this;
	

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		DesktopActivity.releaseLoading(hasFocus);
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_group);
 
		InitTopbarAndBack();
		

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        slidingMenuView = (LinearLayout ) findViewById(R.id.sliding_menu_view);
        
        tabcontent = (ViewGroup) slidingMenuView.findViewById(R.id.sliding_body);

        initUI();
        showDefaultTab();
		 
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

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
	
	
	private void initUI()
    {
		//行程安排
    	lyList.add((LinearLayout) this.findViewById(R.id.ly1));
    	this.findViewById(R.id.ly1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(act,MeetingGuideCatalogScheduleInfoActivity.class);
		    	View view = getLocalActivityManager().startActivity(MeetingGuideCatalogScheduleInfoActivity.class.getName(), i).getDecorView();
				tabcontent.removeAllViews();
				tabcontent.addView(view);

				refreshMenu(0);
			}
		});
    	//人员名单
    	lyList.add((LinearLayout) this.findViewById(R.id.ly2));
    	this.findViewById(R.id.ly2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(act,MeetingGuideCatalogMemberInfoActivity.class);
		    	View view = getLocalActivityManager().startActivity(MeetingGuideCatalogMemberInfoActivity.class.getName(), i).getDecorView();
				tabcontent.removeAllViews();
				tabcontent.addView(view);

				refreshMenu(1);
			}
		});
    	//车辆安排
    	lyList.add((LinearLayout) this.findViewById(R.id.ly3));
    	this.findViewById(R.id.ly3).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(act,MeetingGuideCatalogBusInfoActivity.class);
		    	View view = getLocalActivityManager().startActivity(MeetingGuideCatalogBusInfoActivity.class.getName(), i).getDecorView();
				tabcontent.removeAllViews();
				tabcontent.addView(view);
				refreshMenu(2);
			}
		});
    	//天气情况
    	lyList.add((LinearLayout) this.findViewById(R.id.ly4));
    	this.findViewById(R.id.ly4).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(act,MeetingGuideCatalogWeatherInfoActivity.class);
		    	View view = getLocalActivityManager().startActivity(MeetingGuideCatalogWeatherInfoActivity.class.getName(), i).getDecorView();
				tabcontent.removeAllViews();
				tabcontent.addView(view);
				refreshMenu(3);
			}
		});
    	//通讯服务
    	lyList.add((LinearLayout) this.findViewById(R.id.ly5));
    	this.findViewById(R.id.ly5).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(act,MeetingGuideCatalogContactInfoActivity.class);
		    	View view = getLocalActivityManager().startActivity(MeetingGuideCatalogContactInfoActivity.class.getName(), i).getDecorView();
				tabcontent.removeAllViews();
				tabcontent.addView(view);
				refreshMenu(4);
			}
		});
    	

    	lyList.add((LinearLayout) this.findViewById(R.id.ly6));
    	this.findViewById(R.id.ly6).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {  
//				Toast toast=Toast.makeText(MeetingGuideCatalogActivity.this, "彩信发送成功.",Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.show();
				

				Intent i = new Intent(act,MeetingGuideCatalogMmsInfoActivity.class);
		    	View view = getLocalActivityManager().startActivity(MeetingGuideCatalogMmsInfoActivity.class.getName(), i).getDecorView();
				tabcontent.removeAllViews();
				tabcontent.addView(view);
				refreshMenu(5);
			}
		});
    }//end of initUI
 
   
    
    
    
    void showDefaultTab(){
    	Intent i = new Intent(this,MeetingGuideCatalogScheduleInfoActivity.class);
    	View v = getLocalActivityManager().startActivity(MeetingGuideCatalogScheduleInfoActivity.class.getName(), i).getDecorView();
		tabcontent.removeAllViews();
		tabcontent.addView(v);
		refreshMenu(0);
		
    }
    
    private void refreshMenu(int pisition)
    {
    	for (int i = 0; i < lyList.size(); i++)
    	{
    		if (pisition == i)
    		{
    			ImageView iv = (ImageView) lyList.get(i).getChildAt(0);
    			iv.setImageResource(getSelectImage(i, true));
    			TextView tv = (TextView) lyList.get(i).getChildAt(1);
    			tv.setTextColor(Color.rgb(0, 187, 238));    			
    		}
    		else
    		{
    			ImageView iv = (ImageView) lyList.get(i).getChildAt(0);
    			iv.setImageResource(getSelectImage(i, false));
    			TextView tv = (TextView) lyList.get(i).getChildAt(1);
    			tv.setTextColor(android.graphics.Color.WHITE);
    		}
    	}
    }
    
    private int getSelectImage(int position, boolean bSelected)
    {
    	if (bSelected == false)
    	{
    		if (position == 0)
    			return R.drawable.v5_2_1_desktop_list_apps_center;
    		if (position == 1)
    			return R.drawable.v5_2_0_desktop_list_hot;
    		if (position == 2)
    			return R.drawable.v5_2_1_desktop_list_friends;
    		if (position == 3)
    			return R.drawable.v5_2_1_desktop_list_location;
    		if (position == 4)
    			return R.drawable.v5_2_1_desktop_list_newsfeed;
    		else
    			return 0;
    		
    	}
    	else
    	{
    		if (position == 0)
    			return R.drawable.v5_2_1_desktop_list_apps_center_blue;
    		if (position == 1)
    			return R.drawable.v5_2_0_desktop_list_hot_blue;
    		if (position == 2)
    			return R.drawable.v5_2_1_desktop_list_friends_blue;
    		if (position == 3)
    			return R.drawable.v5_2_1_desktop_list_location_blue;
    		if (position == 4)
    			return R.drawable.v5_2_1_desktop_list_newsfeed_blue;
    		else
    			return 0;
    	}
    }
	

	
}
