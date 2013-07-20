package com.xmeeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.broadsoft.xmcommon.androidconfig.AppConfig;
import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
import com.broadsoft.xmcommon.appsupport.AppInitSupport;
import com.broadsoft.xmeeting.rsservice.RsServiceOnMeetingPersonnelInfoSupport;
import com.broadsoft.xmeeting.rsservice.RsServiceOnWaiterInfoSupport;
import com.broadsoft.xmeeting.uihandler.NotifyUIHandler;
import com.broadsoft.xmeeting.uihandler.OnlineStatusUIHandler;
import com.broadsoft.xmeeting.uihandler.ToDoUIHandler;
import com.broadsoft.xmeeting.wsservice.WsControllerServiceSupport;
import com.founder.common.data.AsyncBitmapLoader;
import com.founder.enforcer.R;
public class WaiterMainActivity extends Activity {
    /** Called when the activity is first created. */
	private ViewPager viewPager;//页锟斤拷锟斤拷锟斤拷   
    private ImageView imageView;// 锟斤拷锟斤拷图片   
    private TextView textView1,textView2;   
    private List<View> views;// Tab页锟斤拷锟叫憋拷   
    private int offset = 0;// 锟斤拷锟斤拷图片偏锟斤拷锟斤拷   
    private int currIndex = 0;// 锟斤拷前页锟斤拷锟斤拷锟�  
    private View view1,view2;//锟斤拷锟斤拷页锟斤拷   
    private Activity act = this;
    
    private ChatAdapter mAdapter;
    private List<Map<String, Object>> chatData;
	private ListView chatListView;
	
	private String meetingId=null;
	

    private TodoListAdapter adapter = null;
    
    public void notifyAdapter(){
    	adapter.notifyDataSetChanged();
    }

	public void setAdapter(TodoListAdapter adapter) {
		this.adapter = adapter;
	}


	private ListView toDoListView = null; 
//    private List<ToDoEntity> toDoData = new ArrayList<ToDoEntity>(); 
//    private List<String> toDoTagData = new ArrayList<String>(); 
    
