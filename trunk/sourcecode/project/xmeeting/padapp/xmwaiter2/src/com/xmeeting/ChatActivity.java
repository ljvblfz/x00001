package com.xmeeting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.broadsoft.xmeeting.uihandler.NotifyUIHandler;
import com.broadsoft.xmeeting.wsservice.WsControllerServiceSupport;
import com.founder.enforcer.R;


public class ChatActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */

	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	
	private String position,meetingId,company,name_title,memberId,memberDisplayName; 
	
	private static Map<String,Map<String,List<ChatMsgEntity>>> meetingChatList = new HashMap<String, Map<String,List<ChatMsgEntity>>>();
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //参与发送消息的宾客座位
        position=intent.getStringExtra("name_title"); 
        meetingId=intent.getStringExtra("meetingId"); 
        company=intent.getStringExtra("company"); 
		name_title=intent.getStringExtra("name_title"); 
		memberId=intent.getStringExtra("memberId"); 
		memberDisplayName=intent.getStringExtra("memberDisplayName"); 
        setContentView(R.layout.chat_xiaohei);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        initView();
        
        initData();
        NotifyUIHandler.init(this);
        
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	NotifyUIHandler.getInstance().destory();
    }
    
    public void initView()
    {
    	mListView = (ListView) findViewById(R.id.listview);
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);

    	((TextView)findViewById(R.id.textView1)).setText("向"+company+"  "+name_title+"发送消息");
    	mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    	
    	
    }
    
//    private String[]msgArray = new String[]{"赶紧过来倒茶...",
//    										"收到。",
//    										"赶紧过来倒茶...", 
//    										"收到。", 
//    										"赶紧过来倒茶...",
//    										"收到。", 
//    										"赶紧过来倒茶...",
//    										"收到。"};
//    
//    private String[]dataArray = new String[]{"2012-09-01 18:00", "2012-09-01 18:10", 
//    										"2012-09-01 18:11", "2012-09-01 18:20", 
//    										"2012-09-01 18:30", "2012-09-01 18:35", 
//    										"2012-09-01 18:40", "2012-09-01 18:50"}; 
//    private final static int COUNT = 8;
    public void initData()
    {
    	mDataArrays.addAll(NotifyHandle.getInstance().getChatMsgList(position, meetingId));
    	
//    	for(int i = 0; i < COUNT; i++)
//    	{
//    		ChatMsgEntity entity = new ChatMsgEntity();
//    		entity.setDate(dataArray[i]);
////    		if (i % 2 == 0)
////    		{
////    			entity.setName("一号桌");
////    			entity.setMsgType(true);
////    		}else{
//			entity.setName("我");
//			entity.setMsgType(false);
////    		}
//    		
//    		entity.setText(msgArray[i]);
//    		mDataArrays.add(entity);
//    	}

    	mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_send:
			send();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	private void send()
	{
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0)
		{
//			try{
				WsControllerServiceSupport.getInstance().sendMessageServiceMessage(contString, memberId);
//			}catch(Exception ae){
//				ae.printStackTrace();
//			}
		}
	}


	public void notifyMsgList(String contString) {
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setDate(getDate());
		entity.setName("我");
		entity.setMsgType(false);
		System.out.println("--------------------------1");
		entity.setText(contString);
		System.out.println("--------------------------2");
		mDataArrays.add(entity);
		System.out.println("--------------------------3.1");
//		mDataArrays = NotifyHandle.getInstance().getChatMsgList(position, meetingId);
//		mAdapter = null;
//		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mAdapter.notifyDataSetChanged();
		NotifyHandle.getInstance().storeMsg(position, meetingId, entity);
		System.out.println("--------------------------4");
		System.out.println("--------------------------5");
		mEditTextContent.setText("");
		System.out.println("--------------------------6");
		mListView.setSelection(mListView.getCount() - 1);
		System.out.println("--------------------------7");
	}
	
	
	
    private String getDate() {
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");						
        						
        return sdf.format(new Date());
    }
    
    
    public void head_xiaohei(View v) {     //标题栏 返回按钮
    	//Intent intent = new Intent (ChatActivity.this,InfoXiaohei.class);			
		//startActivity(intent);	
      } 
}