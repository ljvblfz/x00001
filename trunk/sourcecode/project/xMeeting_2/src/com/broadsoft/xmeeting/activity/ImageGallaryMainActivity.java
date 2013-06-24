package com.broadsoft.xmeeting.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.broadsoft.appsupport.AsyncBitmapLoader;
import com.broadsoft.common.MyPullDownLayoutView;
import com.broadsoft.common.MyPullDownLayoutView.OnPullDownListener;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.R;

public class ImageGallaryMainActivity extends Activity implements OnPullDownListener, OnItemClickListener{

	private static final int WHAT_DID_LOAD_DATA = 0;
	private static final int WHAT_DID_REFRESH = 1;
	private static final int WHAT_DID_MORE = 2;
	
	private int grayColor = Color.parseColor("#666666");
	private int writeColor = Color.parseColor("#ffffff");

	private ListView mListView;
	private ItemListAdapter mItemListAdapter;
	private List<Map<String, Object>> mServerData;
	
	private Activity act = this;
	private Button btnBack;
	private Button btnScan;
	private Button btnSearch;


	private MyPullDownLayoutView mPullDownView;
	private List<String> mStrings = new ArrayList<String>();
	private Button btnWorker;
	private Button btnEnforcer;
	private Button btnPort;
	private int status = 1;
	
	/////////////
//	private ViewPager mTabPager;	
//	private ImageView mTabImg;// 动画图片
//	private ImageView mTab1,mTab2,mTab3,mTab4;
//	private int zero = 0;// 动画图片偏移量
//	private int currIndex = 0;// 当前页卡编号
//	private int one;//单个水平动画位移
//	private int two;
//	private int three;
//	private LinearLayout mClose;
//    private LinearLayout mCloseBtn;
//    private View layout;	
//	private LayoutInflater inflater;
	private BroadcastReceiver receiver;
	
	
	private boolean menu_display = false;
	private PopupWindow menuWindow;
	
	
	private GalleryFlow galleryFlow;
	
	private int currentSelectedPosition = 0;
	
	private ImageAdapter imageAdapter;
	

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		DesktopActivity.releaseLoading(hasFocus);
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
	}
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imagegallary_activity_main);        

		mPullDownView = (MyPullDownLayoutView) findViewById(R.id.pull_down_view);
		mPullDownView.setOnPullDownListener(this);
		mListView = mPullDownView.getListView();
		mListView.setOnItemClickListener(this);
		mListView.setDivider(this.getResources().getDrawable(R.drawable.comm_select_list_line));
		mItemListAdapter = new ItemListAdapter(this);
        new GetDataTask().execute();
        
        //关闭popupwindow用的 
        receiver = new BroadcastReceiver() {
  			public void onReceive(Context context, Intent intent) { 
  				if (menu_display == true){
  					menuWindow.dismiss();
  					menu_display = false;
  				} 
  			}//end of onReceive
  		};
        
        
