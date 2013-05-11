package com.broadsoft.xmeeting.activity;


import io.vov.vitamio.widget.MediaController;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.broadsoft.xmeeting.R;
import com.nmbb.oplayer.OPreference;
import com.nmbb.oplayer.service.MediaScannerService;
import com.nmbb.oplayer.ui.FragmentBase;
import com.nmbb.oplayer.ui.FragmentFileOld;
import com.nmbb.oplayer.ui.FragmentOnline;
import com.nmbb.oplayer.ui.helper.FileDownloadHelper;
import com.nmbb.oplayer.ui.vitamio.LibsChecker;
//import com.hp.hpl.sparta.ParseSource;

public class VideosListActivity extends FragmentActivity implements OnClickListener {

//	ParseSource
	private ViewPager mPager;
//	private RadioButton mRadioFile;
//	PRIVATE RADIOBUTTON MRADIOONLINE;
	public FileDownloadHelper mFileDownload;
	private MediaController mMediaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		new DbHelper<POMedia>().removeAll(POMedia.class);
//		LibsChecker.checkVitamioLibs(ctx)
		if (!LibsChecker.checkVitamioLibs(this, R.string.init_decoders))
			return;

		OPreference pref = new OPreference(this);
		
		String scanPath = "/mnt/sdcard/xmeeting/10001/videos";//Environment.getExternalStorageDirectory().getAbsolutePath();
		//	首次运行，扫描SD卡
//		if (pref.getBoolean(OPlayerApplication.PREF_KEY_FIRST, true)) {
			getApplicationContext()
					.startService(
							new Intent(getApplicationContext(),
									MediaScannerService.class).putExtra(
									MediaScannerService.EXTRA_DIRECTORY, scanPath));

//		}

		setContentView(R.layout.fragment_pager);

		// ~~~~~~ 绑定控件
		mPager = (ViewPager) findViewById(R.id.pager);
//		mRadioFile = (RadioButton) findViewById(R.id.radio_file);
//		mRadioOnline = (RadioButton) findViewById(R.id.radio_online);

		// ~~~~~~ 绑定事件
//		mRadioFile.setOnClickListener(this);
//		mRadioOnline.setOnClickListener(this);
		mPager.setOnPageChangeListener(mPagerListener);

		
		//add 

//		mMediaController = new MediaController(this);
//		mMediaController.setFileName("123123123");
//		mPager.setMediaController(mMediaController);
		
		// ~~~~~~ 绑定数据
		mPager.setAdapter(mAdapter);
		
		//定义返回按钮事件
		registerBackButton();
	}

	@Override
	public void onBackPressed() {
		if (getFragmentByPosition(mPager.getCurrentItem()).onBackPressed())
			return;
		else
			super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mFileDownload != null)
			mFileDownload.stopALl();
	}

	private void initFloatview() {
		wm = (WindowManager) getApplicationContext().getSystemService("window");
		wmParams = new WindowManager.LayoutParams();
		// wmParams=new WindowManager.LayoutParams();
		// wmParams.type=LayoutParams.TYPE_PHONE;
		// wmParams.format=PixelFormat.RGBA_8888;
		// wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL|LayoutParams.FLAG_NOT_FOCUSABLE;
		// wmParams.x=0;
		// wmParams.y=0;
		// wmParams.width=100;
		// wmParams.height=100;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// 启动的方法不用写在oncreate中
		super.onResume();			
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		initFloatview();
//		play1 = new Button(this);
		cache1 = new Button(this);
		createRightButton();
//		System.out.println("resume");
	}

	/** 查找Fragment */
	private FragmentBase getFragmentByPosition(int position) {
		return (FragmentBase) getSupportFragmentManager().findFragmentByTag("android:switcher:" + mPager.getId() + ":" + position);
	}
	
	private WindowManager wm = null; 
	private WindowManager.LayoutParams wmParams = null; 
//	private Button play1; 
	private Button cache1; 
	private int mAlpha = 0; 
	private ViewFlipper viewFlipper = null; 

	
	// 右悬浮键 
	private void createRightButton() {
		wmParams.type = LayoutParams.TYPE_PHONE;
		wmParams.format = PixelFormat.RGBA_8888;
		wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		cache1.setBackgroundResource(R.drawable.back);
		cache1.getBackground().setAlpha(180);
		cache1.setPadding(10,10,10,10);
		cache1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					cache1.setBackgroundResource(R.drawable.back);
					cache1.getBackground().setAlpha(255);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					cache1.setBackgroundResource(R.drawable.back);
					cache1.getBackground().setAlpha(180);
				}
				return false;
			}
		});
		// cache1.setAlpha(0);
		//cache1.setText("缓存");
		cache1.setOnClickListener(mBackListener);
		wmParams.width = 35;
		wmParams.height = 35;
		wmParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
		wmParams.x = 40;
		wmParams.y = 20;
		wm.addView(cache1, wmParams);
	}

	private View.OnClickListener mBackListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) { 
			wm.removeView(cache1); 
			finish();
		}
	};
	
	public void removeBack(){
		wm.removeView(cache1); 
	}
	
	private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

		/** 仅执行一次 */
		@Override
		public Fragment getItem(int position) {
			Fragment result = null;
			switch (position) {
			case 1:
				result = new FragmentOnline();// 在线视频
				break;
			case 0:
			default:
				result = new FragmentFileOld();// 本地视频
				mFileDownload = new FileDownloadHelper(((FragmentFileOld) result).mDownloadHandler);
				break;
			}
			return result;
		}

		@Override
		public int getCount() {
			return 2;
		}
	};

	private ViewPager.SimpleOnPageChangeListener mPagerListener = new ViewPager.SimpleOnPageChangeListener() {
		@Override
		public void onPageSelected(int position) {
//			switch (position) {
//			case 0:// 本地视频
//				mRadioFile.setChecked(true);
//				break;
//			case 1:// 在线视频
//				mRadioOnline.setChecked(true);
//				break;
//			}
		}
	};

	@Override
	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.radio_file:
//			mPager.setCurrentItem(0);
//			break;
//		case R.id.radio_online:
//			mPager.setCurrentItem(1);
//			break;
//		}
	}
	//bottom navigation button
		private Button btnnavhome;
		private Button btnnavback;
		
		public void registerHomeButton() { 
//			Object objView= findViewById(R.id.btnnavhome);  
//			if(objView==null){
//				return;
//			}
//			btnnavhome = (Button)objView;
//			btnnavhome.setOnClickListener(new Button.OnClickListener() { 
//				@Override
//				public void onClick(View view) { 
//					Log.d("System Nav Button--->onClick","btnnavhome"); 
//					
//				}
//			});
		}
		
		
		public void registerBackButton() { 
			Object objView= findViewById(R.id.btnnavback);  
			if(objView==null){
				return;
			}
			btnnavback = (Button) objView;
			btnnavback.setOnClickListener(new Button.OnClickListener() { 
				@Override
				public void onClick(View view) { 
					Log.d("System Nav Button--->onClick","btnnavback"); 
					finish(); 
					execBackButton();
				}
			});
		}
		
		public void execBackButton(){
			
		}
}
