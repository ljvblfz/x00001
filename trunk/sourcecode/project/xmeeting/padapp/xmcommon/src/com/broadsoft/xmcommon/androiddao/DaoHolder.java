package com.broadsoft.xmcommon.androiddao;

import android.content.Context;
import android.util.Log;

public class DaoHolder {

	private String TAG = "DaoHolder";
	private DownloadInfoDao downloadInfoDao;
	private PadInfoDao padInfoDao;
	private CompanyInfoDao companyInfoDao; 
	
	private static DaoHolder daoHolder=new DaoHolder();
	public static DaoHolder getInstance(){
		return daoHolder;
	}
	
	public void init(Context context){
		Log.d(TAG, "init begin");
		this.downloadInfoDao=new DownloadInfoDao(context);
		this.padInfoDao=new PadInfoDao(context); 
//		this.companyInfoDao=new CompanyInfoDao(context); 
		Log.d(TAG, "init end");
		
	}
	
	public DownloadInfoDao getDownloadInfoDao(){ 
		return downloadInfoDao;
	}
	
	
	public PadInfoDao getPadInfoDao(){ 
		return padInfoDao;
	}

	public CompanyInfoDao getCompanyInfoDao() {
		return companyInfoDao;
	}

}
