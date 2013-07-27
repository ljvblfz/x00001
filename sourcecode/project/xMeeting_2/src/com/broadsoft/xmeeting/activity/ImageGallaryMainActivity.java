package com.broadsoft.xmeeting.activity;

import java.lang.ref.SoftReference;
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
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import com.broadsoft.common.MyPullDownLayoutView;
import com.broadsoft.common.MyPullDownLayoutView.OnPullDownListener;
import com.broadsoft.common.util.BitmapSupport;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.util.BitmapUtils;

public class ImageGallaryMainActivity extends Activity implements OnPullDownListener, OnItemClickListener{

	private static final int WHAT_DID_LOAD_DATA = 0;
	private static final int WHAT_DID_REFRESH = 1;
	private static final int WHAT_DID_MORE = 2;
	

	private ListView mListView;
	private ItemListAdapter mItemListAdapter;
	private List<Map<String, Object>> mServerData;
	private MyPullDownLayoutView mPullDownView;
	private List<String> mStrings = new ArrayList<String>();
	
 
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

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
       InitTopbarAndBack();
    }

	private int backCount=0;
	private void InitTopbarAndBack() {
		( (Button) this.findViewById(R.id.btnBack) ).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backCount++; 
				if(backCount==1){
					finish();	
				} 
			}
		});
	}

	//执行异步的操作
	private class RefreshImageTask extends AsyncTask<String, Void, String[]> {

//		Bitmap[] images;
        @Override
        protected String[] doInBackground(String... params) {  
        	String position=params[0]; 
        	String extStorageDirectory=SDCardSupport.getSDCardDirectory(); 
        	JSONArray jsonArrayDetail=(JSONArray)mServerData.get(Integer.parseInt(position)).get("jsonArrayDetail");
        	BitmapWrapper[] bitmapWrappers=new BitmapWrapper[jsonArrayDetail.length()];
			for(int j=0;j<jsonArrayDetail.length();j++){
				JSONObject jsonDetail;
				try {
					jsonDetail = jsonArrayDetail.getJSONObject(j);
					String fileName=jsonDetail.getString("xmmpicImageFile");
					String fileDesc=jsonDetail.getString("xmmpicImageDesc"); 
					Bitmap bitmap =null;
					try{ 
						bitmap = BitmapUtils.getBitmap(extStorageDirectory+fileName,400*400);  
//						bitmap.recycle();
					}catch (OutOfMemoryError e){
			            e.printStackTrace();
			        }
					BitmapWrapper bitmapWrapper=new BitmapWrapper();
					bitmapWrapper.setFileName(fileName);
					bitmapWrapper.setBitmap(bitmap);
					bitmapWrappers[j]=bitmapWrapper;
				} catch (JSONException e) { 
					e.printStackTrace();
				}
			}//end of for j
            
            imageAdapter = new ImageAdapter(ImageGallaryMainActivity.this,bitmapWrappers);
            imageAdapter.createImageGallery();
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
				
				String previewPicFilePath="";
				if(null!=jsonArrayDetail&&jsonArrayDetail.length()>0){
					previewPicFilePath=jsonArrayDetail.getJSONObject(0).getString("xmmpicImageFile");
				}
				// 
				Map<String, Object> map_1 = new HashMap<String, Object>();
				map_1.put("name", title );
				map_1.put("description", desc);
				map_1.put("previewPicFilePath", previewPicFilePath);
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
        public ImageView ivPhoto;
         
    }
     
     
    public class ItemListAdapter extends BaseAdapter{
 
        private LayoutInflater mInflater;
//        private AsyncBitmapLoader asyncLoader = null;  
        
         
        public ItemListAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
//            this.asyncLoader = new AsyncBitmapLoader();  
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
                 
//                convertView = mInflater.inflate(R.layout.find_poi_list_item, null);
                convertView = mInflater.inflate(R.layout.imagegallary_list_item, null);
                
                holder.tvName = (TextView)convertView.findViewById(R.id.tvName); 
                holder.ivPhoto=(ImageView)convertView.findViewById(R.id.ivPhoto); 
                
//                holder.tvDescription = (TextView)convertView.findViewById(R.id.tvDescription); 
                convertView.setTag(holder);
                 
            }else {
                 
                holder = (ViewHolder)convertView.getTag();
            }
            
             
            holder.tvName.setText((String)mServerData.get(position).get("name")); 
            String previewPicFilePath=(String)mServerData.get(position).get("previewPicFilePath");
            Bitmap btmp;
//            BitmapFactory.Options bfOpt;  
//            bfOpt =  new BitmapFactory.Options(); 
//            bfOpt.inSampleSize = 4;
            if(null!=previewPicFilePath&&!"".equals(previewPicFilePath)){
	            String sdBaseDir=SDCardSupport.getSDCardDirectory();
//	            Uri imgUri=Uri.parse("file://"+sdBaseDir+previewPicFilePath);
//	            btmp = BitmapFactory.decodeFile(sdBaseDir+previewPicFilePath,bfOpt); 
	            btmp = BitmapUtils.getBitmap(sdBaseDir+previewPicFilePath,128*128);
	            holder.ivPhoto.setImageBitmap(new SoftReference<Bitmap>(btmp).get());//setImageURI(imgUri);
//	            btmp.recycle();
            }
//          holder.tvDescription.setText((String)mServerData.get(position).get("description")); 
            holder.position=position;
            
            
            //
            //点击图片主题
            convertView.setOnClickListener(new View.OnClickListener() { 
				@Override
				public void onClick(View v) { 
					ViewHolder holder =(ViewHolder)v.getTag(); 
					TextView textViewOnComments=(TextView)ImageGallaryMainActivity.this.findViewById(R.id.textViewOnComments);
					textViewOnComments.setText(holder.tvName.getText()); 
					String[] params={String.valueOf(holder.position)};
					new RefreshImageTask().execute(params);
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
	     private BitmapWrapper[]  mBitmapWrappers;
	     private ImageView[] mImageViews;

	     public ImageAdapter(Context ctx, BitmapWrapper[]  bitmapWrappers) 
	     {
	         mContext  = ctx;
	         mImageViews   = new ImageView[bitmapWrappers.length]; 
	         mBitmapWrappers = bitmapWrappers;
	     }

	     public void createImageGallery() 
	     {
	         int index = 0;

	         for (BitmapWrapper bitmapWrapper : mBitmapWrappers)
	         { 
	        	 Bitmap bitmap=bitmapWrapper.getBitmap();  
//		         Bitmap bitmapWithReflection = BitmapSupport.drawBitmap(bitmap); 
		         Bitmap bitmapWithReflection = BitmapSupport.createBitmap(bitmap); 
		         //create imageview
	             final ImageView imageView = new ImageView(mContext);
	             imageView.setImageBitmap(bitmapWithReflection);
	             imageView.setLayoutParams(new GalleryFlow.LayoutParams(250, 340));
	             imageView.setScaleType(ScaleType.FIT_XY);
	             mImageViews[index++] = imageView; 
//	             bitmapWithReflection.recycle();
	         }  
	     }//end of createReflectedImages


	     public void releaseImageGallery() {
	    	 
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
