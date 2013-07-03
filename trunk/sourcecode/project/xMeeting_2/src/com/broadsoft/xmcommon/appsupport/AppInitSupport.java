package com.broadsoft.xmcommon.appsupport;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.AppConfig;
import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmcommon.androiddao.PadInfoEntity;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
import com.broadsoft.xmdownload.wsservice.WsControllerServiceSupport;

public class AppInitSupport {
	private static final String TAG="AppInitSupport"; 


	public static void initApp(Context ctx,AssetManager assetManager) {
		//初始化配置
		DomAppConfigFactory.init(assetManager);
		AppConfig appConfig=DomAppConfigFactory.getAppConfig();
		Log.d(TAG, "[Config]AppConfig---->"+appConfig);
		//初始化设备ID
		AndroidIdSupport.init(ctx);
		Log.d(TAG, "[AndroidI]AndroidID---->"+AndroidIdSupport.getAndroidID()); 
		String sdcardDir = SDCardSupport.getSDCardDirectory();
		Log.d(TAG, "[SDCard]sdcardDir---->"+sdcardDir);
		// 初始化数据库		
		DaoHolder.getInstance().init(ctx); 
//		// 
		//读取会议信息
		PadInfoEntity padInfoEntity=DaoHolder.getInstance().getPadInfoDao().uniqueOne();
		Log.d(TAG, "[Sqlite]PadInfoEntity---->"+padInfoEntity);
//		CompanyInfoEntity companyInfoEntity=DaoHolder.getInstance().getCompanyInfoDao().uniqueOne();
//		Log.d(TAG, "[Sqlite]CompanyInfoEntity---->"+companyInfoEntity); 
		DownloadInfoEntity downloadInfoEntity=DaoHolder.getInstance().getDownloadInfoDao().findByActivate();
		Log.d(TAG, "[Sqlite]DownloadInfoEntity---->"+downloadInfoEntity);
		//
//		EntityInfoHolder.getInstance().setCompanyInfoEntity(companyInfoEntity);
		EntityInfoHolder.getInstance().setPadInfoEntity(padInfoEntity);
		EntityInfoHolder.getInstance().setDownloadInfoEntity(downloadInfoEntity);

	}//end of initApp




//	private static void connectWS() {
//		try{
//			WsControllerServiceSupport.getInstance().disconnect();  
//		}catch(Exception e){ 
//			Log.d(TAG, "[Websocket]disconnect---exception--"+e.getMessage());
//		}
//		
//		try{ 
//			Log.d(TAG, "[WS]wait 10s"); 
//			Thread.sleep(10*1000);
//		}catch(Exception e){ 
//			Log.d(TAG, "[Websocket]sleep---exception--"+e.getMessage());
//		}
//		WsControllerServiceSupport.getInstance().connect();
//	}
	
	
	
	
	public static void initDownloadInfoEntity(){ 
		DownloadInfoEntity downloadInfoEntity=DaoHolder.getInstance().getDownloadInfoDao().findByActivate();
		EntityInfoHolder.getInstance().setDownloadInfoEntity(downloadInfoEntity);
		
	}
	
	public static void initPadInfoEntity(){ 
		PadInfoEntity padInfoEntity=DaoHolder.getInstance().getPadInfoDao().uniqueOne();
		EntityInfoHolder.getInstance().setPadInfoEntity(padInfoEntity);
		
	}
	
//	public static void initCompanyInfoEntity(){ 
//		CompanyInfoEntity companyInfoEntity=DaoHolder.getInstance().getCompanyInfoDao().uniqueOne();
//		EntityInfoHolder.getInstance().setCompanyInfoEntity(companyInfoEntity);
//		
//	}
	

	public static void debugAppData() {
		//读取会议信息
		PadInfoEntity padInfoEntity=EntityInfoHolder.getInstance().getPadInfoEntity();
		Log.d(TAG, "[debugAppData]PadInfoEntity---->"+padInfoEntity);
//		CompanyInfoEntity companyInfoEntity=EntityInfoHolder.getInstance().getCompanyInfoEntity();
//		Log.d(TAG, "[debugAppData]CompanyInfoEntity---->"+companyInfoEntity); 
		DownloadInfoEntity downloadInfoEntity=EntityInfoHolder.getInstance().getDownloadInfoEntity();
		Log.d(TAG, "[debugAppData]DownloadInfoEntity---->"+downloadInfoEntity);
	}
	
	

	public static void destroyApp(Context ctx,AssetManager assetManager) { 
		WsControllerServiceSupport.getInstance().disconnect();
	}
}