    private Button btnBack;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_info);


		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        new AppInitSupport().initApp(getApplicationContext(), getAssets());

        ToDoUIHandler.initToDo(this);
        OnlineStatusUIHandler.init(this);
        InitTopbarAndBack();
        
        InitTab();
        
        InitChatListView();
        InitTodoListView();

		((TextView)findViewById(R.id.textView1)).setText("服务工作台_"+AndroidIdSupport.getAndroidID());

    }
    
	/*---------------------------InitTab------------------------------------------*/
    
    private void InitTab()
    {
    	InitImageView();   
        InitTextView();   
        InitViewPager();   
    }
    
    private void InitViewPager() 
    {   
        viewPager=(ViewPager) findViewById(R.id.viewpager);   
        views=new ArrayList<View>();   
        LayoutInflater inflater=getLayoutInflater();   
        view1=inflater.inflate(R.layout.main_chat, null);
        view2=inflater.inflate(R.layout.todolist, null);   


        views.add(view1);   
        views.add(view2);   

        viewPager.setAdapter(new MyViewPagerAdapter(views));   
        viewPager.setCurrentItem(0);   
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());   
    }   
    


    private void InitTextView() {   
        textView1 = (TextView) findViewById(R.id.text1);   
        textView2 = (TextView) findViewById(R.id.text2);   

   
        textView1.setOnClickListener(new MyOnClickListener(0));   
        textView2.setOnClickListener(new MyOnClickListener(1));   

    }   

    private void InitImageView() {   
        imageView= (ImageView) findViewById(R.id.cursor);      
        DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm);   
        int screenW = dm.widthPixels;// 锟斤拷取锟街憋拷锟绞匡拷锟�  
        imageView.setMinimumWidth(screenW / 2);
        offset = screenW / 2;// 锟斤拷锟斤拷偏锟斤拷锟斤拷   

    } 
    
    /**   
     *       
     * 头锟斤拷锟斤拷锟斤拷锟斤拷 3 */   
    private class MyOnClickListener implements OnClickListener{   
        private int index=0;   
        public MyOnClickListener(int i){   
            index=i;   
        }   
        public void onClick(View v) {   
            viewPager.setCurrentItem(index);               
        }   
           
    }  
    
    public class MyViewPagerAdapter extends PagerAdapter{   
        private List<View> mListViews;   
           
        public MyViewPagerAdapter(List<View> mListViews) {   
            this.mListViews = mListViews;   
        }   
   
        @Override   
        public void destroyItem(ViewGroup container, int position, Object object) 
        {      
            container.removeView(mListViews.get(position));   
        }   
   
   
        @Override   
        public Object instantiateItem(ViewGroup container, int position) {             
             container.addView(mListViews.get(position), 0);   
             return mListViews.get(position);   
        }   
   
        @Override   
        public int getCount() {            
            return  mListViews.size();   
        }   
           
        @Override   
        public boolean isViewFromObject(View arg0, Object arg1) {              
            return arg0==arg1;   
        }   
    }   
   
    public class MyOnPageChangeListener implements OnPageChangeListener{   
    	   
        int one = offset;// 页锟斤拷1 -> 页锟斤拷2 偏锟斤拷锟斤拷   
        public void onPageScrollStateChanged(int arg0) {   
                   
        }   
   
        public void onPageScrolled(int arg0, float arg1, int arg2) {   
               
        }   
   
        public void onPageSelected(int arg0) {   
            
            Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//锟斤拷然锟斤拷锟斤拷冉霞锟洁，只锟斤拷一锟叫达拷锟诫。   
            currIndex = arg0;   
            animation.setFillAfter(true);// True:图片停锟节讹拷锟斤拷锟斤拷锟斤拷位锟斤拷   
            animation.setDuration(300);   
            imageView.startAnimation(animation);   
           
        }   
    }   
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			Intent intent = new Intent("hideOrShowMenu");
    		sendBroadcast(intent);
			
		}
		return true;
		
		//return super.onKeyDown(keyCode, event);
	}
	
	private void InitTopbarAndBack()
	{
		btnBack = (Button) this.findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent("hideOrShowMenu");
        		sendBroadcast(intent);
			}
		});
	}
	
	/*---------------------------ChatListView------------------------------------------*/
	
	private void InitChatListView()
	{
//		chatData = getChatData();
		chatListView = (ListView) view1.findViewById(R.id.list);
		chatListView.setDivider(this.getResources().getDrawable(R.drawable.comm_select_list_line));
		mAdapter = new ChatAdapter(this);
//		chatListView.setAdapter(mAdapter);
		
		simpleAdapter = new SimpleAdapter(this, memberList, R.layout.main_chat_item, 
				new String[]{"position", "company", "name_title"}, new int[]{R.id.position,R.id.company,R.id.name_title});
		
		chatListView.setAdapter(simpleAdapter);
		
		chatListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				Map<String, Object> item = (Map<String, Object>) parent
						.getItemAtPosition(pos);
				Intent intent = new Intent(act, ChatActivity.class);
				
//				m.put("position", memberArray.getJSONObject(i).getString("xmridSeatno"));
//				m.put("company",  memberArray.getJSONObject(i).getString("xmpiDeptinfo"));
//				m.put("name_title", memberArray.getJSONObject(i).getString("xmpiName")+"("+memberArray.getJSONObject(i).getString("xmpiTitle")+")");
//				m.put("memberId", memberArray.getJSONObject(i).getString("xmpiGuid"));
				intent.putExtra("company", (String)item.get("company"));
				intent.putExtra("name_title", (String)item.get("name_title"));
				intent.putExtra("memberId", (String)item.get("memberId"));
				intent.putExtra("meetingId", meetingId);
				intent.putExtra("memberDisplayName", memberDisplayName);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_up_in,android.R.anim.fade_out);
