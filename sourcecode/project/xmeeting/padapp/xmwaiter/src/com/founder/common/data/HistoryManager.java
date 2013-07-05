package com.founder.common.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HistoryManager {
	
	public class Fav
	{
		private String id;
		
		private String about;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAbout() {
			return about;
		}
		public void setAbout(String about) {
			this.about = about;
		}
		
		
	}
	public static  List<Map<String, String>> getAllHistory(Context ctx)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    	

    	SharedPreferences stationSearch = ctx.getSharedPreferences("His", 0); 

		String sStationCollection = stationSearch.getString("HisCollection", null);
		if (sStationCollection != null)
		{
			String[] sStationArray = sStationCollection.split(";");
    		
    		for (int i = sStationArray.length - 1; i >= 0; i--)
    		{
    			String[] sItem = sStationArray[i].split(",");
    			String sName = sItem[0];
    			String sRoad = sItem[1];
    			String sGuid = sItem[2];
    			Map<String, String> map = new HashMap<String, String>();
    		    map.put("title", sName);
    		    map.put("info", sRoad);
    		    map.put("sGuid", sGuid);
    		    list.add(map);
    		}
		}
    		
		return list;
		
	}
	
	public static  List<Map<String, String>> getHistoryByType(Context ctx, String sType)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    	

    	SharedPreferences stationSearch = ctx.getSharedPreferences("His", 0); 

		String sStationCollection = stationSearch.getString("HisCollection", null);
		if (sStationCollection != null)
		{
			String[] sStationArray = sStationCollection.split(";");
    		
    		for (int i = sStationArray.length - 1; i >= 0; i--)
    		{
    			String[] sItem = sStationArray[i].split(",");
    			if (sItem[2].equals(sType))
    			{
    				Map<String, String> map = new HashMap<String, String>();
    				map.put("title", sItem[0]);
    				map.put("info", sItem[1]);
    				
    				list.add(map);
    			}
    		}
		}
    		
		return list;
		
	}
	
	public static void addHistoryLine(Context ctx, String lineId, String sInfo)
	{
		SharedPreferences stationSearch = ctx.getSharedPreferences("His", 0);
    	String sItemCollection = stationSearch.getString("HisCollection", null);
    	
    	String sType = "line";
		
    	String Item = lineId + "," + sInfo + "," + sType + ";";
    	if (sItemCollection == null)
    		sItemCollection = Item;
		else
			sItemCollection = sItemCollection + Item;
    	
    	Editor editor = stationSearch.edit();
        editor.putString("HisCollection", sItemCollection);
        editor.commit();
	}
	
	public static void addHistoryStation(Context ctx, String stationName, String sInfo)
	{
		SharedPreferences stationSearch = ctx.getSharedPreferences("His", 0);
    	String sItemCollection = stationSearch.getString("HisCollection", null);
    	
    	String sType = "station";
		
    	String Item = stationName + "," + sInfo + "," + sType + ";";
    	if (sItemCollection == null)
    		sItemCollection = Item;
		else
			sItemCollection = sItemCollection + Item;
    	
    	Editor editor = stationSearch.edit();
        editor.putString("HisCollection", sItemCollection);
        editor.commit();
	}
}
