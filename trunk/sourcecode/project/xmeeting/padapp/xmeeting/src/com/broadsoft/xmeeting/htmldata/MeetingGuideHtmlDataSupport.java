package com.broadsoft.xmeeting.htmldata;

import org.json.JSONObject;

public class MeetingGuideHtmlDataSupport {

	
	
	public static String genSchedule(JSONObject data){
		StringBuilder sb=new StringBuilder();
		sb.append("<table style='font-size:30px;' width='100%'>");
		for(int i=0;i<5;i++){
			sb.append("<tr>");
			sb.append("		<td colspn='2' style='color:red;'>第"+(i+1)+"天(2012-12-2"+i+")</td>");  
			sb.append("</tr>"); 
			
			for(int j=0;j<5;j++){
				sb.append("<tr>");
				sb.append("		<td  >08:15</td><td>签到 </td>");  
				sb.append("</tr>"); 
				sb.append("<tr>");
				sb.append("		<td  >09:00</td><td>欢迎致词</td>");  
				sb.append("</tr>"); 
			} 
		}
		sb.append("</table>"); 
		return sb.toString();
		
		
	}
	
	

	public static String genMemberInfo(JSONObject data){
		StringBuilder sb=new StringBuilder();
		sb.append("<table style='font-size:30px;' width='100%'>"); 
		sb.append("<tr style='background-color:gray;'>");
		sb.append("		<td >编号</td>");  
		sb.append("		<td >名字</td>");  
		sb.append("		<td >职务</td>");  
		sb.append("		<td >联系方式</td>");  
		sb.append("</tr>"); 
			
		for(int j=0;j<15;j++){
			sb.append("<tr>");
			sb.append("		<td  >"+(j+1)+"</td>");  
			sb.append("		<td  >张三</td>");  
			sb.append("		<td  >经理</td>");  
			sb.append("		<td  >13798104153</td>");   
			sb.append("</tr>");  
		}  
		sb.append("</table>"); 
		return sb.toString();
		
		
	}
	
	
	public static String genWeatherInfo(JSONObject data){
		StringBuilder sb=new StringBuilder();
		sb.append("<table style='font-size:30px;' width='100%'> "); 
		sb.append("<tr  style='background-color:gray;'>");
		sb.append("		<td colspan='2'>日期</td>");  
		sb.append("		<td >天气现象</td>");  
		sb.append("		<td >气温</td>");  
		sb.append("		<td >风向</td>");  
		sb.append("		<td >风力</td>");  
		sb.append("</tr>"); 
			
		for(int j=0;j<5;j++){
			sb.append("<tr>");
			sb.append("		<td  rowspan='2'>13日星期一</td>");  
			sb.append("		<td  >白天</td>");  
			sb.append("		<td  >晴</td>");  
			sb.append("		<td  >高温 33℃</td>");  
			sb.append("		<td  >南风</td>"); 
			sb.append("		<td  >3-4级 </td>");    
			sb.append("</tr>");  
			sb.append("<tr>"); 
			sb.append("		<td  >夜间</td>");  
			sb.append("		<td  >多云</td>");  
			sb.append("		<td  >低温 18℃</td>");  
			sb.append("		<td  >东南风</td>"); 
			sb.append("		<td  >3-4级 </td>");    
			sb.append("</tr>");  
		}  
		sb.append("</table>"); 
		return sb.toString();
		
		
	}
	
	
	public static String genBus(JSONObject data){
		StringBuilder sb=new StringBuilder();
		sb.append("<table style='font-size:30px;' width='100%'>");
		for(int i=0;i<5;i++){
			sb.append("<tr style='background-color:gray;'>");
			sb.append("		<td colspn='2'>"+(i+1)+"号车</td>");  
			sb.append("</tr>"); 
			 
			sb.append("<tr>");
			sb.append("		<td  >车牌号</td><td>苏A000068 </td>");  
			sb.append("</tr>"); 
			sb.append("<tr>");
			sb.append("		<td  rowspan='2'>人员安排</td><td>上海公司:冯    军     阮前途   黄良宝   王  路  </td>");
			sb.append("</tr>"); 
			sb.append("<tr>");
			sb.append("		                            <td> 江苏公司:单业才    胡玉海   签朝阳   黄志高</td>");
			sb.append("</tr>");  
		}
		sb.append("</table>"); 
		return sb.toString();
		
		
	}
	
	public static String genContact(JSONObject data){
		StringBuilder sb=new StringBuilder();
		sb.append("<br/><br/><br/> "); 
		sb.append("江苏省电力公司接待服务联系人  <br/>  "); 
		sb.append("吴 骏     办公室主任   <br/>  "); 
		sb.append("电话: 15150556655"); 
		sb.append(" ");  
		return sb.toString();
		
		
	}
	
	
}
