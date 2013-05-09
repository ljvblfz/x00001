<%@page import="com.founder.sipbus.syweb.au.po.AuAuthorities"%>
<%@page import="com.founder.sipbus.syweb.au.dao.AuFunctionDaoImpl"%>
<%@page import="com.founder.sipbus.syweb.au.dao.AuAuthoritiesDaoImpl"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.Session"%>
<%
		ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()); 
		AuAuthoritiesDaoImpl auAuthoritiesDao=(AuAuthoritiesDaoImpl)ctx.getBean("auAuthoritiesDao");
		AuFunctionDaoImpl auFunctionDao=(AuFunctionDaoImpl)ctx.getBean("auFunctionDao");
	  	SessionFactory o=(SessionFactory)ctx.getBean("sessionFactory");
	  	Session se=o.openSession();
		String type=request.getParameter("type");
		String funcId=request.getParameter("funcId");
		String authId=request.getParameter("authId");
		List<AuAuthorities> subAuthlist= se.createQuery("from AuAuthorities where parentId='"+authId+"'").list();
		 
		se.close();
%>

已有子权限
<table border="1" bordercolor="green">
	<tr>
		<td>父权限ID</td>
		<td>权限ID</td>
		<td>权限名称</td>
		<td>权限排序</td>
		<td>是否有效</td>
	</tr>
<%for(AuAuthorities a: subAuthlist){%>
	<tr>
		<td><%=a.getParentId() %></td>
		<td><%=a.getAuthorityId() %></td>
		<td><%=a.getAuthorityName() %></td>
		<td><%=a.getAutOrder() %></td>
		<td><%=a.getEnabled() %></td>
	</tr>
<%} %>

</table>
<br/>
<br/>
<br/>
增加子权限


<table border="1" bordercolor="green">
  <tr>
    <th>父权限ID </th>
    <th>权限名称</th>
    <th>权限ID</th>
    <th width="400">后台方法</th>
    <th>生成权限及功能</th>
  </tr>
<%--  <tr>--%>
<%--    <th><input type="text" readonly="readonly" value="<%=authId %>"/></th>--%>
<%--    <th><input id="name1" type="text"  value="查询***"/></th>--%>
<%--    <th><input id="id1" type="text"  style="width: 260"  value="<%=authId+"_SEARCH" %>"/></th>--%>
<%--    <th><input id="method1" type="text"  style="width: 400" value="<%="com.founder.trust."+authId.substring(0,authId.indexOf("_")).toLowerCase()+".services.*.*()"%>"/></th>--%>
<%--    <th><a href="#" onclick="excute(1);">执行</a></th>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <th><input type="text" readonly="readonly" value="<%=authId %>"/></th>--%>
<%--    <th><input id="name2" type="text"  value="高级查询***"/></th>--%>
<%--    <th><input id="id2" type="text"  style="width: 260"  value="<%=authId+"_ADVSEARCH" %>"/></th>--%>
<%--    <th><input id="method2" type="text"  style="width: 400" value="<%="com.founder.trust."+authId.substring(0,authId.indexOf("_")).toLowerCase()+".services.*.*()"%>"/></th>--%>
<%--    <th><a href="#" onclick="excute(2);">执行</a></th>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <th><input type="text" readonly="readonly" value="<%=authId %>"/></th>--%>
<%--    <th><input id="name6" type="text"  value="新增***"/></th>--%>
<%--    <th><input id="id6" type="text"  style="width: 260"  value="<%=authId+"_NEW" %>"/></th>--%>
<%--    <th><input id="method6" type="text"  style="width: 400" value="<%="com.founder.trust."+authId.substring(0,authId.indexOf("_")).toLowerCase()+".services.*.*()"%>"/></th>--%>
<%--    <th><a href="#" onclick="excute(6);">执行</a></th>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <th><input type="text" readonly="readonly" value="<%=authId %>"/></th>--%>
<%--    <th><input id="name7" type="text"  value="修改***"/></th>--%>
<%--    <th><input id="id7" type="text"  style="width: 260"  value="<%=authId+"_UPDATE" %>"/></th>--%>
<%--    <th><input id="method7" type="text"  style="width: 400" value="<%="com.founder.trust."+authId.substring(0,authId.indexOf("_")).toLowerCase()+".services.*.*()"%>"/></th>--%>
<%--    <th><a href="#" onclick="excute(7);">执行</a></th>--%>
<%--  </tr>--%>
<%--  <tr>--%>
<%--    <th><input type="text" readonly="readonly" value="<%=authId %>"/></th>--%>
<%--    <th><input id="name8" type="text"  value="删除***"/></th>--%>
<%--    <th><input id="id8" type="text"  style="width: 260"  value="<%=authId+"_DELETE" %>"/></th>--%>
<%--    <th><input id="method8" type="text"  style="width: 400" value="<%="com.founder.trust."+authId.substring(0,authId.indexOf("_")).toLowerCase()+".services.*.*()"%>"/></th>--%>
<%--    <th><a href="#" onclick="excute(8);">执行</a></th>--%>
<%--  </tr>--%>
</table>

<p>
	<a href="#" onclick="window.opener.top.location.reload();window.close();">关闭并刷新父页面</a>
</p>
<script>
	function excute(index){
		//alert(document.getElementById('name'+index).value);
		//alert(document.getElementById('id'+index).value);
		//alert(document.getElementById('method'+index).value);
		var authId=document.getElementById('id'+index).value;
		var authName=document.getElementById('name'+index).value;
		var funcMethod=document.getElementById('method'+index).value;
		
		get_nav("service.jsp?type=genAuthAndFunc&authId="+authId+"&authName="+authName+"&pAuthId=<%=authId%>&funcMethod="+funcMethod);
		
		window.location.reload();
	
	}
	
	
	function InitAjax() {
		var ajax = false;
		try {
			ajax = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				ajax = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				ajax = false;
			}
		}
		if (!ajax && typeof XMLHttpRequest != 'undefined') {
			ajax = new XMLHttpRequest();
		}
		return ajax;
	}

	function get_nav(url) {
		var ajax = InitAjax();
		ajax.open("GET", encodeURI(encodeURI(url)), true);
		ajax.onreadystatechange = function() {
			if (ajax.readyState == 4 && ajax.status == 200) {
				if('authIdExists'==ajax.responseText){
					alert('功能权限已经存在！');
				}else if('success'==ajax.responseText){
					window.location.reload();
				}else{
					alert('添加失败，请查看控制台输出！');
				}
			}
		}
		ajax.send(null);
	}
</script>








