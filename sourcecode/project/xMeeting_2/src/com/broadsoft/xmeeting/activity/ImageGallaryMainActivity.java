package com.broadsoft.xmeeting.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.broadsoft.appsupport.AsyncBitmapLoader;
import com.broadsoft.common.MyPullDownLayoutView;
import com.broadsoft.common.MyPullDownLayoutView.OnPullDownListener;
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
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
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
	
	private int grayColor = Color.parseColor("#666666");
	private int writeColor = Color.parseColor("#ffffff");

	private Activity act = this;
	private ListView mListView;
	private Button btnBack;
	private Button btnScan;
	private Button btnSearch;

	private MyAdapter mAdapter;
	private List<Map<String, Object>> mData;

	private MyPullDownLayoutView mPullDownView;
	private List<String> mStrings = new ArrayList<String>();
	private Button btnWorker;
	private Button btnEnforcer;
	private Button btnPort;
	private int status = 1;
	
	/////////////
	private ViewPager mTabPager;	
	private ImageView mTabImg;// 动画图片
	private ImageView mTab1,mTab2,mTab3,mTab4;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;//单个水平动画位移
	private int two;
	private int three;
	private LinearLayout mClose;
    private LinearLayout mCloseBtn;
    private View layout;	
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	private LayoutInflater inflater;
	private BroadcastReceiver receiver;
	private GalleryFlow galleryFlow;
	
	private int currentSel = 0;
	
	private ImageAdapter adapter;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagegallary_activity_main);        

		mPullDownView = (MyPullDownLayoutView) findViewById(R.id.pull_down_view);
		mPullDownView.setOnPullDownListener(this);
		mListView = mPullDownView.getListView();
		mListView.setOnItemClickListener(this);
		mListView.setDivider(this.getResources().getDrawable(R.drawable.comm_select_list_line));
		mAdapter = new MyAdapter(this);
        new GetDataTask().execute();
        
        //关闭popupwindow用的
        receiver = new BroadcastReceiver() {
  			public void onReceive(Context context, Intent intent) {
  				//Toast.makeText(act, uploadService.getUploadList().size() + "", Toast.LENGTH_LONG).show();
  				if (menu_display == true)
  				{
  					menuWindow.dismiss();
  					menu_display = false;
  				}
  				
  			}
  		};
        
        
       new RefreshGa().execute();

    	InitTopbarAndBack();
    }
	
	 private void InitTopbarAndBack()
	{
		( (Button) this.findViewById(R.id.btnBack) ).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				//overridePendingTransition(R.anim.zoom_enter,android.R.anim.fade_out);
			}
		});
	}

	//执行异步的操作
	private class RefreshGa extends AsyncTask<Void, Void, String[]> {

		Integer[] images;
        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
        	images = new Integer[9];
        	images[0] = R.drawable.wel; 
        	images[1] = R.drawable.demo2;
        	images[2] = R.drawable.demo3;
        	images[3] = R.drawable.demo4;
        	images[4] = R.drawable.demo1;
        	images[5] = R.drawable.demo2;
        	images[6] = R.drawable.demo3;
        	images[7] = R.drawable.demo4;
        	images[8] = R.drawable.demo1;

            
            adapter = new ImageAdapter(act,images);
            adapter.createReflectedImages();
            return null;
        }

		@Override
        protected void onPostExecute(String[] result) {

			galleryFlow = (GalleryFlow) findViewById(R.id.gallery_flow);
	        galleryFlow.setAdapter(adapter);
	        
	        galleryFlow.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	            	if (position == currentSel)
	            	{
	            		Intent intent = new Intent();
	        			intent.setClass(act, ImageGallaryViewFlipperActivity.class);
	        			
	        			intent.putExtra("id", images[position] + "");
	        			startActivity(intent);// 以传递参数的方式跳转到下一个Activity
	            	}
	            	else
	            		currentSel = position;
	            	//Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
	            }
	            
	        });
	        galleryFlow.setCallbackDuringFling(false);
	        
	        galleryFlow.setOnItemSelectedListener(new OnItemSelectedListener() {    // 设置选择事件监听     
	            @Override
	            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {     
	            	currentSel = position;
	            }     
	              
	            @Override
	            public void onNothingSelected(AdapterView<?> parent) {     
	            }     
	        });     
	                

			
            super.onPostExecute(result);
        }

    }
	
	
    //执行异步的操作
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
        	mData = getDataFromServer();
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
	
	private List<Map<String, Object>> getDataFromServer() {
    	
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
     
     
    public class MyAdapter extends BaseAdapter{
 
        private LayoutInflater mInflater;
        private AsyncBitmapLoader asyncLoader = null;  
        
         
        public MyAdapter(Context context){
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
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		View sel = act.getCurrentFocus();
		View view = galleryFlow.getSelectedView();
		if (sel == view) {
			
			Intent intent = new Intent();
			intent.setClass(act, CallOutActivity.class);
			startActivity(intent);// 以传递参数的方式跳转到下一个Activity
			
			
			
		}
		return super.dispatchTouchEvent(ev);
	}
	
	public class ImageAdapter extends BaseAdapter
	{

	     int mGalleryItemBackground;
	     private Context    mContext;
	     private Integer[]  mImageIds;
	     private ImageView[] mImages;

	     public ImageAdapter(Context c, Integer[] ImageIds) 
	     {
	         mContext  = c;
	         mImageIds = ImageIds;
	         mImages   = new ImageView[mImageIds.length];
	     }

	     public boolean createReflectedImages() 
	     {
	         final int reflectionGap = 4;
	         int index = 0;

	         for (int imageId : mImageIds)
	         {
	             Bitmap originalImage = BitmapFactory.decodeResource(mContext.getResources(), imageId);
	             int width  = originalImage.getWidth();
	             int height = originalImage.getHeight();

	             Matrix matrix = new Matrix();
	             matrix.preScale(1, -1);

	             Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 3, width, height / 3, matrix, false);

	             Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 3), Config.ARGB_8888);

	             Canvas canvas = new Canvas(bitmapWithReflection);

	             canvas.drawBitmap(originalImage, 0, 0, null);

	             Paint deafaultPaint = new Paint();
	             canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);

	             canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

	             Paint paint = new Paint();
	             LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
	                                                        + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);

	             paint.setShader(shader);

	             paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

	             canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

	             final ImageView imageView = new ImageView(mContext);
	             imageView.setImageBitmap(bitmapWithReflection);
	             imageView.setLayoutParams(new GalleryFlow.LayoutParams(250, 340));
	             imageView.setScaleType(ScaleType.FIT_XY);
	             mImages[index++] = imageView;
	             
	             
	             /*
	             imageView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (imageView == galleryFlow.getSelectedView())
						{
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(act, CallOutActivity.class);
							startActivity(intent);// 以传递参数的方式跳转到下一个Activity
						}
					}
				});
				*/
	         }
	         return true;
	     }

	     private Resources getResources() 
	     {
	         // TODO Auto-generated method stub
	         return null;
	     }

	     public int getCount() 
	     {
	         return mImageIds.length;
	     }

	     public Object getItem(int position)
	     {
	         return position;
	     }

	     public long getItemId(int position)
	     {
	         return position;
	     }

	     public View getView(int position, View convertView, ViewGroup parent)
	     {
	         return mImages[position];
	     }

	     public float getScale(boolean focused, int offset) 
	     {
	         return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	     }
	}

	
	
}
