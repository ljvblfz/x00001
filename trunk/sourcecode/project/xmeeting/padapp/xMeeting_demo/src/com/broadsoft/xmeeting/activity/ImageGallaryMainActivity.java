package com.broadsoft.xmeeting.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.broadsoft.appsupport.AsyncBitmapLoader;
import com.broadsoft.common.MyPullDownLayoutView;
import com.broadsoft.common.MyPullDownLayoutView.OnPullDownListener;
import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.R;


import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class ImageGallaryMainActivity extends Activity implements OnPullDownListener, OnItemClickListener{

	private static final int WHAT_DID_LOAD_DATA = 0;
	private static final int WHAT_DID_REFRESH = 1;
	private static final int WHAT_DID_MORE = 2;
	private Activity act = this;
	private ListView mListView;
	private GalleyListAdapter mAdapter;
	private List<Map<String, Object>> mData;
	private MyPullDownLayoutView mPullDownView;
	private List<String> mStrings = new ArrayList<String>();
	private int currentSel = 0;
	int[] ids =
        {
            R.drawable.demo1,
            R.drawable.demo2,
            R.drawable.demo3,
            R.drawable.demo4,
            R.drawable.demo5,
            R.drawable.demo1,
            R.drawable.demo2,
            R.drawable.demo3,
            R.drawable.demo4,
            R.drawable.demo5
        };
	
	GalleryFlow mGallery = null;
    ArrayList<BitmapDrawable> mBitmaps = new ArrayList<BitmapDrawable>();
    
    View.OnClickListener mListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
            case R.id.space_confirm_btn:
                onSpaceBtnClick(v);
                break;
                
            case R.id.max_zoom_confirm_btn:
                onMaxZoomBtnClick(v);
                break;
            case R.id.max_rotate_angle_confirm_btn:
                onMaxAngleBtnClick(v);
                break;
            }
        }
    };
    
    private void onSpaceBtnClick(View v)
    {
        EditText editText = (EditText) findViewById(R.id.space_edittext);
        String text = editText.getText().toString();
        
        try
        {
            int spacing = Integer.parseInt(text);
            if (spacing >= -60 && spacing <= 60)
            {
                mGallery.setSpacing(spacing);
                ((GalleryAdapter)mGallery.getAdapter()).notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(this,
                        getResources().getString(R.string.gallery_space_text_hint),
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void onMaxZoomBtnClick(View v)
    {
        EditText editText = (EditText) findViewById(R.id.max_zoom_edittext);
        String text = editText.getText().toString();
        
        try
        {
            int maxZoom = Integer.parseInt(text);
            if (maxZoom >= -120 && maxZoom <= 120)
            {
                mGallery.setMaxZoom(maxZoom);
                ((GalleryAdapter)mGallery.getAdapter()).notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(this,
                        getResources().getString(R.string.gallery_max_zoom_text_hint),
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void onMaxAngleBtnClick(View v)
    {
        EditText editText = (EditText) findViewById(R.id.max_rotate_angle_edittext);
        String text = editText.getText().toString();
        
        try
        {
            int maxRotationAngle = Integer.parseInt(text);
            if (maxRotationAngle >= -60 && maxRotationAngle <= 60)
            {
                mGallery.setMaxRotationAngle(maxRotationAngle);
                ((GalleryAdapter)mGallery.getAdapter()).notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(this,
                        getResources().getString(R.string.gallery_max_rotate_angle_text_hint),
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		DesktopActivity.releaseLoading(hasFocus);
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagegallary_activity_main);        

        InitGalleyList();
        InitGalley();
    	InitTopbarAndBack();
    	
    	
    }
	
	private void InitGalleyList()
	{
		mPullDownView = (MyPullDownLayoutView) findViewById(R.id.pull_down_view);
		mPullDownView.setOnPullDownListener(this);
		mListView = mPullDownView.getListView();
		mListView.setOnItemClickListener(this);
		mListView.setDivider(this.getResources().getDrawable(R.drawable.comm_select_list_line));
		mAdapter = new GalleyListAdapter(this);
        new GetGalleyListTask().execute();
	}
	
	private void InitGalley()
	{
		mGallery = (GalleryFlow) findViewById(R.id.gallery_flow);
        //mGallery.setBackgroundColor(Color.GRAY);
        mGallery.setGravity(Gravity.CENTER_VERTICAL);
        mGallery.setSpacing(-30);
        findViewById(R.id.space_confirm_btn).setOnClickListener(mListener);
        findViewById(R.id.max_zoom_confirm_btn).setOnClickListener(mListener);
        findViewById(R.id.max_rotate_angle_confirm_btn).setOnClickListener(mListener);
        
		new RefreshGa().execute();
		
	}
	
	private void InitTopbarAndBack()
	{
		( (Button) this.findViewById(R.id.btnBack) ).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	//执行异步的操作
	private class RefreshGa extends AsyncTask<Void, Void, String[]> {

		
        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
        	generateBitmaps();
            return null;
        }

		@Override
        protected void onPostExecute(String[] result) {
			
            super.onPostExecute(result);
            mGallery.setAdapter(new GalleryAdapter());
            mGallery.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
            		Intent intent = new Intent();
        			intent.setClass(act, ImageGallaryViewFlipperActivity.class);
        			
        			intent.putExtra("id", ids[position] + "");
        			startActivity(intent);// 以传递参数的方式跳转到下一个Activity
	            }
	            
	        });
            mGallery.setCallbackDuringFling(false);
	        mGallery.setSelection(4);
        }

    }
	
	private void generateBitmaps()
    {
        
        
        for (int id : ids)
        {
            Bitmap bitmap = createReflectedBitmapById(id);
            if (null != bitmap)
            {
                BitmapDrawable drawable = new BitmapDrawable(bitmap);
                drawable.setAntiAlias(true);
                mBitmaps.add(drawable);
            }
        }
    }
    
    private Bitmap createReflectedBitmapById(int resId)
    {
        Drawable drawable = getResources().getDrawable(resId);
        if (drawable instanceof BitmapDrawable)
        {
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            Bitmap reflectedBitmap = com.broadsoft.common.util.BitmapUtil.createReflectedBitmap(bitmap);
            
            return reflectedBitmap;
        }
        
        return null;
    }
	
	
    //执行异步的操作
	private class GetGalleyListTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
        	mData = getGalleyList();
            return null;
        }

		@Override
        protected void onPostExecute(String[] result) {

            // Call onRefreshComplete when the list has been refreshed.
			mListView.setAdapter(mAdapter);
	        mPullDownView.enableAutoFetchMore(true, 1);
	        mAdapter.notifyDataSetChanged();
	        
	        
			Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
			msg.sendToTarget();
			
            super.onPostExecute(result);
        }

    }
	
	private List<Map<String, Object>> getGalleyList() {
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	for (int i = 0; i < 10; i++)
    	{
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("name", "企业文化图册(" + i + ")" );
        	list.add(map);
    	}
        return list;
    }
	
	public final class ViewHolder{
        public ImageView ivPhoto;
        public TextView tvName;
        public ImageView ivStar;
        public TextView tvCost;
        public TextView tvAbout;
        
        public Button btnPop;
    }
     
     
    public class GalleyListAdapter extends BaseAdapter{
 
        private LayoutInflater mInflater;
        private AsyncBitmapLoader asyncLoader = null;  
        
         
        public GalleyListAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
            this.asyncLoader = new AsyncBitmapLoader();  
        }
        @Override
        public int getCount() {
            return mData.size();
        }
 
        @Override
        public Object getItem(int arg0) {
            return null;
        }
 
        @Override
        public long getItemId(int arg0) {
            return 0;
        }
 
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
             
            ViewHolder holder = null;
            if (convertView == null) {
                 
                holder=new ViewHolder(); 
                 
                convertView = mInflater.inflate(R.layout.find_poi_list_item, null);
                
                holder.tvName = (TextView)convertView.findViewById(R.id.tvName);  
                /*
                holder.ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
                    
                holder.ivStar = (ImageView)convertView.findViewById(R.id.ivStar);
                holder.tvCost = (TextView)convertView.findViewById(R.id.tvCost);
                holder.tvAbout = (TextView)convertView.findViewById(R.id.tvAbout);
                */
                holder.btnPop = (Button) convertView.findViewById(R.id.btnPop);
                holder.btnPop.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						/*
						int p = position;
						LinearLayout ly = (LinearLayout) mListView.getChildAt(p);
						LinearLayout l = (LinearLayout) ly.getChildAt(0);
						Button btn = (Button) l.getChildAt(2);
						int[] location = new int[2];
						btn.getLocationInWindow (location);
						
						LayoutInflater infl = (LayoutInflater)act.getSystemService(LAYOUT_INFLATER_SERVICE);
						View ly2 = infl.inflate(R.layout.popbar, null);
						menuWindow = new PopupWindow(ly2,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); //后两个参数是width和height
						menuWindow.showAtLocation(act.findViewById(R.id.find_poi_list), Gravity.TOP|Gravity.RIGHT, -20, location[1] + 30); //设置layout在PopupWindow中显示的位置
						menu_display = true;
						*/
						
					}
				});
                convertView.setTag(holder);
                 
            }else {
                 
                holder = (ViewHolder)convertView.getTag();
            }
            
            /*
            holder.ivPhoto.setImageBitmap(null);
            Bitmap bitmap = asyncLoader.loadBitmap(holder.ivPhoto,   
            		(String) mData.get(position).get("img_url"),  
                    new ImageCallBack()  
                    {  
                        @Override  
                        public void imageLoad(ImageView imageView, Bitmap bitmap)  
                        {  
                            // TODO Auto-generated method stub  
                            imageView.setImageBitmap(bitmap);  
                        }  
                    });  
              
            if(bitmap == null)  
            {  
            	holder.ivPhoto.setImageResource(R.drawable.umeng_xp_loading);  
            }  
            else  
            {  
            	holder.ivPhoto.setImageBitmap(bitmap);  
            }  
            holder.ivStar.setImageResource(0);
            holder.tvCost.setText((String)mData.get(position).get("cost"));
            holder.tvAbout.setText((String)mData.get(position).get("cate"));
            */
            holder.tvName.setText((String)mData.get(position).get("name"));
            return convertView;
        }
         
    }
    
    
	
	@Override
	public void onRefresh() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
				Message msg = mUIHandler.obtainMessage(WHAT_DID_REFRESH);
				msg.obj = "After refresh " + System.currentTimeMillis();
				msg.sendToTarget();
			}
		}).start();
	}
	
	@Override
	public void onMore() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}				
				Message msg = mUIHandler.obtainMessage(WHAT_DID_MORE);
				msg.obj = "After more " + System.currentTimeMillis();
				msg.sendToTarget();
			}
		}).start();
	}
	
	private Handler mUIHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case WHAT_DID_LOAD_DATA:{
					if(msg.obj != null){
						@SuppressWarnings("unchecked")
						List<String> strings = (List<String>) msg.obj;
						if(!strings.isEmpty()){
							mStrings.addAll(strings);
							mAdapter.notifyDataSetChanged();
						}
					}
					// 诉它数据加载完毕;
					mPullDownView.notifyDidLoad();
					break;
				}
				case WHAT_DID_REFRESH :{
					String body = (String) msg.obj;
					mStrings.add(0, body);
					mAdapter.notifyDataSetChanged();
					// 告诉它更新完毕
					mPullDownView.notifyDidRefresh();
					break;
				}
				
				case WHAT_DID_MORE:{
					String body = (String) msg.obj;
					mStrings.add(body);
					mAdapter.notifyDataSetChanged();
					// 告诉它获取更多完毕
					mPullDownView.notifyDidMore();
					break;
				}
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		

	}
	
	    
	private class GalleryAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return mBitmaps.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (null == convertView)
            {
                convertView = new MyImageView(act);
                convertView.setLayoutParams(new Gallery.LayoutParams(500, 500));
            }
            
            ImageView imageView = (ImageView)convertView;
            imageView.setImageDrawable(mBitmaps.get(position));
            imageView.setScaleType(ScaleType.CENTER_INSIDE);
            //imageView.setBackgroundColor(Color.BLACK);
            
            return imageView;
        }
    }
    
    private class MyImageView extends ImageView
    {
        public MyImageView(Context context)
        {
            this(context, null);
        }
        
        public MyImageView(Context context, AttributeSet attrs)
        {
            super(context, attrs, 0);
        }
        
        public MyImageView(Context context, AttributeSet attrs, int defStyle)
        {
            super(context, attrs, defStyle);
        }
        
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
        }
    }
	
	
}
