package com.broadsoft.xmeeting.uihandler;

import java.util.ArrayList;

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

public class NotifyUIHandler extends Handler {
	private final String TAG = "NotifyUIHandler";
	private Activity act;

	public NotifyUIHandler(Activity act) {
		this.act = act;
	}

	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin");
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload");
		try {
			JSONObject jo = new JSONObject(payload);
			String msgtype = jo.getString("msgtype");
			if ("01".equals(msgtype)) {
				String msgcontent = jo.getString("msgcontent");
				messageList.add(msgcontent);
				showDialog(msgcontent);
//				showDialog2(msgcontent);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d(TAG, "handleMessage end");
	}

	protected void showDialog(String msg) {
		Toast toast = Toast.makeText(act, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	protected void showDialog2(String msg) {
		AlertDialog.Builder builder = new Builder(act);
		builder.setMessage(msg);

		builder.setTitle("提示");

		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}
	
	
	public ArrayList<String> messageList=new ArrayList<String>();
}