//				Log.d("-------======",item.get("title")+"-----------------------------------");
//				showDocument( Uri.fromFile(new File((String)item.get("path"))));

			}
		});
	}
	
	
	private SimpleAdapter simpleAdapter ;
	
	
	private  List<Map<String, Object>> memberList = new ArrayList<Map<String,Object>>();
	
	
	/*锟斤拷应一锟斤拷chatItem锟斤拷示锟斤拷锟斤拷锟斤拷*/
	public final class ChatViewHolder{
        public ImageView ivPhoto;
        public TextView tvName;
        public ImageView ivStar;
        public TextView tvCost;
        public TextView tvAbout;
        
        public Button btnPop;
    }
     
     
    public class ChatAdapter extends BaseAdapter{
 
        private LayoutInflater mInflater;
        @SuppressWarnings("unused")
		private AsyncBitmapLoader asyncLoader = null;  
        
         
        public ChatAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
            this.asyncLoader = new AsyncBitmapLoader();  
        }
        @Override
        public int getCount() {
            return chatData.size();
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
             
            ChatViewHolder holder = null;
            if (convertView == null) {
                 
                holder=new ChatViewHolder(); 
                 
                convertView = mInflater.inflate(R.layout.main_chat_item, null);
                
//                convertView.findViewById(R.id.head).set
                //holder.tvName = (TextView)convertView.findViewById(R.id.tvName); 
                /*
                holder.ivPhoto = (ImageView)convertView.findViewById(R.id.ivPhoto);
                     
                holder.ivStar = (ImageView)convertView.findViewById(R.id.ivStar);
                holder.tvCost = (TextView)convertView.findViewById(R.id.tvCost);
                holder.tvAbout = (TextView)convertView.findViewById(R.id.tvAbout);
                */
                //holder.btnPop = (Button) convertView.findViewById(R.id.btnPop);
                
                convertView.setTag(holder);
                 
            }else {
                 
                holder = (ChatViewHolder)convertView.getTag();
                
            }
            convertView.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					// TODO Auto-generated method stub
    					Intent intent = new Intent(act, ChatActivity.class);
    					
    					startActivity(intent);
    					overridePendingTransition(R.anim.slide_up_in,android.R.anim.fade_out);
    				}
    		});
            
            //holder.tvName.setText((String)chatData.get(position).get("name"));
            /*锟届步锟斤拷锟斤拷图片*/
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
            return convertView;
        }
         
    }
	
	
	/*---------------------------ToDoListView------------------------------------------*/
	
	private void InitTodoListView()
	{
//		getTodoListData(); 
        adapter = new TodoListAdapter(this, ToDoUIHandler.getInstance().getToDoData()); 
        toDoListView = (ListView) view2.findViewById(R.id.group_list);
        toDoListView.setDivider(this.getResources().getDrawable(R.drawable.comm_select_list_line));
        toDoListView.setAdapter(adapter); 

//        Button waterBtn = (Button)view2.findViewById(R.id.waterBtn);
//        Button btnAll = (Button)view2.findViewById(R.id.btnAll);
//        Button waiterBtn = (Button)view2.findViewById(R.id.waiterBtn);
//        Button penBtn = (Button)view2.findViewById(R.id.penBtn);
//        Button micoBtn = (Button)view2.findViewById(R.id.micoBtn);
//        btnAll.setOnClickListener(btnClickListener);
//        waterBtn.setOnClickListener(btnClickListener);
//        waiterBtn.setOnClickListener(btnClickListener);
//        penBtn.setOnClickListener(btnClickListener);
//        micoBtn.setOnClickListener(btnClickListener);
        
	}
	
	
