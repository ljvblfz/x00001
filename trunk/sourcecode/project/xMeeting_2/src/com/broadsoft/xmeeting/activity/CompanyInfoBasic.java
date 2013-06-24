package com.broadsoft.xmeeting.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmeeting.R;

public class CompanyInfoBasic extends Activity {
 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_basicinfo_webview);  
		WebView webView =(WebView)findViewById(R.id.wvBasicInfoStandalone); 
//		webView.setBackgroundColor(0x00000000);
//		webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
		webView.setWebViewClient(new WebViewClient());
		String htmlBasicInfo = getHtmlData2();
//		webView.loadData(htmlBasicInfo, "text/html", "gbk");//中文乱码
		webView.loadDataWithBaseURL(null, htmlBasicInfo, "text/html", "utf-8", null);
 
		 
	}

//	private String getHtmlData() {
//		
//		
//		String htmlBasicInfo="<html><body style='font-size:30px'>" ;
//		htmlBasicInfo+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;江苏省电力公司主要从事江苏省境内电网建设、运行与管理，经营江苏电力销售业务。公司下辖13个市、51个县供电公司及20多个检修、施工、科研等单位，服务全省3300余万电力客户。公司现有职工约4.1万人、农电工约4.4万人；管辖35千伏及以上变电站2750余座、输电线路8.1万公里。";
//		htmlBasicInfo+="<br/>"; 
//		htmlBasicInfo+="<br/>"; 
//		htmlBasicInfo+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2012年，江苏省电力公司认真贯彻落实江苏省委省政府和国家电网公司决策部署，全力服务经济社会发展大局，切实履行社会责任，各方面工作取得新进展。江苏电网安全可靠运行，电力供应平稳有序，圆满完成十八大保供电、迎峰度夏、抗台抢险等任务。全力助推经济转型升级，落实“稳增长、调结构、惠民生”各项举措；特高压入苏工程取得突破，锦屏～苏南特高压直流工程按期投运。各级电网协调发展，全年完成电网建设投资347亿元，同比增长12.7%。主动服务清洁能源发展，确保光伏发电、沿海风电可靠并网消纳。全年全社会用电量完成4581亿千瓦时，同比增长7.0%，保持了较高增长速度，好于广东、浙江、山东等主要经济大省。公司囊括国家电网公司同业对标“综合标杆”、“业绩标杆”和“管理标杆”三项第一名；获得7项“专业管理标杆”。";
//		htmlBasicInfo+="</body></html>";
//		return htmlBasicInfo;
//	}
	
	private String getHtmlData2() {
		String jsonData=EntityInfoHolder.getInstance().getCompanyInfoEntity().getJsonData();

		String htmlBasicInfo="<html><body style='font-size:30px'>" ;
		try {
			JSONObject jsonObject=new JSONObject(jsonData);
			JSONObject companyInfo=jsonObject.getJSONObject("companyinfo"); 
			String compinfo=companyInfo.getString("xmciDescription");
			if(null!=compinfo&&!"".equals(compinfo)){
				htmlBasicInfo+=compinfo;
			}
		} catch (JSONException e) { 
			e.printStackTrace();
		} 
		htmlBasicInfo+="</body></html>";
		return htmlBasicInfo;
	}
	 
	
	 
	
	 
}
