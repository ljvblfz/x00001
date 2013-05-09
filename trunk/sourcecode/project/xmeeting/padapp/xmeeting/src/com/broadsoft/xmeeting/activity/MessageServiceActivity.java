package com.broadsoft.xmeeting.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.broadsoft.common.BaseActivity; 
import com.broadsoft.xmeeting.R;


/**
 * list view article 
 * http://www.vogella.com/articles/AndroidListView/article.html
 * http://www.androidhive.info/2011/10/android-listview-tutorial/
 * @author lu.zhen
 *
 */
public class MessageServiceActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message_activity_main);
		
		buildContactListView();  
		//
		buildLatestMsgListView();

 
	}

	
	/**
	 * 联系人
	 */
	protected void buildContactListView() {
		//联系人
	    ListView listView = (ListView) findViewById(R.id.ContactListView);
	    
	    //生成动态数组，并且转载数据  
	    ArrayList<HashMap<String, String>> mData = new ArrayList<HashMap<String, String>>();  
	    for(int i=0;i<30;i++)  
	    {  
	        HashMap<String, String> map = new HashMap<String, String>();  
	        map.put("ContactItemTitle", "张三"+i);  
	        map.put("ContactItemText", "角色"+i);  
	        mData.add(map);  
	    }  
	    
	    //生成适配器，数组===》ListItem  
	    SimpleAdapter contactAdapter = new SimpleAdapter(this, //没什么解释  
	                                                mData,//数据来源   
	                                                R.layout.message_listitem_contact,//ListItem的XML实现   
	                                                //动态数组与ListItem对应的子项          
	                                                new String[] {"ContactItemTitle", "ContactItemText"},    
	                                                //ListItem的XML文件里面的两个TextView ID  
	                                                new int[] {R.id.ContactItemTitle,R.id.ContactItemText});  
	    //添加并且显示  
	    listView.setAdapter(contactAdapter); 
	    listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) { 
            	TextView contactTitle=(TextView)view.findViewById(R.id.ContactItemTitle);
                String itemItem = contactTitle.getText().toString(); 
//                Toast.makeText(getBaseContext(), itemItem, Toast.LENGTH_LONG).show(); 
                showChatActivity();
            }
        });
	     
	}
	
	
	protected void showChatActivity(){
        Intent intent = new Intent();
		intent.setClass(MessageServiceActivity.this, MessageServiceChatActivity.class);// 指定了跳转前的Activity和跳转后的Activity
		intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
		startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity
		
	}
	

	/**
	 * 最新消息
	 */
	protected void buildLatestMsgListView() {
		//联系人
	    ListView listView = (ListView) findViewById(R.id.LatestMsgListView);
	    
	    //生成动态数组，并且转载数据  
	    ArrayList<HashMap<String, String>> mData = new ArrayList<HashMap<String, String>>();  
	    for(int i=0;i<30;i++)  
	    {  
	        HashMap<String, String> map = new HashMap<String, String>();  
	        map.put("MsgItemSentPerson", "张三"+i);   
	        map.put("MsgItemSentTime", "2012-10-10 11:22:40");   
	        map.put("MsgItemTitle", "消息主题消息主题消息主题"+i);   
	        map.put("MsgItemContent", "消息内容消息内容消息内容消息内容消息内容消息内容"+i);   
	        mData.add(map);  
	    }  
	    
	    //生成适配器，数组===》ListItem  
	    SimpleAdapter contactAdapter = new SimpleAdapter(this, //没什么解释  
	                                                mData,//数据来源   
	                                                R.layout.message_listitem_latestmsg,//ListItem的XML实现   
	                                                //动态数组与ListItem对应的子项          
	                                                new String[] {"MsgItemSentPerson", "MsgItemSentTime", "MsgItemTitle", "MsgItemContent"},    
	                                                //ListItem的XML文件里面的两个TextView ID  
	                                                new int[] {R.id.MsgItemSentPerson,R.id.MsgItemSentTime,R.id.MsgItemTitle,R.id.MsgItemContent});  
	    //添加并且显示  
	    listView.setAdapter(contactAdapter); 
	    listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) { 
            	TextView contactTitle=(TextView)view.findViewById(R.id.MsgItemSentPerson);
                String itemItem = contactTitle.getText().toString(); 
//                Toast.makeText(getBaseContext(), itemItem, Toast.LENGTH_LONG).show(); 
                showChatActivity();
            }
        });
	     
	}

	 
	
	 
}
