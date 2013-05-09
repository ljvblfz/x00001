package com.broadsoft.xmeeting.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

import com.broadsoft.xmeeting.R;
import com.nmbb.oplayer.OPlayerApplication;
import com.nmbb.oplayer.OPreference;
import com.nmbb.oplayer.database.DbHelper;
import com.nmbb.oplayer.po.POMedia;
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
//	private RadioButton mRadioOnline;
	public FileDownloadHelper mFileDownload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		new DbHelper<POMedia>().removeAll(POMedia.class);
//		LibsChecker.checkVitamioLibs(ctx)
		if (!LibsChecker.checkVitamioLibs(this, R.string.init_decoders))
			return;

		OPreference pref = new OPreference(this);
		
		String scanPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		//	首次运行，扫描SD卡
		if (pref.getBoolean(OPlayerApplication.PREF_KEY_FIRST, true)) {
			getApplicationContext()
					.startService(
							new Intent(getApplicationContext(),
									MediaScannerService.class).putExtra(
									MediaScannerService.EXTRA_DIRECTORY, scanPath));
		}

		setContentView(R.layout.fragment_pager);

		// ~~~~~~ 绑定控件
		mPager = (ViewPager) findViewById(R.id.pager);
//		mRadioFile = (RadioButton) findViewById(R.id.radio_file);
//		mRadioOnline = (RadioButton) findViewById(R.id.radio_online);

		// ~~~~~~ 绑定事件
//		mRadioFile.setOnClickListener(this);
//		mRadioOnline.setOnClickListener(this);
		mPager.setOnPageChangeListener(mPagerListener);

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

	/** 查找Fragment */
	private FragmentBase getFragmentByPosition(int position) {
		return (FragmentBase) getSupportFragmentManager().findFragmentByTag("android:switcher:" + mPager.getId() + ":" + position);
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
