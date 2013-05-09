<%@page import="java.net.URLDecoder"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="org.hibernate.SQLQuery"%>
<%@page import="com.founder.sipbus.syweb.au.po.AuRolesAuthorities"%>
<%@page import="com.founder.sipbus.syweb.au.po.AuFunction"%>
<%@page import="com.founder.sipbus.syweb.au.po.AuAuthorities"%>
<%@page import="com.founder.sipbus.syweb.au.po.AuMenu"%>
<%@page import="com.founder.sipbus.syweb.au.dao.AuAuthoritiesDaoImpl"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.Session"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>

<head>
	<title>功能列表</title>

	<link rel="StyleSheet" href="dtree.css" type="text/css" />
	<script type="text/javascript" src="dtree.js"></script>

</head>

<body>
  <%
  
  	request.setCharacterEncoding("UTF-8");
  	String authId=request.getParameter("authId");
  	if(StringUtils.isBlank(authId)||"null".equals(authId.trim())){
  		out.println("请选择权限！");
  		return;
  	}
  	
  	authId=URLDecoder.decode(authId,"UTF-8");
  	
  	ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()); 
  	SessionFactory o=(SessionFactory)ctx.getBean("sessionFactory");
  	AuAuthoritiesDaoImpl authoritiesDao=(AuAuthoritiesDaoImpl)ctx.getBean("auAuthoritiesDao");
  	Session se=o.openSession();
  	
  	List<AuMenu> ml=se.createQuery("from AuMenu where authorityId='"+authId+"'").list();
  	
  //	AuMenu  aumenu=(AuMenu)se.createQuery("from AuMenu where authorityId='"+authId+"'").uniqueResult();
  	AuAuthorities currentAu= (AuAuthorities)se.load(AuAuthorities.class,authId);//authoritiesDao.findById();
  	List<AuFunction> funList= se.createQuery("from AuFunction where auAuthorities.authorityId='"+authId+"' order by functionId asc").list();//currentAu.getAuFunctionList();
  	
  	
  	//IAuAuthoritiesDao authoritiesDao=(AuAuthoritiesDaoImpl)ctx.getBean("auAuthoritiesDao");
  	
  	//List<AuAuthorities> authList=authoritiesDao.findAll();
  	//List<AuAuthorities> authList=new ArrayList();
  	//System.out.println(authList);
   %>

</body>

<table>
	<tr>
		<td>当前权限名称:<%=currentAu.getAuthorityName()+"/"+currentAu.getAuthorityId()%></td>
	</tr>

<tr>
<td> 
<p> 该权限对应的URL列表(AU_FUNCTION)</p>
<table border="1" id="funcListTable" name="funcListTable">
<tr>
<%--	<td>FUNCTIONID</td>--%>
	<td>功能名称</td>
	<td>URL</td>
	<td>操作类型</td>
	<td>描述</td>
	<td>域</td>
	<!-- <td>前台权限设置</td> -->
	<td style="width:50px;">删除</td>
	<td style="width:50px;"><a href="#" onclick="addRow();">新增</a></td>
</tr>
<%for(AuFunction f:funList) {%>
<% 
%>
<tr> 
<%--<td style="width:150px;"><%=f.getFunctionId() %></td>--%>
<td  ><%=f.getFunctionname() %></td>
<td  ><%=f.getUrl() %></td>
<td><%=f.getUrlMethod() %></td>
<td  ><%=f.getDescpt() %></td>
<td><%
	if("1".equals(f.getDomainName())){
		out.print("系统管理");
	}else if("2".equals(f.getDomainName())){
		out.print("机务管理");
	}else if("3".equals(f.getDomainName())){
		out.print("排班调度");
	}else if("5".equals(f.getDomainName())){
		out.print("长连接");
	}
	 %></td>
<td colspan=2><a href="#" onclick="delFunc('<%=f.getFunctionId() %>');">删除</a></td>
</tr>
<%} %>
</table>
</td>
</Tr>
</table>

<p>--------------------------------------------------------------------------------------------------------------------------------</p>

<p>
对应菜单详细信息：
<%if(null!=ml&&ml.size()>0) {
		for(AuMenu aumenu:ml){%>
	<%="<br/>菜单链接:"+aumenu.getMenuAction()%>
	<%="<br/>菜单Domain："+aumenu.getDomainName()%> 
	<p/>
<%}}else {%>
	无
<%} %>
	
