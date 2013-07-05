package com.founder.common.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FavManager {
	
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

	
	public static  List<Map<String, String>> getFavByType(Context ctx, String sType)
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	    	
    	SharedPreferences stationSearch = ctx.getSharedPreferences("Fav", 0); 

		String itemCollection = stationSearch.getString("FavCollection", null);
		if (itemCollection != null)
		{
			String itemArray[] = itemCollection.split(";");
    		for (int i = itemArray.length - 1; i >= 0; i--)
    		{
    			String item[] = itemArray[i].split(",");
    			if (item[1].equals(sType))
    			{
    				Map<String, String> map = new HashMap<String, String>();
    				map.put("title", item[0]);
    				list.add(map);
    			}
    		}
		}
    		
		return list;
		
	}
	
	public static void addFavByType(Context ctx, String sType, String Args)
	{
		SharedPreferences stationSearch = ctx.getSharedPreferences("Fav", 0);
    	String sItemCollection = stationSearch.getString("FavCollection", null);
    	
		
    	String Item = Args + "," + sType + ";";
    	if (sItemCollection == null)
    		sItemCollection = Item;
		else
			sItemCollection = sItemCollection + Item;
    	
    	Editor editor = stationSearch.edit();
        editor.putString("FavCollection", sItemCollection);
        editor.commit();
	}
	
	public static void addFavLine(Context ctx, String lineId)
	{
		SharedPreferences stationSearch = ctx.getSharedPreferences("Fav", 0);
    	String sItemCollection = stationSearch.getString("FavCollection", null);
    	
    	String sType = "line";
		
    	String Item = lineId + "," + sType + ";";
    	if (sItemCollection == null)
    		sItemCollection = Item;
		else
			sItemCollection = sItemCollection + Item;
    	
    	Editor editor = stationSearch.edit();
        editor.putString("FavCollection", sItemCollection);
        editor.commit();
	}
	
	public static void addFavStation(Context ctx, String stationName)
	{
		SharedPreferences stationSearch = ctx.getSharedPreferences("Fav", 0);
    	String sItemCollection = stationSearch.getString("FavCollection", null);
    	
    	String sType = "station";
		
    	String Item = stationName + "," + sType + ";";
    	if (sItemCollection == null)
    		sItemCollection = Item;
		else
			sItemCollection = sItemCollection + Item;
    	
    	Editor editor = stationSearch.edit();
        editor.putString("FavCollection", sItemCollection);
        editor.commit();
	}
}
