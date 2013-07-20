package com.broadsoft.xmeeting.uihandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.xmeeting.ChatActivity;
import com.xmeeting.ToDoEntity;
import com.xmeeting.WaiterMainActivity;

public class ToDoUIHandler extends Handler {
	private final String TAG = "NotifyUIHandler";
	private WaiterMainActivity act2;

	private static ToDoUIHandler notifyUIHandler;
	public ToDoUIHandler(WaiterMainActivity act2) {
		this.act2 = act2;
	}

	private void setAct2(WaiterMainActivity act){
		this.act2 = act;
	}

	
	public static void initToDo(WaiterMainActivity act2){
		if(null==notifyUIHandler){
			notifyUIHandler=new ToDoUIHandler(act2);
		}
	}//end of init
	
	
	public static ToDoUIHandler getInstance(){
		return notifyUIHandler;
	}


	
	
	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin");
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload");
		try {
			JSONObject jo = new JSONObject(payload);
			if(jo.has("msgcontent")){
//				act2.notifyMsgList(jo.getString("msgcontent"));
				ToDoEntity toDoEntity = new ToDoEntity();
				toDoEntity.setPosition(jo.getString("fromDisplayName"));
				toDoEntity.setChecked(false);
				toDoEntity.setType(jo.getString("msgcontent"));
				toDoEntity.setTodoId((toDoData.size()+1)+"");
				SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				toDoEntity.setTimeStr(smp.format(new Date()));
				toDoData.add(0,toDoEntity);
				act2.notifyAdapter();
				showDialog("您收到一个新任务");
			}
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		Log.d(TAG, "handleMessage end");
	}
	

    private List<ToDoEntity> toDoData = new ArrayList<ToDoEntity>(); 
    
    public List<ToDoEntity> getToDoData(){
    	return toDoData;
    }
    
    public void setCheck(boolean checked , int positionId){
    	for(ToDoEntity t: toDoData){
    		if(t.getPosition().equals(positionId+"")){
    			t.setChecked(checked);
    			break;
    		}
    	}
    	System.out.println("================setchecked================"+toDoData);
    }
	 
	public void sendControllerMessage(String payload){
		Message msg = new Message();  
        Bundle bundle = new Bundle();  
        bundle.putString("payload", payload);  
        msg.setData(bundle);   
        sendMessage(msg);
	}
	

	protected void showDialog(String msg) {
		Toast toast = Toast.makeText(act2, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	
//	private ArrayList<String> messageList=new ArrayList<String>();
//	public ArrayList<String> getMessageList() {
//		return messageList;
//	}
	
 
	
}
