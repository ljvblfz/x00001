package com.broadsoft.xmeeting.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.R.drawable;
import com.broadsoft.xmeeting.R.id;
import com.broadsoft.xmeeting.R.layout;
import com.broadsoft.xmeeting.adapter.ImageGallaryMainAdapter;


/**
 * http://www.cnblogs.com/allin/archive/2010/05/11/1732200.html
 * http://blog.csdn.net/hellogv/article/details/4542668
 * @author lu.zhen 
 *
 */
public class ImageGallaryMainActivity extends BaseActivity {
	private List<Map<String, Object>> mData;

	private LinearLayout maincontent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mData = getData();
        ImageGallaryMainAdapter adapter = new ImageGallaryMainAdapter(this,mData); 
        ListView listView= new ListView(this);
        listView.setAdapter(adapter); 
		setContentView(R.layout.imagegallary_activity_main); 

		maincontent = (LinearLayout) findViewById(R.id.imagegallarycontent);
		maincontent.addView(listView);
    }
 
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("imagetitle", "江苏电力公司工会展览");
        map.put("imageinfo", "google 1");
        map.put("imgpreview", R.drawable.company);
        list.add(map);
 
        map = new HashMap<String, Object>();
        map.put("imagetitle", "苏州电力公司工会展览");
        map.put("imageinfo", "google 2");
        map.put("imgpreview", R.drawable.coffee);
        list.add(map);
 
        map = new HashMap<String, Object>();
        map.put("imagetitle", "南京电力公司工会展览");
        map.put("imageinfo", "google 3");
        map.put("imgpreview", R.drawable.huiyizhinan);
        list.add(map);
         
        return list;
    }
    
    
    
    public final static String PIC_TITLE_NAME="PIC_TITLE_NAME";
    public final static String PIC_TITLE_KEY="PIC_TITLE_KEY"; 
    //called by adapter===========================================>
	public void startPictureViewActivity(Button btnImageMore,final String titleName,final String titleKey ) { 
		btnImageMore.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				Bundle bundle = new Bundle();//该类用作携带数据 
				bundle.putString(PIC_TITLE_NAME, titleName);
				bundle.putString(PIC_TITLE_KEY, titleKey);
				Intent intent = new Intent();
				intent.setClass(ImageGallaryMainActivity.this, ImageGallaryViewFlipperActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				intent.putExtras(bundle);//附带上额外的数据   
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}//end of on click
		});

	}//
   
     
    
}
