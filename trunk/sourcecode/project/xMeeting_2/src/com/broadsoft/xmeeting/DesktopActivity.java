package com.broadsoft.xmeeting;
 

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.broadsoft.common.MyImageView;
import com.broadsoft.common.util.FileStoreTools;
import com.broadsoft.common.util.WeatherWebServiceUtil;
import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmcommon.androidnetwork.NetworkSupport;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmdownload.wsservice.WsControllerServiceSupport;
import com.broadsoft.xmeeting.activity.CallOutActivity;
import com.broadsoft.xmeeting.activity.CompanyInfoActivity;
import com.broadsoft.xmeeting.activity.DocumentsListActivity;
import com.broadsoft.xmeeting.activity.ImageGallaryMainActivity;
import com.broadsoft.xmeeting.activity.MeetingGuideCatalogActivity;
import com.broadsoft.xmeeting.activity.NotificationListActivity;
import com.broadsoft.xmeeting.activity.SysSettingActivity;
import com.broadsoft.xmeeting.activity.VideosListActivity;
import com.broadsoft.xmeeting.uihandler.NotifyUIHandler;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * http://blog.csdn.net/Class_Raito/article/details/3390737
 * 
 * http://itil-rong.iteye.com/blog/901564   activity传递参数
 * 
 * @author lu.zhen
 * 
 */
public class DesktopActivity extends Activity implements Runnable  { 
	/**
	 * 数据
	 */
	private Activity act = this;

	private final static String TAG="DesktopActivity";

	/**
	 * 天气预报的控件
	 */
	private com.broadsoft.common.MyImageView city_btn;
	private static final int CITY = 0x11;
	private String city_str;
	private TextView city_text;
	private Spinner province_spinner;
	private Spinner city_spinner;
	private List<String> provinces;
	private List<String> citys;
	private SharedPreferences preference;
	private SoapObject detail;
	private ArrayList<String> list; 
	
	/**
	 * 其他控件
	 */ 
	private long timeOfRetry=20*1000;
	private Handler handlerCheckingWifi = new Handler(); 
	
//	public Handler notifyUIHandler;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.desktop_activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//人名显示
		TextView textViewDisplayName=(TextView)this.findViewById(R.id.textViewDisplayName);
		String memberDisplayName=EntityInfoHolder.getInstance().getDownloadInfoEntity().getMemberDisplayName();
		textViewDisplayName.setText(memberDisplayName);
		
		//wifi设置
		initWifiStatus();
		handlerCheckingWifi.postDelayed(this, timeOfRetry); 
		
