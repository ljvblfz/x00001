package com.founder.common.data;


import android.content.res.AssetManager;

public class TestNetWorker {
	
	public static boolean TestNet(AssetManager assetManager)
	{
		DataConfigManager dcm = new DataConfigManager(assetManager);
		String sUrl = dcm.getAuthenticationUrl("license", "null");
		String sJsonData = null;
		try {
			sJsonData = HttpService.getByHttpClientUnzip(sUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (sJsonData == null || sJsonData == "")
			return false;
		else
			return true;
	}

}
