package com.founder.common.control;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.broadsoft.waiter.R;

public class VedioViewer extends Activity { 

	private String sFilePath;
	
	/** Called when the activity is first created. */ 

	@Override 
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
        
        
		//全屏 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		//标题去掉 
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		//要在全屏等设置完毕后再加载布局 
		setContentView(R.layout.vedio_viewer); 
		
		//定义UI组件 
		VideoView videoView = (VideoView) findViewById(R.id.VideoView01); 
		
		//定义MediaController对象 
		MediaController mediaController = new MediaController(this); 
		
		
		//把MediaController对象绑定到VideoView上 
		mediaController.setAnchorView(videoView); 
		
		//设置VideoView的控制器是mediaController 
		videoView.setMediaController(mediaController); 
		
		sFilePath = this.getIntent().getStringExtra("sFilePath");
		
		//这两种方法都可以 
		//videoView.setVideoPath("[url=file:///sdcard/love_480320.mp4]file:///sdcard/love_480320.mp4[/url]"); 
		
		videoView.setVideoURI(Uri.parse(sFilePath)); 
		
		//启动后就播放 
		videoView.start(); 

	} 

} 