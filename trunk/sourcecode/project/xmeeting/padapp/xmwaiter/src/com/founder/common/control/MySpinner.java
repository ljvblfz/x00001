package com.founder.common.control;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Spinner;

import com.broadsoft.waiter.R;

public class MySpinner extends Spinner implements OnItemClickListener {

	public static SelectDialog dialog = null;
	private ArrayList<String> list;//ArrayList<String> list�洢��Ҫ��ʾ������ 
	public static String text;

	public MySpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
    //�����ͼ������OnClickListener�����������ô˷�����ִ��
	@Override
	public boolean performClick() {
		Context context = getContext();
		final LayoutInflater inflater = LayoutInflater.from(getContext());
		final View view = inflater.inflate(R.layout.formcustomspinner, null);
		final ListView listview = (ListView) view
				.findViewById(R.id.formcustomspinner_list);
		ListviewAdapter adapters = new ListviewAdapter(context, getList());
		listview.setAdapter(adapters);
		listview.setOnItemClickListener(this);
		dialog = new SelectDialog(context, R.style.MySpinnerDialog);//����Dialog��������ʽ����
		LayoutParams params = new LayoutParams(650, LayoutParams.FILL_PARENT);
		dialog.setCanceledOnTouchOutside(true);// ���õ��Dialog�ⲿ��������ر�Dialog
		dialog.show();
		dialog.addContentView(view, params);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> view, View itemView, int position,
			long id) {
		setSelection(position);
		setText(list.get(position));
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}

	public String getText() {
		return text;
	}

	@SuppressWarnings("static-access")
	public void setText(String text) {
		this.text = text;
	}

}
