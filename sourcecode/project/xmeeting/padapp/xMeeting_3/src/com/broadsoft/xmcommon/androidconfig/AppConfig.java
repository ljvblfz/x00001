package com.broadsoft.xmcommon.androidconfig;

public class AppConfig {

	
	
	public String appname;
	public String sdcarddir;

	public String databasename;
	public String databaseversion;
	public String serverenable;
	public String serveripport;
	public String version;

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getSdcarddir() {
		return sdcarddir;
	}

	public void setSdcarddir(String sdcarddir) {
		this.sdcarddir = sdcarddir;
	}

	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	public String getDatabaseversion() {
		return databaseversion;
	}

	public void setDatabaseversion(String databaseversion) {
		this.databaseversion = databaseversion;
	}

	public String getServeripport() {
		return serveripport;
	}

	public void setServeripport(String serveripport) {
		this.serveripport = serveripport;
	}

	public String getServerenable() {
		return serverenable;
	}

	public void setServerenable(String serverenable) {
		this.serverenable = serverenable;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "AppConfig [appname=" + appname + ", sdcarddir=" + sdcarddir
				+ ", databasename=" + databasename + ", databaseversion="
				+ databaseversion + ", serverenable=" + serverenable
				+ ", serveripport=" + serveripport + ", version=" + version
				+ "]";
	}

}
