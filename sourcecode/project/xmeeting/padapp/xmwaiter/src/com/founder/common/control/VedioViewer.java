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
        
        
		//ȫ�� 
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		
		//����ȥ�� 
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		//Ҫ��ȫ����������Ϻ��ټ��ز��� 
		setContentView(R.layout.vedio_viewer); 
		
		//����UI��� 
		VideoView videoView = (VideoView) findViewById(R.id.VideoView01); 
		
		//����MediaController���� 
		MediaController mediaController = new MediaController(this); 
		
		
		//��MediaController����󶨵�VideoView�� 
		mediaController.setAnchorView(videoView); 
		
		//����VideoView�Ŀ�������mediaController 
		videoView.setMediaController(mediaController); 
		
		sFilePath = this.getIntent().getStringExtra("sFilePath");
		
		//�����ַ��������� 
		//videoView.setVideoPath("[url=file:///sdcard/love_480320.mp4]file:///sdcard/love_480320.mp4[/url]"); 
		
		videoView.setVideoURI(Uri.parse(sFilePath)); 
		
		//������Ͳ��� 
		videoView.start(); 

	} 

} 