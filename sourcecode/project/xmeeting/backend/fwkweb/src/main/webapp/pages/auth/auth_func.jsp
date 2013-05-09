<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.founder.sipbus.syweb.au.po.AuAuthorities"%>
<%@page import="com.founder.sipbus.syweb.au.dao.AuAuthoritiesDaoImpl"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<title>权限管理</title>
	<link rel="StyleSheet" href="dtree.css" type="text/css" />
	<script type="text/javascript" src="dtree.js"></script>
</head>
<body>
  <%
  	ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()); 
    AuAuthoritiesDaoImpl authoritiesDao=(AuAuthoritiesDaoImpl)ctx.getBean("auAuthoritiesDao");
  	
  	List<AuAuthorities> authList=//authoritiesDao.findAll();
  	authoritiesDao.findByHql("from AuAuthorities where delFlag=0 order by autOrder asc ");
  	//List<AuAuthorities> authList=new ArrayList();
  	//System.out.println(authList);
   %>
<div class="dtree">
	<p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>
	<!--
		<p><a href="javascript: c.openAll();">open all</a> | <a href="javascript: c.closeAll();">close all</a></p>
	-->
	<script type="text/javascript">
		<!--
		d = new dTree('d');
		d.add(0,-1,'权限列表','','','','','img/imgfolder.gif');
		<%
			for(AuAuthorities a:authList){
			if("0".equals(a.getParentId())){
				out.print("d.add(\""+a.getAuthorityId()+"\",0,'"+a.getAuthorityName()+"',\"javascript:showDesc('"+a.getAuthorityId()+"')\");");
			}else{
				out.print("d.add(\""+a.getAuthorityId()+"\",\""+a.getParentId()+"\",'"+a.getAuthorityName()+"',\"javascript:showDesc('"+a.getAuthorityId()+"')\");");
			}
				%>
		<%}%>
		
		c=new dTree('c');
		c.add(0,-1,'权限列表','','','','','img/imgfolder.gif');
		c.add("a",0,'Node 1','example01.html');
		c.add("b",0,'Node 2','example01.html');
		c.add(3,"a",'Node 1.1','example01.html');
		c.add(4,0,'Node 3','example01.html');
		c.add(5,3,'Node 1.1.1','example01.html');
		c.add(6,5,'Node 1.1.1.1','example01.html');
		c.add(7,0,'Node 4','example01.html');
		c.add(8,"a",'Node 1.2',"javascript:showDesc('xxxxxxx')");
		c.add(9,0,'My Pictures','example01.html','Pictures I\'ve taken over the years','','','img/imgfolder.gif');
		c.add(10,9,'The trip to Iceland','example01.html','Pictures of Gullfoss and Geysir');
		c.add(11,9,'Mom\'s birthday','example01.html');
		c.add(12,0,'Recycle Bin','example01.html','','','img/trash.gif');

		
		
		function showDesc(authId){
			
			document.getElementById('funcList').src='func_list.jsp?authId='+encodeURI(encodeURI(authId));
		}
		
		//-->
	</script>
</div>
<table border="0" >

<%
	String authId=request.getParameter("authId");
%>
<tr>
<td width="300"  valign="top"><script>document.write(d);</script></td>
<td valign="top" width="1000">
<iframe src="func_list.jsp?authId=<%=authId%>" frameborder="0" height="800" width="100%"  scrolling="auto" noresize="noresize"  id="funcList" name="funcList"/>
</td>
</tr>
</table> 

</body>

<script>
<%
	if(StringUtils.isNotBlank(authId)){
		%>
		document.getElementById('funcList').src='func_list.jsp?authId=encodeURI(encodeURI(<%=authId%>))';
		//showDesc('<%=authId%>');
		<%
	}
%>
</script>
</html>