package com.broadsoft.xmeeting.adapter;
 

import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.R.id;
import com.broadsoft.xmeeting.R.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

public class DataGridAdapter extends BaseAdapter {
	Context mContext;
	private String[] id = { "编号", "S002", "S003", "S004", "S005", "S006", "S007", "S012", "S013", "S014", "S015", "S016", "S017" };
	private String[] name = { "名字", "张三", "Ravi", "Amit", "Arun", "Anil", "Kashif", "张三", "Ravi", "Amit", "Arun", "Anil", "Kashif" };
//	private String[] company = { "公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司", "网路技术有限公司" };
	private String[] stafflevel = { "职务", "职员", "经理", "职员", "职员", "职员", "职员" , "职员", "经理", "职员", "职员", "职员", "职员"};
	private String[] contactinfo = { "联系方式", "13912345678", "13912345678", "13912345678", "13912345678", "13912345678", "13912345678" , "13912345678", "13912345678", "13912345678", "13912345678", "13912345678", "13912345678"};
 

	private LayoutInflater mInflater;

	public DataGridAdapter(Context c) {
		mContext = c;
		mInflater = LayoutInflater.from(c);
		Log.d("DataGridAdapter-->", "DataGridAdapter....");
//		dataGridModel.init();
	}

	public int getCount() {
		Log.d("DataGridAdapter-->", "getCount");
		return id.length;
	}

	public Object getItem(int position) {
		Log.d("DataGridAdapter-->", "getItem");
		return position;
	}

	public long getItemId(int position) {
		Log.d("DataGridAdapter-->", "getItemId");
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d("DataGridAdapter-->", "getView");
		View customgridView = createView(parent);
		TextView txtIdView;
		TextView txtNameView;
//		TextView txtCompanyView;
		TextView txtStaffLevelView;
		TextView txtContactinfoView;
		
		
		txtIdView = (TextView) customgridView.findViewById(R.id.txtId);
//		txtIdView.setPadding(100, 10, 10, 10);
//		txtIdView.setWidth(200);
		txtNameView = (TextView) customgridView.findViewById(R.id.txtName);
//		txtNameView.setPadding(100, 10, 10, 10);
//		txtCompanyView = (TextView) customgridView.findViewById(R.id.txtCompany);
//		txtCompanyView.setPadding(100, 10, 10, 10);
		txtStaffLevelView = (TextView) customgridView.findViewById(R.id.txtStaffLevel);
//		txtStaffLevelView.setPadding(100, 10, 10, 10);
		//
		txtContactinfoView = (TextView) customgridView.findViewById(R.id.txtContactinfo);
//		txtContactinfoView.setPadding(100, 10, 10, 10);
		
		
		
		TableRow tableRow = (TableRow) customgridView.findViewById(R.id.TableRow01);
		if(position==0){ 
			tableRow.setBackgroundColor(Color.GRAY);
			txtIdView.setBackgroundColor(Color.GRAY);
			txtNameView.setBackgroundColor(Color.GRAY);
//			txtCompanyView.setBackgroundColor(Color.GRAY);
			txtStaffLevelView.setBackgroundColor(Color.GRAY);
			txtContactinfoView.setBackgroundColor(Color.GRAY);
			txtIdView.setTextColor(Color.WHITE);
			txtNameView.setTextColor(Color.WHITE);
//			txtCompanyView.setTextColor(Color.WHITE);
			txtStaffLevelView.setTextColor(Color.WHITE);
			txtContactinfoView.setTextColor(Color.WHITE);
		}
		//
		txtIdView.setText(id[position]);
		txtNameView.setText(name[position]);
//		txtCompanyView.setText(company[position]);
		txtStaffLevelView.setText(stafflevel[position]); 
		txtContactinfoView.setText(contactinfo[position]); 
		return customgridView;
	}

	private View createView(ViewGroup parent) {
		View convertViewx = mInflater.inflate(R.layout.meetingguide_memberinfo_customgrid, parent, false);
		return convertViewx;
	}

}