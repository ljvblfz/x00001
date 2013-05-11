package com.broadsoft.xmeeting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.broadsoft.common.BaseActivity;
import com.broadsoft.xmeeting.activity.CallServiceActivity;
import com.broadsoft.xmeeting.activity.CompanyInfoActivity;
import com.broadsoft.xmeeting.activity.DocumentsListActivity;
import com.broadsoft.xmeeting.activity.ImageGallaryMainActivity;
import com.broadsoft.xmeeting.activity.MeetingGuideActivity;
import com.broadsoft.xmeeting.activity.MessageServiceActivity;
import com.broadsoft.xmeeting.activity.SysSettingActivity;
import com.broadsoft.xmeeting.activity.TimerServiceActivity;
import com.broadsoft.xmeeting.activity.VideosListActivity;
import com.broadsoft.xmeeting.activity.VotingActivity;

/**
 * http://blog.csdn.net/Class_Raito/article/details/3390737
 * 
 * http://itil-rong.iteye.com/blog/901564   activity传递参数
 * 
 * @author lu.zhen
 * 
 */
public class DesktopActivity extends BaseActivity { 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.desktop_activity_main);

		initNavButtonForMeetingguide();
		initNavButtonForCallService();
		initNavButtonForCompanyInfo();
		initNavButtonForImageGallary();
//		initNavButtonForVote();
		initNavButtonSysSetting();
		initNavButtonTimerService();
//		initNavButtonMessageService();
//		initNavButtonInternetBrowser();
		
		initNavButtonForDocuments();
		initNavButtonForVideos();
	}

	
	//====================>
	public void initNavButtonForDocuments() {
		Button btnnavdocumentguide = (Button) findViewById(R.id.desktop_btnnavdocument);
		btnnavdocumentguide.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				Intent intent = new Intent();
				intent.setClass(DesktopActivity.this, DocumentsListActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}//end of on click
		});

	}//
	public void initNavButtonForVideos() {
		Button btnnavvideoguide = (Button) findViewById(R.id.desktop_btnnavvideo);
		btnnavvideoguide.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				Intent intent = new Intent();
				intent.setClass(DesktopActivity.this, VideosListActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}//end of on click
		});

	}//

	//
	public void initNavButtonForMeetingguide() {
		Button btnnavmeetingguide = (Button) findViewById(R.id.desktop_btnnavmeetingguide);
		btnnavmeetingguide.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				Intent intent = new Intent();
				intent.setClass(DesktopActivity.this, MeetingGuideActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}//end of on click
		});

	}//
	 
	
	//
	public void initNavButtonForCallService() {
		Button btnnavcallservice = (Button) findViewById(R.id.desktop_btnnavcallservice);
		btnnavcallservice.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				Intent intent = new Intent();
				intent.setClass(DesktopActivity.this, CallServiceActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}//end of on click
		});

	}//
	
	//
	public void initNavButtonForCompanyInfo() {
		Button btnnavcompanyinfo = (Button) findViewById(R.id.desktop_btnnavcompanyinfo);
		btnnavcompanyinfo.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				Intent intent = new Intent();
				intent.setClass(DesktopActivity.this, CompanyInfoActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}//end of on click
		});

	}// 

	//
	public void initNavButtonForImageGallary() {
		Button btnnavimagegallary = (Button) findViewById(R.id.desktop_btnnavimage);
		btnnavimagegallary.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				Intent intent = new Intent();
				intent.setClass(DesktopActivity.this, ImageGallaryMainActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}//end of on click
		});

	}//

	//
//	public void initNavButtonForVote() {
//		Button btnnavvote = (Button) findViewById(R.id.desktop_btnnavvote);
//		btnnavvote.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) { 
//				Intent intent = new Intent();
//				intent.setClass(DesktopActivity.this, VotingActivity.class);// 指定了跳转前的Activity和跳转后的Activity
//				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
//				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity
//
//			}//end of on click
//		});
//
//	}//
	//
	public void initNavButtonSysSetting() {
		Button btnnavsetting = (Button) findViewById(R.id.desktop_btnnavsyssetting);
		btnnavsetting.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				Intent intent = new Intent();
				intent.setClass(DesktopActivity.this, SysSettingActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}//end of on click
		});

	}//
	//
	public void initNavButtonTimerService() {
		Button btnnavsetting = (Button) findViewById(R.id.desktop_btnnavtimer);
		btnnavsetting.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) { 
				Intent intent = new Intent();
				intent.setClass(DesktopActivity.this, TimerServiceActivity.class);// 指定了跳转前的Activity和跳转后的Activity
				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity

			}//end of on click
		});

	}//
	
	

//	public void initNavButtonMessageService() {
//		Button btnnavmessage = (Button) findViewById(R.id.desktop_btnnavmessage);
//		btnnavmessage.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) { 
//				Intent intent = new Intent();
//				intent.setClass(DesktopActivity.this, MessageServiceActivity.class);// 指定了跳转前的Activity和跳转后的Activity
//				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
//				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity
//
//			}//end of on click
//		});
//
//	}//
	
	

//	public void initNavButtonInternetBrowser() {
//		Button btnnavmessage = (Button) findViewById(R.id.desktop_btnnavinternetbrowser);
//		btnnavmessage.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) { 
//				Intent intent = new Intent();
//				intent.setClass(DesktopActivity.this, InternetBrowserActivity.class);// 指定了跳转前的Activity和跳转后的Activity
//				intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
//				startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity
//
//			}//end of on click
//		});
//
//	}//
	
	
}