</p>
<p>--------------------------------------------------------------------------------------------------------------------------------</p>

<a href="#" onclick="delAuth();">删除该权限</a>


<% 


//AuAuthorities parentAuth= (AuAuthorities)se.createQuery("from AuAuthorities where authorityId='"+currentAu.getParentId()+"'").uniqueResult();

List<AuAuthorities> subAuthlist= se.createQuery("from AuAuthorities where parentId='"+authId+"'").list();

%>
<table border="1" bordercolor="green" style="width:100%;">
  <tr>
    <td style="width: 250px">父权限ID </td>
    <td style="width: 250px">权限ID</td>
    <td style="width: 120px">权限名称</td>
    <td style="width: 50px">排序</td>
    <td style="width: 200px">生成权限及功能</td>
  </tr>
  <tr>
    <td style="width: 250px"><input type="text"  style="width: 250px"   readonly="readonly" value="<%=authId %>"/></td>
    <td style="width: 250px"><input id="id1" type="text"  style="width:250px"  value="<%=authId+"_" %>"/></td>
    <td  style="width: 120px"><input id="name1" style="width:120px"   type="text"  value=""/></td>
    <td  style="width: 50px"><input id="autOrder1" type="text"  style="width:50px"  value="<%=subAuthlist.size()+1 %>"/></td>
    <td style="width: 200px"><a href="#" onclick="excute(1);">添加子权限</a></td>
  </tr>
</table>




<p/>

<p>--------------------------------------------------------------------------------------------------------------------------------</p>

<table border="1" id="funcListTablse1" name="funcListTablse1">
<tr> 
	<td>已赋权角色</td>
	<td style="width:50px;"><a href="#" onclick="delAllAuRolesAuthorities('<%=authId %>');">全部删除</a></td>
</tr>
<%
String hql="from AuRolesAuthorities where authorityId='"+authId+"'" ;

String sql="select name from sys_role where guid=?";

List<AuRolesAuthorities> aRAList=se.createQuery(hql).list();
//List<AuRolesAuthorities> aRAList=bizDtlDao.findByHqlNoEntityType(hql);


for(AuRolesAuthorities f:aRAList) {
	SQLQuery squery=se.createSQLQuery(sql);
	squery.setString(0,f.getRoleId());
	String rolename=(String)squery.uniqueResult();
		
%>
<tr> 
<td style="width:150px;"><%=rolename+"--"+f.getRoleId() %></td>
<td colspan=2><a href="#" onclick="delAuRolesAuthorities('<%=f.getRelId() %>');">删除</a></td>
</tr>
<%} %>
</table>





<script>
	function delFunc(funcId){
		if(window.confirm('是否要删除该权限的功能！')){
			get_nav('service.jsp?type=delfunc&funcId='+funcId);
		}
	}
	
	
	function delAllAuRolesAuthorities(authId){
		if(window.confirm('是否要取消该角色的该权限！')){
			get_nav('service.jsp?type=delAllAuRolesAuthorities&authId='+authId);
		}
	}
	
	function delAuRolesAuthorities(funcId){
		if(window.confirm('是否要取消该角色的该权限！')){
			get_nav('service.jsp?type=delAuRolesAuthorities&funcId='+funcId);
		}
	}
	
	
function saveFunc(index){
		var func_name=document.getElementById('func_name'+index).value;
		var url=document.getElementById('url'+index).value;
		var url_method=document.getElementById('url_method'+index).value;
		var descpt=document.getElementById('descpt'+index).value;
		var domain=document.getElementById('domain'+index).value;

		if(url_method==''){
			alert('请选择操作类型');
			return;
		}
		if(url==''){
			alert('请输入URL');
			return;
		}
		if(domain==''){
			alert('请选择域');
			return;
		}
		
		var savUrl="service.jsp?type=savefunc&authId=<%=authId%>&func_name="+func_name+"&domain="+domain
				+"&url_method="+url_method+"&url="+url+"&descpt="+descpt;
		var ajax = InitAjax();
		ajax.open("GET", encodeURI(encodeURI(savUrl)), true);
		ajax.onreadystatechange = function() {
			if (ajax.readyState == 4 && ajax.status == 200) {
				window.top.location.href=encodeURI(encodeURI("auth_func.jsp?authId=<%=authId%>"));
			}
		}
		ajax.send(null);
}


