package com.xmeeting;

import java.util.ArrayList;
import java.util.Calendar;
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

import com.founder.enforcer.R;


public class ChatActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */

	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	
	private String position,meetingId; 
	
	private static Map<String,Map<String,List<ChatMsgEntity>>> meetingChatList = new HashMap<String, Map<String,List<ChatMsgEntity>>>();
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        //参与发送消息的宾客座位
        position=intent.getStringExtra("position"); 
        meetingId=intent.getStringExtra("meetingId"); 
        
        setContentView(R.layout.chat_xiaohei);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        initView();
        
        initData();
    }
    
    
    public void initView()
    {
    	mListView = (ListView) findViewById(R.id.listview);
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);

    	((TextView)findViewById(R.id.textView1)).setText("向"+position+"发送的消息");
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
    	mDataArrays=NotifyHandle.getInstance().getChatMsgList(position, meetingId);
    	
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
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setName("我");
			entity.setMsgType(false);
			entity.setText(contString);
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			NotifyHandle.getInstance().storeMsg(position, meetingId, entity);
			
			mEditTextContent.setText("");
			
			mListView.setSelection(mListView.getCount() - 1);
		}
	}
	
    private String getDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));
        
        
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
        						
        						
        return sbBuffer.toString();
    }
    
    
    public void head_xiaohei(View v) {     //标题栏 返回按钮
    	//Intent intent = new Intent (ChatActivity.this,InfoXiaohei.class);			
		//startActivity(intent);	
      } 
}