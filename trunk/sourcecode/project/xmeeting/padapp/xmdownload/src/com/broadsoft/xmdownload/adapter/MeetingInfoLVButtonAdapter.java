package com.broadsoft.xmdownload.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
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
import com.broadsoft.xmdownload.R;
import com.broadsoft.xmdownload.rsservice.RsServiceOnMeetingInfoSupport;

public class MeetingInfoLVButtonAdapter extends BaseAdapter {

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
			if("1".equals(status)){
				map.put("meetingStatus", "激活"); 
			}else{
				map.put("meetingStatus", ""); 
			}
			mAppList.add(map);
		}//end of for 
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
			convertView = mInflater.inflate(R.layout.meeting_list_item, null);
			holder.tvMeetingId = (TextView) convertView
					.findViewById(R.id.tvMeetingId);
			holder.tvMeetingTitle = (TextView) convertView
					.findViewById(R.id.tvMeetingTitle);
			holder.tvMeetingStatus = (TextView) convertView
					.findViewById(R.id.tvMeetingStatus);
			//
			holder.btnActivate = (Button) convertView
					.findViewById(R.id.btnActivate);
			holder.downloadInfo = (Button) convertView
					.findViewById(R.id.downloadInfo);
//			holder.btnEntry = (Button) convertView.findViewById(R.id.btnEntry);
			//
			convertView.setTag(holder);
		}
		// 初始化数据
		HashMap<String, Object> meetingItemInfo = mAppList.get(position);
		if (meetingItemInfo != null) {
			String strMeetingId = (String) meetingItemInfo.get("meetingId");
			String strMeetingTitle = (String) meetingItemInfo.get("meetingTitle");
			String strMeetingStatus = (String) meetingItemInfo.get("meetingStatus");
			holder.tvMeetingId.setText(strMeetingId.substring(20));
			holder.tvMeetingTitle.setText(strMeetingTitle);
			holder.tvMeetingStatus.setText(strMeetingStatus);
			//
			holder.downloadInfo.setOnClickListener(new LVBtnCommonListener("1",
					strMeetingId));
			holder.btnActivate.setOnClickListener(new LVBtnCommonListener("2",
					strMeetingId));
//			holder.btnEntry.setOnClickListener(new LVBtnCommonListener("3",
//					strMeetingId));
		}// end of if
		return convertView;
	} // end of getView

	class ListViewButtonViewHolder {

		TextView tvMeetingId;
		TextView tvMeetingTitle;
		TextView tvMeetingStatus;
		//
		Button downloadInfo;
		Button btnActivate;
//		Button btnEntry;
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
			Log.d(TAG, "type is :  " + strType + " ----meeting id is: "
					+ strMeetingId);
			if ("1".equals(strType)) {
				// downloadd
				RsServiceOnMeetingInfoSupport.downloadWithoutFile(strMeetingId);
			} else if ("2".equals(strType)) {
				// activate  
				DaoHolder.getInstance().getDownloadInfoDao().activate(strMeetingId);
			} 
//			else if ("3".equals(strType)) { 
//			}
		}
	}// end of LVBtnCommonListener 

}