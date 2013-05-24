package com.broadsoft.xmcommon.androiddao;

public class PadInfoEntity {

	private String guid;
	private String androidId;
	private String assetCode;
	private String jsonData;

 

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAndroidId() {
		return androidId;
	}

	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	@Override
	public String toString() {
		return "PadInfoEntity [guid=" + guid + ", androidId=" + androidId
				+ ", assetCode=" + assetCode + ", jsonData=" + jsonData + "]";
	}

}
