package com.broadsoft.xmeeting.adapter;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.activity.ImageGallaryMainActivity;

public class ImageGallaryMainAdapter extends BaseAdapter {
	private List<Map<String, Object>> mData;

	private LayoutInflater mInflater;
	private Context context;

	public ImageGallaryMainAdapter(Context context,
			List<Map<String, Object>> mData) {
		this.context = context;
		this.mData = mData;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ImageGallaryListViewHolder holder = null;
		if (convertView == null) {

			holder = new ImageGallaryListViewHolder();

			convertView = mInflater.inflate(R.layout.imagegallary_listview,
					null);
			holder.imagepreview = (ImageView) convertView
					.findViewById(R.id.imgpreview);
			holder.imagetitle = (TextView) convertView
					.findViewById(R.id.imagetitle);
			holder.imageinfo = (TextView) convertView
					.findViewById(R.id.imageinfo);
			holder.imagemore = (Button) convertView
					.findViewById(R.id.imagemore);
			convertView.setTag(holder);

		} else {
			holder = (ImageGallaryListViewHolder) convertView.getTag();
		}

		holder.imagepreview.setBackgroundResource((Integer) mData.get(position)
				.get("imgpreview"));
		holder.imagetitle.setText((String) mData.get(position)
				.get("imagetitle"));
		holder.imageinfo.setText((String) mData.get(position).get("imageinfo"));
		
		final String strTitle=holder.imagetitle.getText().toString();
		final String strKey="testkey";
		

		ImageGallaryMainActivity mainActivity=(ImageGallaryMainActivity)context;
		//mainActivity.startPictureViewActivity(holder.imagemore,strTitle,strKey);
		
//		holder.imagemore.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) { 
////				showImageMore(v.getId(),strTitle);
//			}
//		});

		return convertView;
	}// end of getView

 
	
	
	


	
	/**
	 * dialog demo:http://www.oschina.net/question/54100_32486
	 * listview中点击按键弹出对话框
	 */
	public void showImageMoreDialog(int id,String title) { 
		View dialogView = mInflater.inflate(R.layout.imagegallary_picview, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
		builder.setTitle("图片浏览--"+title);
		builder.setView(dialogView);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//TODO:
			}
		});
		builder.show(); 
	}
}