//       new RefreshImageTask().execute();

       InitTopbarAndBack();
    }
	
	private void InitTopbarAndBack() {
		( (Button) this.findViewById(R.id.btnBack) ).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish(); 
			}
		});
	}

	//执行异步的操作
	private class RefreshImageTask extends AsyncTask<Void, Void, String[]> {

//		Bitmap[] images;
        @Override
        protected String[] doInBackground(Void... params) {  
        	int position=0;
        	
        	
        	String extStorageDirectory=SDCardSupport.getSDCardDirectory();
        	
        	
        	
//        	String imageDirectoryInSD = extStorageDirectory+"/upload/xmeeting/10001/xmeeting_meetingimage/10001/";
//        	Bitmap bitmap1 = BitmapFactory.decodeFile(imageDirectoryInSD+"demo1.jpg");  
//        	Bitmap bitmap2 = BitmapFactory.decodeFile(imageDirectoryInSD+"demo2.jpg");  
//        	Bitmap bitmap3 = BitmapFactory.decodeFile(imageDirectoryInSD+"demo3.jpg");  
//        	Bitmap bitmap4 = BitmapFactory.decodeFile(imageDirectoryInSD+"demo4.jpg");  
//        	Bitmap bitmap5 = BitmapFactory.decodeFile(imageDirectoryInSD+"demo5.jpg");   
//        	
//        	BitmapWrapper[] bitmapWrappers=new BitmapWrapper[5];
//        	BitmapWrapper bitmapWrapper1=new BitmapWrapper();
//        	BitmapWrapper bitmapWrapper2=new BitmapWrapper();
//        	BitmapWrapper bitmapWrapper3=new BitmapWrapper();
//        	BitmapWrapper bitmapWrapper4=new BitmapWrapper();
//        	BitmapWrapper bitmapWrapper5=new BitmapWrapper();
//        	//
//        	bitmapWrapper1.setBitmap(bitmap1);
//        	bitmapWrapper1.setFileName("/upload/xmeeting/10001/xmeeting_meetingimage/10001/demo1.jpg");
//        	//
//
//        	bitmapWrapper2.setBitmap(bitmap2);
//        	bitmapWrapper2.setFileName("/upload/xmeeting/10001/xmeeting_meetingimage/10001/demo2.jpg");
//        	//
//
//        	bitmapWrapper3.setBitmap(bitmap3);
//        	bitmapWrapper3.setFileName("/upload/xmeeting/10001/xmeeting_meetingimage/10001/demo3.jpg");
//        	// 
//        	bitmapWrapper4.setBitmap(bitmap4);
//        	bitmapWrapper4.setFileName("/upload/xmeeting/10001/xmeeting_meetingimage/10001/demo4.jpg");
//        	// 
//        	bitmapWrapper5.setBitmap(bitmap5);
//        	bitmapWrapper5.setFileName("/upload/xmeeting/10001/xmeeting_meetingimage/10001/demo5.jpg");
//        	
//        	//
//        	bitmapWrappers[0]=bitmapWrapper1;
//        	bitmapWrappers[1]=bitmapWrapper2;
//        	bitmapWrappers[2]=bitmapWrapper3;
//        	bitmapWrappers[3]=bitmapWrapper4;
//        	bitmapWrappers[4]=bitmapWrapper5; 
        	JSONArray jsonArrayDetail=(JSONArray)mServerData.get(position).get("jsonArrayDetail");
        	BitmapWrapper[] bitmapWrappers=new BitmapWrapper[jsonArrayDetail.length()];
			for(int j=0;j<jsonArrayDetail.length();j++){
				JSONObject jsonDetail;
				try {
					jsonDetail = jsonArrayDetail.getJSONObject(j);
					String fileName=jsonDetail.getString("xmmpicImageFile");
					String fileDesc=jsonDetail.getString("xmmpicImageDesc"); 
					Bitmap bitmap = BitmapFactory.decodeFile(extStorageDirectory+fileName);  
					BitmapWrapper bitmapWrapper=new BitmapWrapper();
					bitmapWrapper.setFileName(fileName);
					bitmapWrapper.setBitmap(bitmap);
					bitmapWrappers[j]=bitmapWrapper;
				} catch (JSONException e) { 
					e.printStackTrace();
				}
			}//end of for j
            
            imageAdapter = new ImageAdapter(ImageGallaryMainActivity.this,bitmapWrappers);
            imageAdapter.createReflectedImages();
            return null;
        }//end of doInBackground

		@Override
        protected void onPostExecute(String[] result) {

			galleryFlow = (GalleryFlow) findViewById(R.id.gallery_flow);
	        galleryFlow.setAdapter(imageAdapter);
	        
	        galleryFlow.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
	            	if (position == currentSelectedPosition) {
	            		Intent intent = new Intent();
	        			intent.setClass(ImageGallaryMainActivity.this, ImageGallaryViewPopupActivity.class);  
	        			BitmapWrapper bitmapWrapper=(BitmapWrapper)imageAdapter.getItem(position); 
	        			intent.putExtra("fileName", bitmapWrapper.getFileName());
	        			startActivity(intent);//  
	            	} else{
	            		currentSelectedPosition = position; 
	            	} 
	            }//end of onItemClick 
	        });
	        galleryFlow.setCallbackDuringFling(false);
	        
	        galleryFlow.setOnItemSelectedListener(new OnItemSelectedListener() {    // 设置选择事件监听     
	            @Override
	            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {     
	            	currentSelectedPosition = position;
	            }     
	              
	            @Override
	            public void onNothingSelected(AdapterView<?> parent) {     
	            }     
	        });     
	                 
            super.onPostExecute(result);
        }//end of onPostExecute

    }//end of RefreshImageTask
	
	
    //执行异步的操作
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
        	mServerData = getDataFromServer();
            return null;
        }

		@Override
        protected void onPostExecute(String[] result) {

            // Call onRefreshComplete when the list has been refreshed.
			mListView.setAdapter(mItemListAdapter);
	        mPullDownView.enableAutoFetchMore(true, 1);
	        mItemListAdapter.notifyDataSetChanged();
	        
	        
			Message msg = mUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
			msg.sendToTarget();
			
            super.onPostExecute(result);
        }

    }//end of GetDataTask
	
	private static JSONObject createJSONObject(String strJson) {
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(strJson);
		} catch (JSONException e) {
//			Log.e(TAG, "[createJSONObject]Error parsing data " + e.toString());
		}
		return jObj;
	}

	
	private List<Map<String, Object>> getDataFromServer() {

    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	//
		DownloadInfoEntity downloadInfoEntity=EntityInfoHolder.getInstance().getDownloadInfoEntity();
		String jsonData=downloadInfoEntity.getJsonData();
		JSONObject jsonObject=createJSONObject(jsonData);
		try {
			JSONObject jsonMeetingInfo=jsonObject.getJSONObject("meetingInfo");
			JSONArray jsonArray=jsonMeetingInfo.getJSONArray("listOfXmMeetingPicture");
			for(int i=0;i<jsonArray.length();i++){
				JSONObject json=jsonArray.getJSONObject(i);
				String title=json.getString("xmmpicImageTitle");
				String desc=json.getString("xmmpicImageDescription");
				JSONArray jsonArrayDetail=json.getJSONArray("listOfXmMeetingPictureDetail"); 
				
				// 
				Map<String, Object> map_1 = new HashMap<String, Object>();
				map_1.put("name", title );
				map_1.put("description", desc);
				map_1.put("jsonArrayDetail", jsonArrayDetail);
		    	list.add(map_1);
			} //end of for
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		// 
        return list;
    }//end of getDataFromServer
	
	public final class ViewHolder{
        public int position;
        public TextView tvName;
        public TextView tvDescription;
        
//
//        public String guid;
//        public ImageView ivPhoto;
//        public ImageView ivStar;
//        public TextView tvCost;
//        public TextView tvAbout;
//        
//        public Button btnPop;
    }
     
     
    public class ItemListAdapter extends BaseAdapter{
 
        private LayoutInflater mInflater;
        private AsyncBitmapLoader asyncLoader = null;  
        
         
        public ItemListAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
            this.asyncLoader = new AsyncBitmapLoader();  
        }
        @Override
        public int getCount() {
            return mServerData.size();
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
                holder.tvDescription = (TextView)convertView.findViewById(R.id.tvDescription); 
                convertView.setTag(holder);
                 
            }else {
                 
                holder = (ViewHolder)convertView.getTag();
            }
            
             
            holder.tvName.setText((String)mServerData.get(position).get("name")); 
            holder.tvDescription.setText((String)mServerData.get(position).get("description")); 
            holder.position=position;
            
            
            //
            //点击图片主题
            convertView.setOnClickListener(new View.OnClickListener() { 
				@Override
				public void onClick(View v) { 
					ViewHolder holder =(ViewHolder)v.getTag(); 
					TextView textViewOnComments=(TextView)ImageGallaryMainActivity.this.findViewById(R.id.textViewOnComments);
					textViewOnComments.setText(holder.tvName.getText()); 
//					String[] args={String.valueOf(holder.position)};
					new RefreshImageTask().execute();
				} //end of onClick
			});
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
							mItemListAdapter.notifyDataSetChanged();
						}
					}
					// 诉它数据加载完毕;
					mPullDownView.notifyDidLoad();
					break;
				}
				case WHAT_DID_REFRESH :{
					String body = (String) msg.obj;
					mStrings.add(0, body);
					mItemListAdapter.notifyDataSetChanged();
					// 告诉它更新完毕
					mPullDownView.notifyDidRefresh();
					break;
				} 
				case WHAT_DID_MORE:{
					String body = (String) msg.obj;
					mStrings.add(body);
					mItemListAdapter.notifyDataSetChanged();
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
	
	
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		View sel = act.getCurrentFocus();
//		View view = galleryFlow.getSelectedView();
//		if (sel == view) {
//			
//			Intent intent = new Intent();
//			intent.setClass(act, CallOutActivity.class);
//			startActivity(intent);// 以传递参数的方式跳转到下一个Activity 
//		}
//		return super.dispatchTouchEvent(ev);
//	}
	
	
	public class BitmapWrapper {
		private String  fileName;
		private Bitmap  bitmap;
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public Bitmap getBitmap() {
			return bitmap;
		}
		public void setBitmap(Bitmap bitmap) {
			this.bitmap = bitmap;
		}
		
		
	}
	
	public class ImageAdapter extends BaseAdapter
	{

	     int mGalleryItemBackground;
	     private Context    mContext;
//	     private Bitmap[]  mBitmapImages;
	     private BitmapWrapper[]  mBitmapWrappers;
	     private ImageView[] mImageViews;

	     //Bitmap[] bitmapImages,
	     public ImageAdapter(Context ctx, BitmapWrapper[]  bitmapWrappers) 
	     {
	         mContext  = ctx;
//	         mBitmapImages = bitmapImages;
	         mImageViews   = new ImageView[bitmapWrappers.length]; 
	         //
	         mBitmapWrappers = bitmapWrappers;
	     }

	     public boolean createReflectedImages() 
	     {
	         final int reflectionGap = 4;
	         int index = 0;

	         for (BitmapWrapper bitmapWrapper : mBitmapWrappers)
	         {
	        	 
	        	 Bitmap bitmap=bitmapWrapper.getBitmap();
	        	 
	             Bitmap originalImage = bitmap;
	             //BitmapFactory.decodeResource(mContext.getResources(), imageId);
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
	             mImageViews[index++] = imageView; 
	         } 
	         return true;
	     }

	     private Resources getResources() 
	     { 
	         return null;
	     }

	     public int getCount() 
	     {
	         return mBitmapWrappers.length;
	     }

	     public Object getItem(int position)
	     { 
	         return mBitmapWrappers[position];
	     }

	     public long getItemId(int position)
	     {
	         return position;
	     }

	     public View getView(int position, View convertView, ViewGroup parent)
	     {
	         return mImageViews[position];
	     }

	     public float getScale(boolean focused, int offset) 
	     {
	         return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	     }
	}

	
	
}
