package com.founder.common.control;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.broadsoft.waiter.R;
import com.founder.common.data.BitmapLoader;


public class PhotoListViewer extends Activity{

	@SuppressWarnings("rawtypes")
	private ArrayList al;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_viewer);
        
        Bundle bundle = getIntent().getExtras();
        al = bundle.getParcelableArrayList("fliepathlist");
        
        
        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        // 创建用于描述图像数据的ImageAdapter对象
        ImageAdapter imageAdapter = new ImageAdapter(this);
        // 设置Gallery组件的Adapter对象
        gallery.setAdapter(imageAdapter);
        
        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        	@Override
        	public void onItemSelected(AdapterView<?> parent, View v,int position, long id) {
        	}

        	@Override
        	public void onNothingSelected(AdapterView<?> arg0) {
        	//这里不做响应
        	}
        });


	}
	
	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
			public ImageAdapter(Context context) {
			mContext = context;
		}

		public int getCount() { 
			return al.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView image = new ImageView(mContext);

			Bitmap b  = BitmapLoader.getBitmapByWidth((String) al.get(position), 900, 0);	
			image.setImageBitmap(b);
			image.setAdjustViewBounds(true);
			image.setLayoutParams(new Gallery.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			return image;
		}
	}
}