		//桌面按钮
		initGridButton();  
		//天气预报
		initWeather();    
		//
		NotifyUIHandler.init(this);
		//init websocket  
		initWebsocket(); 
		
		
		
		 
	}

	private void initWifiStatus() {
		Log.d(TAG, "[initWifiStatus]begin.");
		ImageView ivWifiIcon=(ImageView)findViewById(R.id.ivWifiIcon); 
		if(isConnected()){
			ivWifiIcon.setImageResource(R.drawable.wifi_on_64); 
		}else{
			ivWifiIcon.setImageResource(R.drawable.wifi_off_64);  
		}
		Log.d(TAG, "[initWifiStatus]end.");
	}

	
	private boolean flagOnHandler=true;
	protected boolean isConnected(){
		ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		return NetworkSupport.isConnected(connMgr);
	}

	@Override
	public void run() {
		if(!flagOnHandler){
			return;
		}
		initWifiStatus();
		handlerCheckingWifi.postDelayed(this,timeOfRetry );
	}
	
	
	/**
	 * Disable back key
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_BACK) { 
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_HOME) { 
			return false;
		} 
		return super.onKeyDown(keyCode, event);  
	}
	
	
	@Override
	protected void onDestroy() {
		this.flagOnHandler=false;
		try{
			WsControllerServiceSupport.getInstance().disconnect();  
		}catch(Exception e){ 
			Log.d(TAG, "[Websocket]disconnect---exception--"+e.getMessage());
		}
	}

	private void initWebsocket() {
		Log.d(TAG, "[Websocket]Init begin.");
		String meetingId=EntityInfoHolder.getInstance().getDownloadInfoEntity().getMeetingId();
		String memberId=EntityInfoHolder.getInstance().getDownloadInfoEntity().getMemberId();
		String memberDisplayName=EntityInfoHolder.getInstance().getDownloadInfoEntity().getMemberDisplayName(); 
		WsControllerServiceSupport.getInstance().initData(meetingId, memberId, memberDisplayName); 
		WsControllerServiceSupport.getInstance().connect();
		Log.d(TAG, "[Websocket]Init end.");
	}
 
//	
//	Runnable notifyRunnable=new Runnable(){ 
//		@Override
//		public void run() { 
//			while(true){
//				if(CallServiceHolder.getInstance().isUpdated()){
//					CallServiceHolder.getInstance().setUpdated(false);
//					Toast toast=Toast.makeText(DesktopActivity.this, "你有新的消息.",Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				}//end of if
//				try {
//					Thread.sleep(10*1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} 
//			}//end of while 
//		}//end of run 
//	};//end of notifyRunnable
	
	
//	public OPlayerApplication getOPA(){
//		return ((OPlayerApplication)getApplicationContext());
//	}
//	
//	private void initMeetingData() {
////		DownloadMeetingUtil.downloadMeetingInfo(getOPA());
//	}
	
//	
//	public static void main(String[] args) {
//		DesktopActivity s =new DesktopActivity();
//		s.initMeetingData();
//		
//		
//	}

	private void initGridButton()
	{
		//1
		( (com.broadsoft.common.MyImageView) this.findViewById(R.id.btnCom) ).setOnClickListener(new MyImageView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity("1");
				
			}
		});
		
		//2
		( (com.broadsoft.common.MyImageView) this.findViewById(R.id.btnGuide) ).setOnClickListener(new MyImageView.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity("2");
			}
		});
	
		//3
		( (com.broadsoft.common.MyImageView) this.findViewById(R.id.btnCall) ).setOnClickListener(new MyImageView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity("3");
			}
		});
		
		//4
		( (com.broadsoft.common.MyImageView) this.findViewById(R.id.btnDocu) ).setOnClickListener(new MyImageView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity("4");
			}
		});
		
		//5
		( (com.broadsoft.common.MyImageView) this.findViewById(R.id.btnPhoto) ).setOnClickListener(new MyImageView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity("5");
			}
		});
		
		//6
		( (com.broadsoft.common.MyImageView) this.findViewById(R.id.btnVedio) ).setOnClickListener(new MyImageView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity("6");
			}
		});
		
		//7
		( (com.broadsoft.common.MyImageView) this.findViewById(R.id.btnSetting) ).setOnClickListener(new MyImageView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity("7");
			}
		});
		//8
		( (com.broadsoft.common.MyImageView) this.findViewById(R.id.btnMessage) ).setOnClickListener(new MyImageView.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity("8");
			}
		});
		
	}

	private void startActivity(String type) {
		if (!isloading) {
			isloading = true;
			new StartActivityTask().execute(type);
		}
	}
	
	/**
	 * 释放activity loading 的状态
	 * @param hasFocus
	 */
	public static void releaseLoading(boolean hasFocus){
//		System.out.println("================================hasFocus =========="+hasFocus);
		if(hasFocus){
//			System.out.println("================================upload isloading =========="+DesktopActivity.isloading);
			DesktopActivity.isloading=false;
		}
	}
	
	public static boolean isloading=false;
	
	//执行异步的操作
  	private class StartActivityTask extends AsyncTask<String, Void, String[]> {
  		private int flag = 0;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String[] doInBackground(String... params) {
			// Simulates a background job.
			flag = Integer.parseInt(params[0]);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			return null;

		}

		@Override
		protected void onPostExecute(String[] result) {

			// Call onRefreshComplete when the list has been refreshed.
			Intent intent = new Intent();
			switch (flag) {
			case 1:
				intent.setClass(act, CompanyInfoActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				break;
			case 2:
				intent.setClass(act, MeetingGuideCatalogActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				break;
			case 3:
				intent.setClass(act, CallOutActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				break;
			case 4:
				intent.setClass(act, DocumentsListActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				break;
			case 5:
				intent.setClass(act, ImageGallaryMainActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				break;
			case 6:
				intent.setClass(act, VideosListActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				break;
			case 7:
				intent.setClass(act, SysSettingActivity.class);// 指定了跳转前的Activity和跳转后的Activity
			case 8:
				intent.setClass(act, NotificationListActivity.class);// 指定了跳转前的Activity和跳转后的Activity
			}

			startActivity(intent);// 以传递参数的方式跳转到下一个Activity

  		}
          
    }
  	
  	
	private String wheatherDirectory="/upload/xmeeting_wheatherinfo/"; 
	private String wheatherFileName="wheatherinfo.xml";
	
	
	private void initWeather()
	{
		Log.d(TAG, "initWeather begin.");
		
		preference = getSharedPreferences("weather", MODE_PRIVATE);
		city_str = readSharpPreference();

		city_text = (TextView) findViewById(R.id.city);
		city_text.setText(city_str);

		//text = (TextView) findViewById(R.id.test);

		city_btn = (com.broadsoft.common.MyImageView) findViewById(R.id.city_button);

		city_btn.setOnClickListener(new ClickEvent());

		findViewById(R.id.content_today_layout).getBackground().setAlpha(120);
		findViewById(R.id.content_small_bg1).getBackground().setAlpha(120);
		findViewById(R.id.content_small_bg2).getBackground().setAlpha(120);
		findViewById(R.id.content_small_bg3).getBackground().setAlpha(120);
		
		
		
		com.broadsoft.common.MyImageView btnReturn = (com.broadsoft.common.MyImageView)this.findViewById(R.id.btnReturn);
		btnReturn.setOnClickListener(new View.OnClickListener() { 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		String weatherFilePath = SDCardSupport.getSDCardDirectory() +wheatherDirectory+ wheatherFileName;
		String sXml = FileStoreTools.readFileSdcard(weatherFilePath);
		Log.d(TAG, "weatherFile[" + weatherFilePath + "] content: " + sXml);

		
		if (sXml != null && sXml.equals("") == false) {
			if ( sXml.equals("<list/>") == false) {
				XStream xstream = new XStream(new DomDriver()); // does not require XPP3 library
				list = (ArrayList<String>) xstream.fromXML(sXml);
				initUI(city_str); 
			}
		}

		new GetWheatherDataTask().execute();
		Log.d(TAG, "initWeather end.");
	}
	
	//执行异步的操作
	private class GetWheatherDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected void onPreExecute() {
			// 第一个执行方法
			super.onPreExecute();
		}

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			detail = WeatherWebServiceUtil.getWeatherByCity(city_str);
			Log.d(TAG, "wheather soap content: " + detail);
			if (detail != null) {
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 0; i < detail.getPropertyCount() - 1; i++) {
					list.add(detail.getProperty(i).toString());
				}
				XStream xStream = new XStream(new DomDriver());
				String strxml = xStream.toXML(list);
//				String filePath = Environment.getExternalStorageDirectory()
//						.toString() + "/Enforcer/";// 存放数据的文件夹
				

				String fileDirectory = SDCardSupport.getSDCardDirectory() +wheatherDirectory;
				

				Log.d(TAG, "weatherFile[" + fileDirectory + "] content: " + strxml);
				FileStoreTools.saveFile(strxml, fileDirectory, wheatherFileName);
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {

			// Call onRefreshComplete when the list has been refreshed.
			refresh(city_str);
			super.onPostExecute(result);
		}

	}

	class ClickEvent implements View.OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
			case R.id.city_button:

				show_dialog(CITY);

				break;

			default:
				break;
			}

		}

	}

	public void showTast(String string)
	{
		Toast.makeText(DesktopActivity.this, "刷新失败", 1).show();

	}

	public void show_dialog(int cityId)
	{

		switch (cityId)
		{
		case CITY:

			// 取得city_layout.xml中的视图
			final View view = LayoutInflater.from(this).inflate(
					R.layout.weather_city_layout, null);

			// 省份Spinner
			province_spinner = (Spinner) view
					.findViewById(R.id.province_spinner);
			// 城市Spinner
			city_spinner = (Spinner) view.findViewById(R.id.city_spinner);

			// 省份列表
			provinces = WeatherWebServiceUtil.getProvinceList();

			ArrayAdapter adapter = new ArrayAdapter(this,
					android.R.layout.simple_spinner_item, provinces);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			province_spinner.setAdapter(adapter);
			// 省份Spinner监听器
			province_spinner
					.setOnItemSelectedListener(new OnItemSelectedListener()
					{

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int position, long arg3)
						{

							citys = WeatherWebServiceUtil
									.getCityListByProvince(provinces
											.get(position));
							ArrayAdapter adapter1 = new ArrayAdapter(
									DesktopActivity.this,
									android.R.layout.simple_spinner_item, citys);
							adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							city_spinner.setAdapter(adapter1);

						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0)
						{

						}
					});

			// 城市Spinner监听器
			city_spinner.setOnItemSelectedListener(new OnItemSelectedListener()
			{

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3)
				{
					city_str = citys.get(position);
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0)
				{

				}
			});

			// 选择城市对话框
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("请选择所属城市");
			dialog.setView(view);
			dialog.setPositiveButton("确定",
					new DialogInterface.OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							city_text.setText(city_str);
							writeSharpPreference(city_str);
							refresh(city_str);

						}
					});
			dialog.setNegativeButton("取消",
					new DialogInterface.OnClickListener()
					{

						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.dismiss();

						}
					});

			dialog.show();

			break;

		default:
			break;
		}

	}

	protected void refresh(String city_str)
	{
		
		if (detail == null)
		{
			 Toast toast = Toast.makeText(this, "连接 天气预报服务器失败.", Toast.LENGTH_SHORT);
		     toast.show();
		     return;
		}
		try
		{
			// 取得<string>10月13日 中雨转小雨</string>中的数据
			String date = detail.getProperty(7).toString();
			// 将"10月13日 中雨转小雨"拆分成两个数组
			String[] date_array = date.split(" ");
			TextView today_text = (TextView) findViewById(R.id.today);
			today_text.setText(date_array[0]);

			// 取得<string>江苏 无锡</string>中的数据
			TextView city_text = (TextView) findViewById(R.id.city_text);
			city_text.setText(detail.getProperty(1).toString());

			TextView today_weather = (TextView) findViewById(R.id.today_weather);
			today_weather.setText(date_array[1]);

			// 取得<string>15℃/21℃</string>中的数据
			TextView qiweng_text = (TextView) findViewById(R.id.qiweng);
			qiweng_text.setText(detail.getProperty(8).toString());

			// 取得<string>今日天气实况：气温：20℃；风向/风力：东南风
			// 2级；湿度：79%</string>中的数据,并通过":"拆分成数组
			TextView shidu_text = (TextView) findViewById(R.id.shidu);
			String date1 = detail.getProperty(4).toString();
			shidu_text.setText(date1.split("：")[4]);

			// 取得<string>东北风3-4级</string>中的数据
			TextView fengli_text = (TextView) findViewById(R.id.fengli);
			fengli_text.setText(detail.getProperty(9).toString());

			// 取得<string>空气质量：良；紫外线强度：最弱</string>中的数据,并通过";"拆分,再通过":"拆分,拆分两次,取得我们需要的数据
			String date2 = detail.getProperty(5).toString();
			String[] date2_array = date2.split("；");
			TextView kongqi_text = (TextView) findViewById(R.id.kongqi);
			kongqi_text.setText(date2_array[0].split("：")[1]);

			TextView zhiwai_text = (TextView) findViewById(R.id.zhiwai);
			zhiwai_text.setText(date2_array[1].split("：")[1]);

			// 设置小贴士数据
			// <string>穿衣指数：较凉爽，建议着长袖衬衫加单裤等春秋过渡装。年老体弱者宜着针织长袖衬衫、马甲和长裤。感冒指数：虽然温度适宜但风力较大，仍较易发生感冒，体质较弱的朋友请注意适当防护。
			//运动指数：阴天，较适宜开展各种户内外运动。洗车指数：较不宜洗车，路面少量积水，如果执意擦洗汽车，要做好溅上泥水的心理准备。晾晒指数：天气阴沉，不利于水分的迅速蒸发，不太适宜晾晒。若需要晾晒，请尽量选择通风的地点。
			//旅游指数：阴天，风稍大，但温度适宜，总体来说还是好天气。这样的天气很适宜旅游，您可以尽情享受大自然的风光。路况指数：阴天，路面比较干燥，路况较好。舒适度指数：温度适宜，风力不大，您在这样的天气条件下，会感到比较清爽和舒适。
			//空气污染指数：气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。紫外线指数：属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。</string>
			String[] xiaotieshi = detail.getProperty(6).toString().split("\n");
			TextView xiaotieshi_text = (TextView) findViewById(R.id.xiaotieshi);
			xiaotieshi_text.setText(xiaotieshi[0]);

			// 设置当日图片
			ImageView image = (ImageView) findViewById(R.id.imageView1);
			int icon = parseIcon(detail.getProperty(10).toString());
			image.setImageResource(icon);

			// 取得第二天的天气情况
			String[] date_str = detail.getProperty(12).toString().split(" ");
			TextView tomorrow_date = (TextView) findViewById(R.id.tomorrow_date);
			tomorrow_date.setText(date_str[0]);

			TextView tomorrow_qiweng = (TextView) findViewById(R.id.tomorrow_qiweng);
			tomorrow_qiweng.setText(detail.getProperty(13).toString());

			TextView tomorrow_tianqi = (TextView) findViewById(R.id.tomorrow_tianqi);
			tomorrow_tianqi.setText(date_str[1]);

			ImageView tomorrow_image = (ImageView) findViewById(R.id.tomorrow_image);
			int icon1 = parseIcon(detail.getProperty(15).toString());
			tomorrow_image.setImageResource(icon1);

			// 取得第三天的天气情况
			String[] date_str1 = detail.getProperty(17).toString().split(" ");
			TextView afterday_date = (TextView) findViewById(R.id.afterday_date);
			afterday_date.setText(date_str1[0]);

			TextView afterday_qiweng = (TextView) findViewById(R.id.afterday_qiweng);
			afterday_qiweng.setText(detail.getProperty(18).toString());

			TextView afterday_tianqi = (TextView) findViewById(R.id.afterday_tianqi);
			afterday_tianqi.setText(date_str1[1]);

			ImageView afterday_image = (ImageView) findViewById(R.id.afterday_image);
			int icon2 = parseIcon(detail.getProperty(20).toString());
			afterday_image.setImageResource(icon2);

			// 取得第四天的天气情况
			String[] date_str3 = detail.getProperty(22).toString().split(" ");
			TextView nextday_date = (TextView) findViewById(R.id.nextday_date);
			nextday_date.setText(date_str3[0]);

			TextView nextday_qiweng = (TextView) findViewById(R.id.nextday_qiweng);
			nextday_qiweng.setText(detail.getProperty(23).toString());

			TextView nextday_tianqi = (TextView) findViewById(R.id.nextday_tianqi);
			nextday_tianqi.setText(date_str3[1]);

			ImageView nextday_image = (ImageView) findViewById(R.id.nextday_image);
			int icon3 = parseIcon(detail.getProperty(25).toString());
			nextday_image.setImageResource(icon3);

		} catch (Exception e)
		{
			showTast(detail.getProperty(0).toString().split("。")[0]);
		}

	}
	
	protected void initUI(String city_str)
	{
		
		if (list == null)
		{
			 Toast toast = Toast.makeText(this, "连接服务器失败", Toast.LENGTH_SHORT);
		     toast.show();
		     return;
		}

		// 取得<string>10月13日 中雨转小雨</string>中的数据
		String date = list.get(7).toString();
		// 将"10月13日 中雨转小雨"拆分成两个数组
		String[] date_array = date.split(" ");
		TextView today_text = (TextView) findViewById(R.id.today);
		today_text.setText(date_array[0]);

		// 取得<string>江苏 无锡</string>中的数据
		TextView city_text = (TextView) findViewById(R.id.city_text);
		city_text.setText(list.get(1).toString());

		TextView today_weather = (TextView) findViewById(R.id.today_weather);
		today_weather.setText(date_array[1]);

		// 取得<string>15℃/21℃</string>中的数据
		TextView qiweng_text = (TextView) findViewById(R.id.qiweng);
		qiweng_text.setText(list.get(8).toString());

		// 取得<string>今日天气实况：气温：20℃；风向/风力：东南风
		// 2级；湿度：79%</string>中的数据,并通过":"拆分成数组
		TextView shidu_text = (TextView) findViewById(R.id.shidu);
		String date1 = list.get(4).toString();
		shidu_text.setText(date1.split("：")[4]);

		// 取得<string>东北风3-4级</string>中的数据
		TextView fengli_text = (TextView) findViewById(R.id.fengli);
		fengli_text.setText(list.get(9).toString());

		// 取得<string>空气质量：良；紫外线强度：最弱</string>中的数据,并通过";"拆分,再通过":"拆分,拆分两次,取得我们需要的数据
		String date2 = list.get(5).toString();
		String[] date2_array = date2.split("；");
		TextView kongqi_text = (TextView) findViewById(R.id.kongqi);
		kongqi_text.setText(date2_array[0].split("：")[1]);

		TextView zhiwai_text = (TextView) findViewById(R.id.zhiwai);
		zhiwai_text.setText(date2_array[1].split("：")[1]);

		// 设置小贴士数据
		// <string>穿衣指数：较凉爽，建议着长袖衬衫加单裤等春秋过渡装。年老体弱者宜着针织长袖衬衫、马甲和长裤。感冒指数：虽然温度适宜但风力较大，仍较易发生感冒，体质较弱的朋友请注意适当防护。
		//运动指数：阴天，较适宜开展各种户内外运动。洗车指数：较不宜洗车，路面少量积水，如果执意擦洗汽车，要做好溅上泥水的心理准备。晾晒指数：天气阴沉，不利于水分的迅速蒸发，不太适宜晾晒。若需要晾晒，请尽量选择通风的地点。
		//旅游指数：阴天，风稍大，但温度适宜，总体来说还是好天气。这样的天气很适宜旅游，您可以尽情享受大自然的风光。路况指数：阴天，路面比较干燥，路况较好。舒适度指数：温度适宜，风力不大，您在这样的天气条件下，会感到比较清爽和舒适。
		//空气污染指数：气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。紫外线指数：属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。</string>
		String[] xiaotieshi = list.get(6).toString().split("\n");
		TextView xiaotieshi_text = (TextView) findViewById(R.id.xiaotieshi);
		xiaotieshi_text.setText(xiaotieshi[0]);

		// 设置当日图片
		ImageView image = (ImageView) findViewById(R.id.imageView1);
		int icon = parseIcon(list.get(10).toString());
		image.setImageResource(icon);

		// 取得第二天的天气情况
		String[] date_str = list.get(12).toString().split(" ");
		TextView tomorrow_date = (TextView) findViewById(R.id.tomorrow_date);
		tomorrow_date.setText(date_str[0]);

		TextView tomorrow_qiweng = (TextView) findViewById(R.id.tomorrow_qiweng);
		tomorrow_qiweng.setText(list.get(13).toString());

		TextView tomorrow_tianqi = (TextView) findViewById(R.id.tomorrow_tianqi);
		tomorrow_tianqi.setText(date_str[1]);

		ImageView tomorrow_image = (ImageView) findViewById(R.id.tomorrow_image);
		int icon1 = parseIcon(list.get(15).toString());
		tomorrow_image.setImageResource(icon1);

		// 取得第三天的天气情况
		String[] date_str1 = list.get(17).toString().split(" ");
		TextView afterday_date = (TextView) findViewById(R.id.afterday_date);
		afterday_date.setText(date_str1[0]);

		TextView afterday_qiweng = (TextView) findViewById(R.id.afterday_qiweng);
		afterday_qiweng.setText(list.get(18).toString());

		TextView afterday_tianqi = (TextView) findViewById(R.id.afterday_tianqi);
		afterday_tianqi.setText(date_str1[1]);

		ImageView afterday_image = (ImageView) findViewById(R.id.afterday_image);
		int icon2 = parseIcon(list.get(20).toString());
		afterday_image.setImageResource(icon2);

		// 取得第四天的天气情况
		String[] date_str3 = list.get(22).toString().split(" ");
		TextView nextday_date = (TextView) findViewById(R.id.nextday_date);
		nextday_date.setText(date_str3[0]);

		TextView nextday_qiweng = (TextView) findViewById(R.id.nextday_qiweng);
		nextday_qiweng.setText(list.get(23).toString());

		TextView nextday_tianqi = (TextView) findViewById(R.id.nextday_tianqi);
		nextday_tianqi.setText(date_str3[1]);

		ImageView nextday_image = (ImageView) findViewById(R.id.nextday_image);
		int icon3 = parseIcon(list.get(25).toString());
		nextday_image.setImageResource(icon3);

		

	}

	// 工具方法，该方法负责把返回的天气图标字符串，转换为程序的图片资源ID。
	private int parseIcon(String strIcon)
	{
		if (strIcon == null)
			return -1;
		if ("0.gif".equals(strIcon))
			return R.drawable.w0;
		if ("1.gif".equals(strIcon))
			return R.drawable.w1;
		if ("2.gif".equals(strIcon))
			return R.drawable.w2;
		if ("3.gif".equals(strIcon))
			return R.drawable.w3;
		if ("4.gif".equals(strIcon))
			return R.drawable.w4;
		if ("5.gif".equals(strIcon))
			return R.drawable.w5;
		if ("6.gif".equals(strIcon))
			return R.drawable.w6;
		if ("7.gif".equals(strIcon))
			return R.drawable.w7;
		if ("8.gif".equals(strIcon))
			return R.drawable.w8;
		if ("9.gif".equals(strIcon))
			return R.drawable.w9;
		if ("10.gif".equals(strIcon))
			return R.drawable.w10;
		if ("11.gif".equals(strIcon))
			return R.drawable.w10;
		if ("12.gif".equals(strIcon))
			return R.drawable.w10;
		if ("13.gif".equals(strIcon))
			return R.drawable.w13;
		if ("14.gif".equals(strIcon))
			return R.drawable.w14;
		if ("15.gif".equals(strIcon))
			return R.drawable.w15;
		if ("16.gif".equals(strIcon))
			return R.drawable.w16;
		if ("17.gif".equals(strIcon))
			return R.drawable.w17;
		if ("18.gif".equals(strIcon))
			return R.drawable.w18;
		if ("19.gif".equals(strIcon))
			return R.drawable.w19;
		if ("20.gif".equals(strIcon))
			return R.drawable.w20;
		if ("21.gif".equals(strIcon))
			return R.drawable.w8;
		if ("22.gif".equals(strIcon))
			return R.drawable.w9;
		if ("23.gif".equals(strIcon))
			return R.drawable.w10;
		if ("24.gif".equals(strIcon))
			return R.drawable.w10;
		if ("25.gif".equals(strIcon))
			return R.drawable.w10;
		if ("26.gif".equals(strIcon))
			return R.drawable.w15;
		if ("27.gif".equals(strIcon))
			return R.drawable.w17;
		if ("28.gif".equals(strIcon))
			return R.drawable.w17;
		if ("29.gif".equals(strIcon))
			return R.drawable.w29;
		if ("30.gif".equals(strIcon))
			return R.drawable.w29;
		if ("31.gif".equals(strIcon))
			return R.drawable.w29;
		return 0;
	}
	
	
	public void writeSharpPreference(String string){
		
		SharedPreferences.Editor editor = preference.edit();
		editor.putString("city", string);
		editor.commit();
	
	}
	
	public String readSharpPreference(){
		
		String city = preference.getString("city", "南京");
		
		return city;
		
	}


	
}
