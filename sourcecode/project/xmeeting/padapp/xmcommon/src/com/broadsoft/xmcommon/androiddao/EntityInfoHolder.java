package com.broadsoft.xmcommon.androiddao;

import org.json.JSONException;
import org.json.JSONObject;

public class EntityInfoHolder {
	private static final String TAG = "EntityInfoHolder";
	
	
//	private CompanyInfoEntity companyInfoEntity;
	private DownloadInfoEntity downloadInfoEntity;
	private PadInfoEntity padInfoEntity;
	private String xmpdGuid;
	
	private static EntityInfoHolder entityHolder = new EntityInfoHolder();

	public static EntityInfoHolder getInstance() {
		return entityHolder;
	}

	//
	public String getXmpdGuid() throws JSONException {
		if (null != xmpdGuid) {
			return xmpdGuid;
		}  else {
			String jsonData = padInfoEntity.getJsonData();
			JSONObject jsonObject = new JSONObject(jsonData);
			xmpdGuid = jsonObject.getString("xmpdGuid");
			return xmpdGuid;
		}
	} // end of getXmpdGuid

	public String getAssetCode() throws JSONException {
		String assetCode = padInfoEntity.getAssetCode();
		return assetCode;
	}

	//
//
//	public CompanyInfoEntity getCompanyInfoEntity() {
//		return companyInfoEntity;
//	}
//
//	public void setCompanyInfoEntity(CompanyInfoEntity companyInfoEntity) {
//		this.companyInfoEntity = companyInfoEntity;
//	}

	public DownloadInfoEntity getDownloadInfoEntity() {
		return downloadInfoEntity;
	}

	public void setDownloadInfoEntity(DownloadInfoEntity downloadInfoEntity) {
		this.downloadInfoEntity = downloadInfoEntity;
	}

	public PadInfoEntity getPadInfoEntity() {
		return padInfoEntity;
	}

	public void setPadInfoEntity(PadInfoEntity padInfoEntity) {
		this.padInfoEntity = padInfoEntity;
	}

}
