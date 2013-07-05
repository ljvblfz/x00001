package com.broadsoft.xmeeting.activity;


import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.R;

public class NotificationListActivity extends Activity {
	
	private final static String TAG="NotificationListActivity";

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		DesktopActivity.releaseLoading(hasFocus); 
		super.onWindowFocusChanged(hasFocus);
	} 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_activity_main); 
		ListView lvNotificationList=(ListView)this.findViewById(R.id.lvNotificationList);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
		 //生成动态数组，加入数据  
        ArrayList<HashMap<String, Object>> notificationListItem = new ArrayList<HashMap<String, Object>>();  
        for(int i=0;i<20;i++)  
        {  
            HashMap<String, Object> map = new HashMap<String, Object>();     
            map.put("notificationSeq", ""+(i+1));  
            map.put("notificationContent", "冯总,请出来一下,王总找你.");  
            map.put("notificationStatus", "未读");  
            notificationListItem.add(map);  
        }  
        //生成适配器的Item和动态数组对应的元素  
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,notificationListItem, 
            R.layout.notification_list_item,//ListItem的XML实现  
            //动态数组与ImageItem对应的子项          
            new String[] {"notificationSeq","notificationContent","notificationStatus"},   
            //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
            new int[] { R.id.tvNotificationSeq,R.id.tvNotificationContent,R.id.tvNotificationStatus}  
        );  
        lvNotificationList.setAdapter(listItemAdapter);
        
        
        //添加点击  
        lvNotificationList.setOnItemClickListener(new OnItemClickListener() {   
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,   long arg3) {   
            	Log.d(TAG, "onItemClick");
            }  
        });  
          
        //添加长按点击  
        lvNotificationList.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {  
              
            @Override  
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {   
            	Log.d(TAG, "onCreateContextMenu");    
            }  
        });    
        initTopbarAndBack();
	}//end of onCreate

	 
	
	private void initTopbarAndBack(){
		( (Button) this.findViewById(R.id.btnBack) ).setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				finish(); 
			}
		});
	}//end of initTopbarAndBack
 
	
 
	
}
