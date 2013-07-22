package com.broadsoft.xmdownload.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmdownload.rsservice.RsServiceOnMeetingInfoSupport;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.uihandler.DownloadByHandUIHandler;

public class MeetingInfoLVButtonAdapter extends BaseAdapter {
	private String TAG="MeetingInfoLVButtonAdapter";

	private ListViewButtonViewHolder holder;
	//
	private ArrayList<HashMap<String, Object>> mAppList;
	private LayoutInflater mInflater;
	private Context mContext;
//	private String[] keyString;

	public MeetingInfoLVButtonAdapter(Context c) {
		mContext = c;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		init();
	}

	public void init() {
		mAppList = new ArrayList<HashMap<String, Object>>();
		List<DownloadInfoEntity> listOfMeetingInfo=DaoHolder.getInstance().getDownloadInfoDao().findAll(); 
		for(DownloadInfoEntity downloadInfoEntity:listOfMeetingInfo){  
			String status=downloadInfoEntity.getStatus();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("meetingId", downloadInfoEntity.getMeetingId());
			map.put("meetingTitle", downloadInfoEntity.getMeetingName());
			map.put("downloadTime", downloadInfoEntity.getDownloadTime());
			Log.d(TAG, "downloadTime is: "+downloadInfoEntity.getDownloadTime());
			if("1".equals(status)){
				map.put("meetingStatus", "激活"); 
			}else{
				map.put("meetingStatus", ""); 
			}
			mAppList.add(map);
		}//end of for 
	}
	
	
	public void reload(){
		init();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mAppList.size();
	}

	@Override
	public Object getItem(int position) {
		return mAppList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void removeItem(int position) {
		mAppList.remove(position);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView != null) {
			holder = (ListViewButtonViewHolder) convertView.getTag();
		} else {
			holder = new ListViewButtonViewHolder();
			convertView = mInflater.inflate(R.layout.download_meeting_list_item, null);
			holder.tvMeetingId = (TextView) convertView
					.findViewById(R.id.tvMeetingId);
			holder.tvMeetingTitle = (TextView) convertView
					.findViewById(R.id.tvMeetingTitle);
			holder.tvMeetingStatus = (TextView) convertView
					.findViewById(R.id.tvMeetingStatus);
			holder.tvDownloadTime = (TextView) convertView
					.findViewById(R.id.tvDownloadTime); 
			//
			holder.btnActivate = (Button) convertView
					.findViewById(R.id.btnActivate);
			holder.btnDelete = (Button) convertView
					.findViewById(R.id.btnDelete);
			holder.btnDownloadJsonInfo = (Button) convertView
					.findViewById(R.id.btnDownloadJsonInfo);
			holder.btnDownloadAllInfo = (Button) convertView.findViewById(R.id.btnDownloadAllInfo);
			//
			convertView.setTag(holder);
		}
		// 初始化数据
		HashMap<String, Object> meetingItemInfo = mAppList.get(position);
		if (meetingItemInfo != null) {
			String strMeetingId = (String) meetingItemInfo.get("meetingId");
			String strMeetingTitle = (String) meetingItemInfo.get("meetingTitle");
			String strMeetingStatus = (String) meetingItemInfo.get("meetingStatus");
			String downloadTime = (String) meetingItemInfo.get("downloadTime");
			holder.tvMeetingId.setText(strMeetingId.substring(28));
			holder.tvMeetingTitle.setText(strMeetingTitle);
			holder.tvMeetingStatus.setText(strMeetingStatus);
			holder.tvDownloadTime.setText(downloadTime);
			//
			holder.btnDownloadJsonInfo.setOnClickListener(new LVBtnCommonListener("1",
					strMeetingId));
			holder.btnDownloadAllInfo.setOnClickListener(new LVBtnCommonListener("2",
					strMeetingId));
			holder.btnActivate.setOnClickListener(new LVBtnCommonListener("3",
					strMeetingId));
			holder.btnDelete.setOnClickListener(new LVBtnCommonListener("4",
					strMeetingId));
		}// end of if
		return convertView;
	} // end of getView

	class ListViewButtonViewHolder {

		TextView tvMeetingId;
		TextView tvMeetingTitle;
		TextView tvMeetingStatus;
		TextView tvDownloadTime;
		//
		Button btnDownloadJsonInfo;
		Button btnDownloadAllInfo;
		Button btnActivate;
		Button btnDelete;
	}

	// =================button listener

	class LVBtnCommonListener implements OnClickListener {
		private String strType;
		private String strMeetingId;
		private String TAG = "LVBtnCommonListener";

		LVBtnCommonListener(String strType, String strMeetingId) {
			this.strType = strType;
			this.strMeetingId = strMeetingId;
		}

		@Override
		public void onClick(View v) {
			Log.d(TAG, "type is :  " + strType + " ----meeting id is: " + strMeetingId);
			if ("1".equals(strType)) {
				//download json info
				RsServiceOnMeetingInfoSupport.downloadByType(RsServiceOnMeetingInfoSupport.TYPE_DOWNLOAD_WITHOUT_FILE,strMeetingId); 
			} else if ("2".equals(strType)) {
				//download all info
				RsServiceOnMeetingInfoSupport.downloadByType(RsServiceOnMeetingInfoSupport.TYPE_DOWNLOAD_WITH_FILE,strMeetingId); 
			} else if ("3".equals(strType)) { 
				//activate  
				DaoHolder.getInstance().getDownloadInfoDao().activate(strMeetingId); 
				DownloadByHandUIHandler.getInstance().sendActivateMessage(); 
			}else if ("4".equals(strType)) { 
				//delete   
				new AlertDialog.Builder(mContext)
			 	.setTitle("确认")
			 	.setMessage("确定要删除会议信息吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {   
						DaoHolder.getInstance().getDownloadInfoDao().deleteByMeetingId(strMeetingId);
						DownloadByHandUIHandler.getInstance().sendDeleteMessage();
						dialog.dismiss();
					}
				}).setNegativeButton("取消", null).show();
				
			}
		}
	}// end of LVBtnCommonListener 

}