//	private View.OnClickListener btnClickListener = new View.OnClickListener() {
//		@Override
//		public void onClick(View v) {  
//	        Button waterBtn = (Button)view2.findViewById(R.id.waterBtn);
//	        Button btnAll = (Button)view2.findViewById(R.id.btnAll);
//	        Button waiterBtn = (Button)view2.findViewById(R.id.waiterBtn);
//	        Button penBtn = (Button)view2.findViewById(R.id.penBtn);
//	        Button micoBtn = (Button)view2.findViewById(R.id.micoBtn);
//			System.out.println(1+"=============="+(v.getId()==R.id.btnAll));
//			System.out.println(2+"=============="+(v.getId()==R.id.waterBtn));
//			System.out.println(3+"=============="+(v.getId()==R.id.penBtn));
//			System.out.println(4+"=============="+(v.getId()==R.id.micoBtn));
//			System.out.println(5+"=============="+(v.getId()==R.id.waiterBtn));
//			if(v.getId()==R.id.btnAll){
//				((Button)btnAll).setBackgroundResource(R.drawable.bus_btn_left_s);
//				((Button)btnAll).setTextColor(Color.rgb(255, 255, 255));
//			}else{
//				((Button)btnAll).setBackgroundResource(R.drawable.bus_btn_left_n);
//				((Button)btnAll).setTextColor(Color.rgb(66,66,66));
//			}
//			if(v.getId()==R.id.waterBtn){
//				((Button)waterBtn).setBackgroundResource(R.drawable.bus_btn_middle_s);
//				((Button)waterBtn).setTextColor(Color.rgb(255, 255, 255));
//			}else{
//				((Button)waterBtn).setBackgroundResource(R.drawable.bus_btn_middle_n_l);
//				((Button)waterBtn).setTextColor(Color.rgb(66,66,66));
//			}
//
//			if(v.getId()==R.id.penBtn){
//				((Button)penBtn).setBackgroundResource(R.drawable.bus_btn_middle_s);
//				((Button)penBtn).setTextColor(Color.rgb(255, 255, 255));
//			}else{
//				((Button)penBtn).setBackgroundResource(R.drawable.bus_btn_middle_n_l);
//				((Button)penBtn).setTextColor(Color.rgb(66,66,66));
//			}
//
//			if(v.getId()==R.id.micoBtn){
//				((Button)micoBtn).setBackgroundResource(R.drawable.bus_btn_middle_s);
//				((Button)micoBtn).setTextColor(Color.rgb(255, 255, 255));
//			}else{
//				((Button)micoBtn).setBackgroundResource(R.drawable.bus_btn_middle_n_l);
//				((Button)micoBtn).setTextColor(Color.rgb(66,66,66));
//			}
//
//			if(v.getId()==R.id.waiterBtn){
//				((Button)waiterBtn).setBackgroundResource(R.drawable.bus_btn_right_s);
//				((Button)waiterBtn).setTextColor(Color.rgb(255, 255, 255));
//			}else{
//				((Button)waiterBtn).setBackgroundResource(R.drawable.bus_btn_right_n);
//				((Button)waiterBtn).setTextColor(Color.rgb(66,66,66));
//			}
//		}
//	};
	
