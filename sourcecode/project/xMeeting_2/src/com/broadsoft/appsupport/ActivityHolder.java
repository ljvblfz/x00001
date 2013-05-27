package com.broadsoft.appsupport;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;

public class ActivityHolder {
	private static ConcurrentHashMap<String,Activity>  activityMap=new ConcurrentHashMap<String,Activity>();
	
	private ActivityHolder(){
		
	}
	
	private static ActivityHolder holder=new ActivityHolder();
	
	public static ActivityHolder getInstance(){
		return holder;
	}
	
	
	
	//=============================
	public void putActivity(String name,Activity activity){
		activityMap.putIfAbsent(name, activity);
	}
	
	public void destroyAllActivity(){
		Enumeration<Activity> enumActivity=activityMap.elements();
		
		while(enumActivity.hasMoreElements()){
			Activity activity=enumActivity.nextElement();
			activity.finish();
		}
	}
	
}
