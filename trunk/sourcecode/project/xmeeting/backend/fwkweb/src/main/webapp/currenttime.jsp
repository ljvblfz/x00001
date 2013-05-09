<%@page import="com.founder.sipbus.syweb.au.util.*"%>
<%@page import="com.founder.sipbus.syweb.au.po.*"%>

 

<%
	String format=request.getParameter("format");
	if(org.apache.commons.lang.StringUtils.isBlank(format)){
		out.print(System.currentTimeMillis());
	}else{
		out.print(com.founder.sipbus.common.util.TimeUtils.date2String(format, new java.util.Date()));
	}
%>


<% 
// ISSOManager ssoManager=(ISSOManager)MemcachedUtil.getMemCachedClient().get(SsoUtil.getSSOManagerFromSession(request));
// SysUser sysUser= ssoManager.getSysUser();
// String userid=sysUser.getUserid();
// out.print("User Id:  "+userid);
%>
