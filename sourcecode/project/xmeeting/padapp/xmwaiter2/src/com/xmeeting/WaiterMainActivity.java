package com.xmeeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.founder.common.data.AsyncBitmapLoader;
import com.founder.enforcer.R;
public class WaiterMainActivity extends Activity {
    /** Called when the activity is first created. */
	private ViewPager viewPager;//页卡内容   
    private ImageView imageView;// 动画图片   
    private TextView textView1,textView2;   
    private List<View> views;// Tab页面列表   
    private int offset = 0;// 动画图片偏移量   
    private int currIndex = 0;// 当前页卡编号   
    private View view1,view2;//各个页卡   
    private Activity act = this;
    
    private ChatAdapter mAdapter;
    private List<Map<String, Object>> chatData;
	private ListView chatListView;
	
	private String meetingId="testmeeting";
	

    private TodoListAdapter adapter = null;
    private ListView toDoListView = null; 
    private List<String> toDoData = new ArrayList<String>(); 
    private List<String> toDoTagData = new ArrayList<String>(); 

    
    
    private Button btnBack;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_info);

        InitTopbarAndBack();
        
        InitTab();
        
        InitChatListView();
        InitTodoListView();
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
        int screenW = dm.widthPixels;// 获取分辨率宽度   
        imageView.setMinimumWidth(screenW / 2);
        offset = screenW / 2;// 计算偏移量   

    } 
    
    /**   
     *       
     * 头标点击监听 3 */   
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
    	   
        int one = offset;// 页卡1 -> 页卡2 偏移量   
        public void onPageScrollStateChanged(int arg0) {   
                   
        }   
   
        public void onPageScrolled(int arg0, float arg1, int arg2) {   
               
        }   
   
        public void onPageSelected(int arg0) {   
            
            Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//显然这个比较简洁，只有一行代码。   
            currIndex = arg0;   
            animation.setFillAfter(true);// True:图片停在动画结束位置   
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
		
		simpleAdapter = new SimpleAdapter(this, getChatData(), R.layout.main_chat_item, 
				new String[]{"position", "company", "name_title"}, new int[]{R.id.position,R.id.company,R.id.name_title});
		
		chatListView.setAdapter(simpleAdapter);
		
		chatListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				Map<String, Object> item = (Map<String, Object>) parent
						.getItemAtPosition(pos);
				Intent intent = new Intent(act, ChatActivity.class);
				intent.putExtra("position", (String)item.get("position"));
				intent.putExtra("meetingId", meetingId);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_up_in,android.R.anim.fade_out);
