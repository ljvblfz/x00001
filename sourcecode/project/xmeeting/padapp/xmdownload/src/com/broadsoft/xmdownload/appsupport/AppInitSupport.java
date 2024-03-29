package com.broadsoft.xmdownload.appsupport;

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
import com.broadsoft.xmdownload.wsservice.WsDownloadServiceSupport;

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
		//
		if("0".equals(appConfig.getServerenable())){ 
			//初始化demo数据
			String jsonData=AssetManagerSupport.readText(assetManager); 
			DemoDataInit.init(jsonData); 
			Log.d(TAG, "[Demo]jsonData---->"+jsonData);
		}else{ 
			// 监听websocket消息
			WsDownloadServiceSupport.getInstance().initData(AndroidIdSupport.getAndroidID());
//			try{
//				WsDownloadServiceSupport.getInstance().disconnect();
//			}catch(Exception e){ 
//				Log.d(TAG, "[WS]disconnect---exception--"+e.getMessage());
//			}
//			try{ 
//				Log.d(TAG, "[WS]wait 10s"); 
//				Thread.sleep(10*1000);
//			}catch(Exception e){ 
//				Log.d(TAG, "[WS]sleep---exception--"+e.getMessage());
//			}
//			WsDownloadServiceSupport.getInstance().connect(); 
			Log.d(TAG, "[WS]connect---->done.");
		}
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
	}
	
	

	public static void destroyApp(Context ctx,AssetManager assetManager) { 
		
	}
}
