package com.broadsoft.xmeeting.htmldata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MeetingGuideHtmlDataSupport {

	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String genSchedule(JSONObject data){ 
		//
		if(data!=null){ 
			StringBuilder sbHtml=new StringBuilder();
			sbHtml.append("<table style='font-size:30px;' width='100%'>"); 
			try {
				JSONArray jsonArray=data.getJSONArray("listOfXmMeetingSchedule");
				for(int i=0;i<jsonArray.length();i++){
					JSONObject  jsonObject=jsonArray.getJSONObject(i);
					String xmmsTitle=jsonObject.getString("xmmsTitle");

					sbHtml.append("<tr>");
					sbHtml.append("		<td colspn='2' style='color:#FFA500'>"+xmmsTitle+"</td>");  
					sbHtml.append("</tr>"); 
					JSONArray jsonArrayDetail=jsonObject.getJSONArray("listOfXmMeetingScheduleDetail");
					for(int j=0;j<jsonArrayDetail.length();j++){
						JSONObject  jsonDetail=jsonArrayDetail.getJSONObject(j);
						String xmmsdTime=jsonDetail.getString("xmmsdTime");
						String xmmsdTitle=jsonDetail.getString("xmmsdTitle");
						String xmmsdDescription=jsonDetail.getString("xmmsdDescription");
						sbHtml.append("<tr>");
						sbHtml.append("		<td>"+xmmsdTime+"</td><td>"+xmmsdTitle+"</td>");  
						sbHtml.append("</tr>");  
						sbHtml.append("<tr>");
						sbHtml.append("		<td>"+xmmsdDescription+"</td>");  
						sbHtml.append("</tr>"); 
					}//end of for j
				}//end of for i 
			} catch (JSONException e) { 
				e.printStackTrace();
			}  
			sbHtml.append("</table>"); 
			return sbHtml.toString();
		}//end of if data!=null
		
		StringBuilder sb=new StringBuilder();
		sb.append("<table style='font-size:30px;' width='100%'>"); 
		sb.append("<tr>");
		sb.append("		<td colspn='2' style='color:#FFA500'>11月1日（星期四）</td>");  
		sb.append("</tr>"); 
		//
		sb.append("<tr>");
		sb.append("		<td  >14:21</td><td>迎接</td>");  
		sb.append("</tr>"); 
		sb.append("<tr>");
		sb.append("		<td  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>冯总一行乘G138（上海—南京，13：00出发）<br/>到达南京南站 <br/>迎候人员：单业才   胡玉海  钱朝阳 <br/>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;黄志高   吴  俊  高仲华 <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;季  强</td>");  
		sb.append("</tr>"); 
		sb.append("<tr>");
		sb.append("		<td  >14：21—15：20</td><td>前往江苏省电力物资供应公司参观调研</td>");  
		sb.append("</tr>");  
		sb.append("<tr>");
		sb.append("		<td  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>迎候人员：潘震东  唐建清  薛  祥</td>");  
		sb.append("</tr>");  
		sb.append("<tr>");
		sb.append("		<td>15：20—16：00</td><td>参观考察省调控中心（省公司31楼）</td>");  
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>一楼迎候人员：甑玉林  林  敏  周建海<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;夏  俊  王之伟</td>");  
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("		<td>16：00—17：00</td><td>上海市电力公司与江苏省电力公司交流座谈会</td>");  
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>会议地点：省公司6楼会议室<br/>江苏公司参加人员：<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单业才   胡玉海   潘震东   钱朝阳 <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;黄志高   甑玉林   林  敏   周建海 <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;夏  俊   王之伟   吴  俊   陈  庆 <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;顾国栋   白元强   季  强   王  旭 <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;黄  强 <br/></td>");  
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("		<td>17：30</td><td>晚餐</td>");  
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>用餐地点：金陵饭店三楼扬子A厅<br/>（第一桌）<br/>用餐人员：<br/>上海公司：冯  军  阮前途  黄良宝 <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;王  路  范永根  沈兆新<br/> 江苏公司：单业才  胡玉海  马苏龙  <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;潘震东  钱朝阳  李  斌<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;黄志高   甑玉林   林  敏<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;周建海   夏  俊   王之伟 </td>");  
		sb.append("</tr>"); 
		sb.append("<tr>");
		sb.append("		<td> &nbsp;</td><td> &nbsp;</td>");  
		sb.append("</tr>");
		//
		sb.append("<tr>");
		sb.append("		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>用餐地点：金陵饭店三楼紫金厅<br/>（第二桌）<br/>用餐人员：<br/>上海公司：车申刚  史济康  吴英姿<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 周  简  王  伟  应志玮<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  陈红兵  陈  杰  牛  凯<br/> 江苏公司：吴  俊   陈  庆  顾国栋<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;白元强   高仲华  季  强<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;王  旭   黄  强 </td>");  
		sb.append("</tr>"); 
		sb.append("<tr>");
		sb.append("		<td>19：00</td><td>礼送（G7251次20：11分，备G19次19：46分)</td>");  
		sb.append("</tr>"); 
		sb.append("<tr>");
		sb.append("		<td></td><td>送行人员：单业才  胡玉海  钱朝阳  <br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;黄志高  吴  俊   高仲华 季  强</td>");  
		sb.append("</tr>"); 
		//  
		sb.append("</table>"); 
		return sb.toString(); 
	}//end of genSchedule
	
	 

	public static String genWeatherInfo(JSONObject data){
		if(data!=null){
			StringBuilder sbHtml=new StringBuilder();
			try {
				JSONArray jsonArray=data.getJSONArray("listOfXmMeetingWeather");
				sbHtml.append("<table style='font-size:30px;' width='100%'>"); 
				for(int i=0;i<jsonArray.length();i++){
					JSONObject  jsonObject=jsonArray.getJSONObject(i); 
					String xmmwDescription=jsonObject.getString("xmmwDescription");
					sbHtml.append("<tr  >");
					sbHtml.append("		<td >"+xmmwDescription+"</td>");  
					sbHtml.append("</tr>");    
				}
				sbHtml.append("</table>"); 
				
			} catch (JSONException e) { 
				e.printStackTrace();
			} 
			return sbHtml.toString();
			
		}
		//
		StringBuilder sb=new StringBuilder();
		sb.append("<font color='#FFA500'>11月1日（星期四） 南京</font> <br/> <br/> "); 
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;晴，最高温度17度，最低温度9度 <br/> <br/> "); 
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;东风3-4级别。"); 
		sb.append(" ");  
		return sb.toString();
	}//end of genWeatherInfo
	 
	
	public static String genBus(JSONObject data){
		if(data!=null){
			StringBuilder sbHtml=new StringBuilder();
			try {
				JSONArray jsonArray=data.getJSONArray("listOfXmMeetingBus");
				sbHtml.append("<table style='font-size:30px;' width='100%'>"); 
				for(int i=0;i<jsonArray.length();i++){
					JSONObject  jsonObject=jsonArray.getJSONObject(i); 
					String xmmbCard=jsonObject.getString("xmmbCard");
					String xmmbCode=jsonObject.getString("xmmbCode");
					String xmmbDesc=jsonObject.getString("xmmbDesc"); 
					sbHtml.append("<tr  >");
					sbHtml.append("		<td style='color:#FFA500' >"+xmmbCode+"："+xmmbCard+"</td>");  
					sbHtml.append("</tr>");  
					sbHtml.append("<tr>");
					sbHtml.append("		<td>"+xmmbDesc+"</td>");
					sbHtml.append("</tr>");     
					if(i!=jsonArray.length()-1){ 
						sbHtml.append("<tr  >");
						sbHtml.append("		<td >&nbsp;</td>");  
						sbHtml.append("</tr>");  
					}
				}
				sbHtml.append("</table>"); 
				
			} catch (JSONException e) { 
				e.printStackTrace();
			} 
			return sbHtml.toString();
			
		}
		StringBuilder sb=new StringBuilder();
		
		sb.append("<table style='font-size:30px;' width='100%'>"); 
		sb.append("<tr  >");
		sb.append("		<td style='color:#FFA500' >一号车：苏A00068</td>");  
		sb.append("</tr>");  
		sb.append("<tr>");
		sb.append("		<td>上海公司：冯  军  阮前途  黄良宝  王  路</td>");
		sb.append("</tr>"); 
		sb.append("<tr>");
		sb.append("		<td>江苏公司：单业才  胡玉海  钱朝阳  黄志高</td>");
		sb.append("</tr>");   
		//
		sb.append("<tr  >");
		sb.append("		<td >&nbsp;</td>");  
		sb.append("</tr>");  

		sb.append("<tr  >");
		sb.append("		<td style='color:#FFA500' >二号车:苏A00021</td>");  
		sb.append("</tr>");  
		sb.append("<tr>");
		sb.append("		<td>上海公司：范永根  沈兆新 车申刚 史济康 </td>");
		sb.append("</tr>"); 
		sb.append("<tr>");
		sb.append("		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;吴英姿  周  简 王  伟 应志玮</td>");
		sb.append("</tr>"); 
		sb.append("<tr>");
		sb.append("		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;陈红兵  陈  杰 牛  凯 </td>");
		sb.append("</tr>"); 
		sb.append("<tr>");
		sb.append("		<td>江苏公司：吴  俊  季  强</td>");
		sb.append("</tr>"); 
		sb.append("</table>"); 
		return sb.toString(); 
	}//end of genBus
	
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String genContact(JSONObject data){
		if(null!=data){
			StringBuilder sbHtml=new StringBuilder();
			try {
				JSONArray jsonArray=data.getJSONArray("listOfXmMeetingContact"); 
				sbHtml.append("<table style='font-size:30px;' width='100%'>"); 
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonContact=jsonArray.getJSONObject(i);
					String xmmcDescription=jsonContact.getString("xmmcDescription"); 
					sbHtml.append("<tr  >");
					sbHtml.append("		<td >"+xmmcDescription+"</td>");  
					sbHtml.append("</tr>");    
				} 
				sbHtml.append("</table>"); 
			} catch (JSONException e) { 
				e.printStackTrace();
			} 
			return sbHtml.toString();
		}
		//
		StringBuilder sb=new StringBuilder(); 
		sb.append("<font color='#FFA500'>江苏省电力公司接待服务联系人 </font> <br/> <br/> "); 
		sb.append("吴 骏&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 办公室主任   <br/> <br/> "); 
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电话: 15150556655"); 
		sb.append(" ");  
		return sb.toString();
		
		
	}//end of genContact
	
	
}
