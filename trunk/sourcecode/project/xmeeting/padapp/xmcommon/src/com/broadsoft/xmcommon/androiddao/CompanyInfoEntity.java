package com.broadsoft.xmcommon.androiddao;

public class CompanyInfoEntity {

	private String guid;
	private String companyName;
	private String companyCode;
	private String jsonData;
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	@Override
	public String toString() {
		return "CompanyInfoEntity [guid=" + guid + ", companyName="
				+ companyName + ", companyCode=" + companyCode + ", jsonData="
				+ jsonData + "]";
	}

  

}