//				Log.d("-------======",item.get("title")+"-----------------------------------");
//				showDocument( Uri.fromFile(new File((String)item.get("path"))));

			}
		});
		
	}
	
	
	private SimpleAdapter simpleAdapter ;
	
	
	private List<Map<String, Object>> getChatData()
	{
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (int i = 0 ; i< 10; i ++)
		{
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("position", "A40"+i);
			m.put("company", "江苏电力分公司");
			m.put("name_title", "陆臻(总经理)");
			l.add(m);
		}
		return l;
	}
	
	/*对应一条chatItem显示的内容*/
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
            /*异步加载图片*/
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
		getTodoListData(); 
        adapter = new TodoListAdapter(this, toDoData, toDoTagData); 
        toDoListView = (ListView) view2.findViewById(R.id.group_list);
        toDoListView.setDivider(this.getResources().getDrawable(R.drawable.comm_select_list_line));
        toDoListView.setAdapter(adapter); 
        

        Button waterBtn = (Button)view2.findViewById(R.id.waterBtn);
        Button btnAll = (Button)view2.findViewById(R.id.btnAll);
        Button waiterBtn = (Button)view2.findViewById(R.id.waiterBtn);
        Button penBtn = (Button)view2.findViewById(R.id.penBtn);
        Button micoBtn = (Button)view2.findViewById(R.id.micoBtn);
        btnAll.setOnClickListener(btnClickListener);
        waterBtn.setOnClickListener(btnClickListener);
        waiterBtn.setOnClickListener(btnClickListener);
        penBtn.setOnClickListener(btnClickListener);
        micoBtn.setOnClickListener(btnClickListener);
        
	}
	
	
	private View.OnClickListener btnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {  
	        Button waterBtn = (Button)view2.findViewById(R.id.waterBtn);
	        Button btnAll = (Button)view2.findViewById(R.id.btnAll);
	        Button waiterBtn = (Button)view2.findViewById(R.id.waiterBtn);
	        Button penBtn = (Button)view2.findViewById(R.id.penBtn);
	        Button micoBtn = (Button)view2.findViewById(R.id.micoBtn);
			System.out.println(1+"=============="+(v.getId()==R.id.btnAll));
			System.out.println(2+"=============="+(v.getId()==R.id.waterBtn));
			System.out.println(3+"=============="+(v.getId()==R.id.penBtn));
			System.out.println(4+"=============="+(v.getId()==R.id.micoBtn));
			System.out.println(5+"=============="+(v.getId()==R.id.waiterBtn));
			if(v.getId()==R.id.btnAll){
				((Button)btnAll).setBackgroundResource(R.drawable.bus_btn_left_s);
				((Button)btnAll).setTextColor(Color.rgb(255, 255, 255));
			}else{
				((Button)btnAll).setBackgroundResource(R.drawable.bus_btn_left_n);
				((Button)btnAll).setTextColor(Color.rgb(66,66,66));
			}
			if(v.getId()==R.id.waterBtn){
				((Button)waterBtn).setBackgroundResource(R.drawable.bus_btn_middle_s);
				((Button)waterBtn).setTextColor(Color.rgb(255, 255, 255));
			}else{
				((Button)waterBtn).setBackgroundResource(R.drawable.bus_btn_middle_n_l);
				((Button)waterBtn).setTextColor(Color.rgb(66,66,66));
			}

			if(v.getId()==R.id.penBtn){
				((Button)penBtn).setBackgroundResource(R.drawable.bus_btn_middle_s);
				((Button)penBtn).setTextColor(Color.rgb(255, 255, 255));
			}else{
				((Button)penBtn).setBackgroundResource(R.drawable.bus_btn_middle_n_l);
				((Button)penBtn).setTextColor(Color.rgb(66,66,66));
			}

			if(v.getId()==R.id.micoBtn){
				((Button)micoBtn).setBackgroundResource(R.drawable.bus_btn_middle_s);
				((Button)micoBtn).setTextColor(Color.rgb(255, 255, 255));
			}else{
				((Button)micoBtn).setBackgroundResource(R.drawable.bus_btn_middle_n_l);
				((Button)micoBtn).setTextColor(Color.rgb(66,66,66));
			}

			if(v.getId()==R.id.waiterBtn){
				((Button)waiterBtn).setBackgroundResource(R.drawable.bus_btn_right_s);
				((Button)waiterBtn).setTextColor(Color.rgb(255, 255, 255));
			}else{
				((Button)waiterBtn).setBackgroundResource(R.drawable.bus_btn_right_n);
				((Button)waiterBtn).setTextColor(Color.rgb(66,66,66));
			}
		}
	};
	
	private void getTodoListData(){ 
        toDoData.add("2013-05-06"); 
        toDoTagData.add("2013-05-06"); 
        for(int i=0;i<3;i++){ 
            toDoData.add("一号桌客人"); 
        } 
        toDoData.add("2013-05-04"); 
        toDoTagData.add("2013-05-04"); 
        for(int i=0;i<3;i++){ 
            toDoData.add("二号桌客人"); 
        } 
        toDoData.add("2013-05-01"); 
        toDoTagData.add("2013-05-01"); 
        for(int i=0;i<30;i++){ 
            toDoData.add("三号桌客人"); 
        } 
    } 
    private class TodoListAdapter extends ArrayAdapter<String>{ 
          
        private List<String> listTag = null; 
        public TodoListAdapter(Context context, List<String> objects, List<String> tags) { 
            super(context, 0, objects); 
            this.listTag = tags; 
        } 
          
        @Override
        public boolean isEnabled(int position) { 
            if(listTag.contains(getItem(position))){ 
                return false; 
            } 
            return super.isEnabled(position); 
        } 
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) { 
            View view = convertView; 
            if(listTag.contains(getItem(position))){ 
                view = LayoutInflater.from(getContext()).inflate(R.layout.todolist_tag, null); 
            }else{                     
                view = LayoutInflater.from(getContext()).inflate(R.layout.todolist_item, null);
                view.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(act, WaiterMainActivity.class);
						intent.putExtra("scanResult", toDoData.get(position));
						//startActivity(intent);
						//overridePendingTransition(R.anim.slide_up_in,android.R.anim.fade_out);
					}
				});
            } 
            TextView textView = (TextView) view.findViewById(R.id.group_list_item_text); 
            textView.setText(getItem(position)); 
            return view; 
        } 
    } 
    
	
	
	
    
}