//	private void getTodoListData(){ 
////        toDoData.add("2013-05-06"); 
////        toDoTagData.add("2013-05-06"); 
//		
//		ToDoEntity e = new ToDoEntity();
//		e.setPosition("1111111111");
//		e.setChecked(true);
//		e.setType("1");
//		e.setTodoId("0000000000000000");
//		e.setTimeStr("2013-10-11");
//
//		ToDoEntity e1 = new ToDoEntity();
//		e1.setPosition("222");
//		e1.setChecked(false);
//		e1.setType("2");
//		e1.setTodoId("11111");
//		e1.setTimeStr("2013-10-12");
//		ToDoEntity e2 = new ToDoEntity();
//		e2.setPosition("333");
//		e2.setChecked(true);
//		e2.setType("3");
//		e2.setTodoId("33333");
//		e2.setTimeStr("2013-10-13");
//        for(int i=0;i<3;i++){ 
//            toDoData.add(e); 
//        } 
////        toDoData.add("2013-05-04"); 
////        toDoTagData.add("2013-05-04"); 
//        for(int i=0;i<3;i++){ 
//            toDoData.add(e1); 
//        } 
////        toDoData.add("2013-05-01"); 
////        toDoTagData.add("2013-05-01"); 
//        for(int i=0;i<30;i++){ 
//            toDoData.add(e2); 
//        } 
//    } 
    private class TodoListAdapter extends ArrayAdapter<ToDoEntity>{ 
          
        private List<String> listTag = null; 
        public TodoListAdapter(Context context, List<ToDoEntity> objects) { 
            super(context, 0, objects); 
//            this.listTag = tags; 
        } 
//        public boolean isEnabled(int position) { 
//            if(listTag.contains(getItem(position))){ 
//                return false; 
//            } 
//            return super.isEnabled(position); 
//        } 
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = convertView; 
//            if(listTag.contains(getItem(position))){ 
//                view = LayoutInflater.from(getContext()).inflate(R.layout.todolist_tag, null); 
//            }else{                     
                view = LayoutInflater.from(getContext()).inflate(R.layout.todolist_item, null);

//    			System.out.println(getItem(position).getType()+getItem(position).getTodoId()+"****11111*************************"+getItem(position).isChecked());
                
                ((TextView)view.findViewById(R.id.textView2)).setText(getItem(position).getType());
//                ((ImageView)view.findViewById(R.id.head)).setImageResource(getItem(position).getType().equals("2")?R.drawable.bookpen2:R.drawable.coffee2);
                if(getItem(position).getType().startsWith("请求茶")){
                	 ((ImageView)view.findViewById(R.id.head)).setImageResource(R.drawable.coffee2);
                }else if (getItem(position).getType().startsWith("请求纸")){
               	 ((ImageView)view.findViewById(R.id.head)).setImageResource(R.drawable.bookpen2);
                }else if (getItem(position).getType().startsWith("请求话")){
               	 ((ImageView)view.findViewById(R.id.head)).setImageResource(R.drawable.microphone);
                }else {
               	 ((ImageView)view.findViewById(R.id.head)).setImageResource(R.drawable.users);
                }
                
//                ((ImageView)view.findViewById(R.id.head)).setImageResource(getItem(position).getType().equals("2")?
//                		R.drawable.microphone:R.drawable.users);
                
                ((TextView)view.findViewById(R.id.textView3)).setText(getItem(position).getTimeStr());
                
                final ToggleButton toggleButton = (ToggleButton)view.findViewById(R.id.toggleButton1);
                if(getItem(position).isChecked()){
            		toggleButton.setChecked(true); 
                }
                toggleButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(!getItem(position).isChecked()){
	            			//TODO set task status to done
//	            			System.out.println(getItem(position).getTodoId()+"*****************************"+getItem(position).isChecked());
	                		toggleButton.setChecked(true); 
	                		ToDoUIHandler.getInstance().setCheck(true, position+1);
	                		getItem(position).setChecked(true);
						}
            		}
				}); 
                
