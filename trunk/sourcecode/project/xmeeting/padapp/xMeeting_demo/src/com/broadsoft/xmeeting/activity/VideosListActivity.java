package com.broadsoft.xmeeting.activity;


import io.vov.vitamio.widget.MediaController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.broadsoft.common.util.FolderUtils;
import com.broadsoft.xmeeting.R;
import com.nmbb.oplayer.OPreference;
import com.nmbb.oplayer.database.DbHelper;
import com.nmbb.oplayer.exception.Logger;
import com.nmbb.oplayer.po.POMedia;
import com.nmbb.oplayer.ui.FragmentBase;
import com.nmbb.oplayer.ui.FragmentFileOld;
import com.nmbb.oplayer.ui.helper.FileDownloadHelper;
import com.nmbb.oplayer.ui.vitamio.LibsChecker;
import com.nmbb.oplayer.util.FileUtils;
import com.nmbb.oplayer.util.PinyinUtils;
//import com.hp.hpl.sparta.ParseSource;

public class VideosListActivity extends FragmentActivity implements OnClickListener {

//	ParseSource
	private ViewPager mPager;
//	private RadioButton mRadioFile;
//	PRIVATE RADIOBUTTON MRADIOONLINE;
	public FileDownloadHelper mFileDownload;
	private MediaController mMediaController;

	public String getSDPath() {
		File sdDir = null;
//		boolean sdCardExist = Environment.getExternalStorageState().equals(
//				Android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
//		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
//		}
		return sdDir.getAbsolutePath();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new DbHelper<POMedia>().removeAll(POMedia.class);
//		LibsChecker.checkVitamioLibs(ctx)
		if (!LibsChecker.checkVitamioLibs(this, R.string.init_decoders))
			return;

		OPreference pref = new OPreference(this);
		
		String scanPath = FolderUtils.getVideoDir(FolderUtils.demoMeetingId);//getSDPath()+"/xmeeting/10001/videos";//Environment.getExternalStorageDirectory().getAbsolutePath();
		//	首次运行，扫描SD卡
//		if (pref.getBoolean(OPlayerApplication.PREF_KEY_FIRST, true)) {
//			getApplicationContext()
//					.startService(
//							new Intent(getApplicationContext(),
//									MediaScannerService.class).putExtra(
//									MediaScannerService.EXTRA_DIRECTORY, scanPath));

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
//		play1 = new Button(this);
//		System.out.println("resume");
	}

	/** 查找Fragment */
	private FragmentBase getFragmentByPosition(int position) {
		return (FragmentBase) getSupportFragmentManager().findFragmentByTag("android:switcher:" + mPager.getId() + ":" + position);
	}
	  

	private View.OnClickListener mBackListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) { 
			finish();
		}
	};
	
	
	private FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

		/** 仅执行一次 */
		@Override
		public Fragment getItem(int position) {
			Fragment result = null;
			
			
			result = new FragmentFileOld();// 本地视频
			mFileDownload = new FileDownloadHelper(((FragmentFileOld) result).mDownloadHandler);
//			switch (position) {
//			case 1:
//				result = new FragmentOnline();// 在线视频
//				break;
//			case 0:
//			default:
//				result = new FragmentFileOld();// 本地视频
//				mFileDownload = new FileDownloadHelper(((FragmentFileOld) result).mDownloadHandler);
//				break;
//			}
			return result;
		}

		@Override
		public int getCount() {
			return 1;
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
		
		/**
		 * Disable back key
		 */
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			Log.d("BaseActivity--->onKeyDown", "onKeyDown  keyCode----->" + keyCode);
			Log.d("BaseActivity--->onKeyDown", "onKeyDown  KEYCODE_BACK----->" +  KeyEvent.KEYCODE_BACK);
			Log.d("BaseActivity--->onKeyDown", "onKeyDown  KEYCODE_HOME----->" +  KeyEvent.KEYCODE_HOME);
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				// do something
				return false;
			} else if (keyCode == KeyEvent.KEYCODE_HOME) {
				// do something
				return false;
			}
			 
			return super.onKeyDown(keyCode, event); 
			// Disable all keys
//			return false;
		}
		private DbHelper<POMedia> mDbHelper = new DbHelper<POMedia>();;
		private Map<String, Object> mDbWhere = new HashMap<String, Object>(2);
		
		/**
		 * 保存入库
		 * 
		 * @throws FileNotFoundException
		 */
		private void save(POMedia media) {
			mDbWhere.put("path", media.path);
			mDbWhere.put("last_modify_time", media.last_modify_time);
			//检测
			if (!mDbHelper.exists(media, mDbWhere)) {
				try {
					if (media.title != null && media.title.length() > 0)
						media.title_key = PinyinUtils.chineneToSpell(media.title.charAt(0) + "");
				} catch (Exception ex) {
					Logger.e(ex);
				}
				media.last_access_time = System.currentTimeMillis();

				//提取缩略图
				//			extractThumbnail(media);
//				media.mime_type = FileUtils.getMimeType(media.path);

				//入库
				mDbHelper.create(media);

				//扫描到一个
//				notifyObservers(SCAN_STATUS_RUNNING, media);
			}
		}
}
