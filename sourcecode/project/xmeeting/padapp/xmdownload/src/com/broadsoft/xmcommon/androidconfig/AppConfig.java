package com.broadsoft.xmcommon.androidconfig;

public class AppConfig {

	public String appname;
	public String sdcarddir;

	public String databasename;
	public String databaseversion;
	public String serveripport;

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

	@Override
	public String toString() {
		return "AppConfig [appname=" + appname + ", sdcarddir=" + sdcarddir
				+ ", databasename=" + databasename + ", databaseversion="
				+ databaseversion + ", serveripport=" + serveripport + "]";
	}

}