//            } 
            TextView textView = (TextView) view.findViewById(R.id.group_list_item_text); 
            textView.setText(getItem(position).getPosition()); 
            return view; 
        } 
    } 
    

    private static JSONObject jsobj ;
    
    private static String xmmiName;
    
    private static String memberId;
    
    private static String memberDisplayName;
    
	
    private class AppInitSupport {
    	private static final String TAG="AppInitSupport"; 

    	
    	public void initApp(Context ctx,AssetManager assetManager ) {
    		//初始化配置
    		DomAppConfigFactory.init(assetManager);
    		AppConfig appConfig=DomAppConfigFactory.getAppConfig();
    		Log.d(TAG, "[Config]AppConfig---->"+appConfig);
    		//初始化设备ID
    		AndroidIdSupport.init(ctx);
    		Log.d(TAG, "[AndroidI]AndroidID---->"+AndroidIdSupport.getAndroidID()); 
    		//SD card
    		String sdcardDir = SDCardSupport.getSDCardDirectory();
    		Log.d(TAG, "[SDCard]sdcardDir---->"+sdcardDir);   
    		//WS service
    		new RequestWaiterInfoTask().execute();

    	}//end of initApp
     

        
        
    	 private class RequestWaiterInfoTask extends AsyncTask<Void, Void, String[]> { 
    			@Override
    			protected void onPreExecute() {
    				super.onPreExecute();
    			}
    			@Override
    			protected String[] doInBackground(Void... params) { 
    				//Get waiter infomation
    				try {
    					Log.d(this.getClass().getName(),RsServiceOnWaiterInfoSupport.requestWaiterInfo()+"----RsServiceOnWaiterInfoSupport.requestWaiterInfo()-----");
    					JSONObject  waiterObj =  null ;
//    						waiterObj = RsServiceOnWaiterInfoSupport.requestWaiterInfo();
	    					int i = 0;
	    					while(null==waiterObj && i<10){
	    						try{
	    							waiterObj=RsServiceOnWaiterInfoSupport.requestWaiterInfo();
		    					}catch(Exception ae){
//		    						ae.printStackTrace();
		    					}
//	    						Log.d(this.getClass().getName(), "============requestWaiterInfo times============="+i);
//	    						Log.d(this.getClass().getName(), "============waiterObj============="+waiterObj);
	    						i++;
	    					}
    					
    					if(waiterObj==null){
    						finish();
    					}
//    					if(null!=jsobj){
	    					jsobj = waiterObj.getJSONObject("jsonData");
							Log.d(this.getClass().getName(), "============jsobj============="+jsobj);
	    					meetingId = jsobj.getJSONObject("xervicePersonnelPadIVO").getJSONArray("list").getJSONObject(0).getString("xmmiGuid");
	    					xmmiName = jsobj.getJSONObject("xervicePersonnelPadIVO").getJSONArray("list").getJSONObject(0).getString("xmmiName");
	    					memberId = jsobj.getJSONObject("xervicePersonnelPadIVO").getJSONArray("list").getJSONObject(0).getString("xmpiGuid");
	    					memberDisplayName = jsobj.getJSONObject("xervicePersonnelPadIVO").getJSONArray("list").getJSONObject(0).getString("xmpiName");
//    					}
//						Log.d(this.getClass().getName(), "============meetingId============="+meetingId);
//						Log.d(this.getClass().getName(), "============xmmiName============="+xmmiName);
//						Log.d(this.getClass().getName(), "============memberId============="+memberId);
//						Log.d(this.getClass().getName(), "============memberDisplayName============="+memberDisplayName);
    				} catch (JSONException e) {
    					e.printStackTrace();
    				}
    				
    				loadMemberList(RsServiceOnMeetingPersonnelInfoSupport.requestMeetingPersonnelInfo(meetingId));
    				WsControllerServiceSupport.getInstance().initData(meetingId, memberId, memberDisplayName);
    				WsControllerServiceSupport.getInstance().connect();
    				return null;
    			}

    			
    			private void loadMemberList(JSONObject jsobj){
    				
    				try {
    					JSONArray memberArray = jsobj.getJSONObject("jsonData").getJSONArray("listOfXmMeetingPersonnelSeatPadIVO");
    					l = new ArrayList<Map<String, Object>>();
    					for (int i = 0 ; i< memberArray.length(); i ++){
    						Map<String, Object> m = new HashMap<String, Object>();
    						m.put("position", memberArray.getJSONObject(i).getString("xmridSeatno"));
    						m.put("company",  memberArray.getJSONObject(i).getString("xmpiDeptinfo"));
    						m.put("name_title", memberArray.getJSONObject(i).getString("xmpiName")+"("+memberArray.getJSONObject(i).getString("xmpiTitle")+")");
    						m.put("memberId", memberArray.getJSONObject(i).getString("xmpiGuid"));
    						memberList.add(m);
    					}
    					
    				} catch (JSONException e) {
    					e.printStackTrace();
    				}
    			}
    			
    			private List<Map<String, Object>> l ;
    			
    			@Override
    			protected void onPostExecute(String[] result) { 
    				((TextView)findViewById(R.id.textView1)).setText("服务工作台("+xmmiName+")_"+AndroidIdSupport.getAndroidID());
    				((TextView)findViewById(R.id.offlineTipText1)).setText(memberDisplayName);
    				simpleAdapter.notifyDataSetChanged();
    	  		}   
    	    }//end of RequestWaiterInfoTask
    	    

    	public void destroyApp(Context ctx,AssetManager assetManager) { 
    		WsControllerServiceSupport.getInstance().disconnect();
    	}
    }
    
}