function delAuth(){
		var savUrl="service.jsp?type=delAuth&authId=<%=authId%>";
		var ajax = InitAjax();
		ajax.open("GET", encodeURI(encodeURI(savUrl)), true);
		ajax.onreadystatechange = function() {
			if (ajax.readyState == 4 && ajax.status == 200) {
				if('success'==ajax.responseText){
					//window.top.location.reload();
					window.top.location.href=encodeURI(encodeURI("auth_func.jsp?authId=<%=currentAu.getParentId()%>"));
				}else{
					alert('删除失败，请先删除子权限、对应功能和取消赋权');
				}
			}
		}
		ajax.send(null);
}

var i=0;
function   addRow() {
	var   table   =   document.getElementById("funcListTable");   //   tableId   是你表格的id 
	var   tr   =   table.insertRow(); 
	//var   td   =   tr.insertCell(); 
	var   td2   =   tr.insertCell(); 
	var   td3   =   tr.insertCell(); 
	var   td4  =   tr.insertCell(); 
	var   td5   =   tr.insertCell(); 
	var   td6   =   tr.insertCell(); 
	var   td7   =   tr.insertCell(); 
	

    
    
	//td.id="dnytd_"+i;
	//td.innerHTML="<input id='func_name"+i+
	//"' type='text' style='width:260px;' value='<%=authId.indexOf("_")!=-1?authId.substring(0,authId.lastIndexOf("_")):""%>_**_BTN'></input>";
	
	td2.innerHTML="<input id='func_name"+i
	+"' type='text' value=''></input>";
	td2.id="dnytd2_"+i;
	
	td3.innerHTML="<input id='url"+i
	+"' type='text' style='width:200px;' value=''></input>";
	td3.id="dnytd3_"+i;
	
	td4.innerHTML= 
	"<select id='url_method"+i
	+"'  style='width:60px;' >"
	+"<option value='' selected>--请选择--</option>"
	+"<option value='POST'>POST</option>"
	+"<option value='GET'>GET</option>"
	+"<option value='DELETE'>DELETE</option>" 
	+"<option value='PUT'>PUT</option>"
	+"</select>";
	td4.id="dnytd4_"+i;

	td5.innerHTML="<input id='descpt"+i
	+"' type='text'  style='width:150px;'  value=''></input>";
	td5.id="dnytd5_"+i;

	td6.innerHTML="<select id='domain"+i
	+"'  style='width:60px;' >"
	+"<option value='' selected>--请选择--</option>"
	+"<option value='1'>系统管理</option>"
	+"<option value='2'>机务管理</option>"
	+"<option value='3'>排班调度</option>" 
	+"<option value='5'>长连接</option>"
	+"</select>";
	td6.id="dnytd6_"+i;
	
	
	td7.innerHTML="<a href='#' onclick='saveFunc("+i+");'>保存</a>";
	td7.id="dnytd3_"+i;
	i++;
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
		ajax.open("GET", url, true);
		ajax.onreadystatechange = function() {
			if (ajax.readyState == 4 && ajax.status == 200) {
				window.location.reload();
			}
		};
		ajax.send(null);
	}
	
	
	function excute(index){
		//alert(document.getElementById('name'+index).value);
		//alert(document.getElementById('id'+index).value);
		//alert(document.getElementById('method'+index).value);
		var authId=document.getElementById('id'+index).value;
		var authName=document.getElementById('name'+index).value;
		var autOrder=document.getElementById('autOrder'+index).value;
		
		add_func_nav("service.jsp?type=genAuthAndFunc&authId="+authId+"&authName="+authName+"&pAuthId=<%=authId%>&autOrder="+autOrder);
		
		//window.top.location.reload();
	
	}
	function add_func_nav(url) {
		var ajax = InitAjax();
		ajax.open("GET", encodeURI(encodeURI(url)), true);
		ajax.onreadystatechange = function() {
			if (ajax.readyState == 4 && ajax.status == 200) {
				if('authIdExists'==ajax.responseText){
					alert('功能权限已经存在！');
				}else if('success'==ajax.responseText){
					window.top.location.href=encodeURI(encodeURI("auth_func.jsp?authId=<%=authId%>"));
				}else{
					alert('添加失败，请查看控制台输出！');
				}
			}
		};
		ajax.send(null);
	}

</script>
<%
  	se.close(); %>
</html>