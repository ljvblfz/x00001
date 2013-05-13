package com.broadsoft.xmeeting.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.broadsoft.common.BaseActivityGroup;
import com.broadsoft.xmeeting.R;

public class CompanyInfoActivity extends BaseActivityGroup {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_activity_main);

		TabHost tabHost = (TabHost) findViewById(R.id.companyInfoTabHost);
		tabHost.setup();
		tabHost.setup(super.getLocalActivityManager());

		// 公司介绍
		TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tab_1");
		Intent intent = new Intent().setClass(this,
				CompanyInfoBasicIntroductionActivity.class);
		tabSpec1.setContent(intent);
		tabSpec1.setIndicator("公司介绍");
		tabHost.addTab(tabSpec1);
		// 领导风采
		TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab_2");
		tabSpec2.setContent(R.id.tabLeaderinfo);
		tabSpec2.setIndicator("领导风采");
		tabHost.addTab(tabSpec2);
		// 组织架构
		TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab_3");
		tabSpec3.setContent(R.id.tabOrginfo);
		tabSpec3.setIndicator("组织架构");
		tabHost.addTab(tabSpec3);

		//
		tabHost.setCurrentTab(1);

	}

}
