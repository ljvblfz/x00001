<%@page import="java.util.Date"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.founder.sipbus.syweb.au.dao.AuFunctionDaoImpl"%>
<%@page import="com.founder.sipbus.syweb.au.dao.AuRolesAuthoritiesDaoImpl"%>
<%@page import="com.founder.sipbus.syweb.au.po.AuFunction"%>
<%@page import="com.founder.sipbus.syweb.au.po.AuAuthorities"%>
<%@page import="com.founder.sipbus.syweb.au.dao.AuAuthoritiesDaoImpl"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.Session"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URLDecoder"%>
<%
		ApplicationContext ctx=WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()); 
		AuAuthoritiesDaoImpl auAuthoritiesDao=(AuAuthoritiesDaoImpl)ctx.getBean("auAuthoritiesDao");
		AuRolesAuthoritiesDaoImpl auRolesAuthoritiesDao=(AuRolesAuthoritiesDaoImpl)ctx.getBean("auRolesAuthoritiesDao");
		AuFunctionDaoImpl auFunctionDao=(AuFunctionDaoImpl)ctx.getBean("auFunctionDao");
	  	SessionFactory o=(SessionFactory)ctx.getBean("sessionFactory");
	  	Session se=o.openSession();
		String type=request.getParameter("type");
		String funcId=request.getParameter("funcId");
		String authId=request.getParameter("authId");
		if(StringUtils.isNotBlank(authId))
	  		authId=URLDecoder.decode(authId,"UTF-8");
		if(StringUtils.isNotBlank(funcId))
	  		funcId=URLDecoder.decode(funcId,"UTF-8");
		if("delAllAuRolesAuthorities".equals(type)){
			String h="delete AuRolesAuthorities where authorityId='"+authId+"'";
				Transaction ta= se.beginTransaction();
				try{
					se.createQuery(h).executeUpdate();
					ta.commit();
				}catch(Exception ae){
					ta.rollback();
				 	ae.printStackTrace();
				 	return;
				}
		}if("delAuRolesAuthorities".equals(type)){
			auRolesAuthoritiesDao.delete(funcId);
		}else if("delfunc".equals(type)){
			auFunctionDao.deletePhysics(funcId);
		}else if("savefunc".equals(type)){
			String func_name=request.getParameter("func_name");
			if(StringUtils.isNotBlank(func_name))
				func_name=URLDecoder.decode(func_name,"UTF-8");
			String url=request.getParameter("url");
			if(StringUtils.isNotBlank(url))
				url=URLDecoder.decode(url,"UTF-8");
			String url_method=request.getParameter("url_method");
			if(StringUtils.isNotBlank(url_method))
				url_method=URLDecoder.decode(url_method,"UTF-8");
			String descpt=request.getParameter("descpt");
			if(StringUtils.isNotBlank(descpt))
				descpt=URLDecoder.decode(descpt,"UTF-8");
			String domain=request.getParameter("domain");
			if(StringUtils.isNotBlank(domain))
				domain=URLDecoder.decode(domain,"UTF-8");
			
			AuAuthorities au=auAuthoritiesDao.findById(authId);
			AuFunction nf=new AuFunction();
			nf.setUrl(url);
			nf.setUrlMethod(url_method);
			nf.setDescpt(descpt);
			nf.setDomainName(domain);
			nf.setFunctionname(func_name);
			nf.setAuAuthorities(au);
			nf.setCreateBy("system");
			nf.setUpdateBy("system");
			nf.setCreateDt(new Date());
			nf.setUpdateDt(new Date());
			out.clear();
			out.print(auFunctionDao.insert(nf).getFunctionId());
		}else if("delAuth".equals(type)){
			if(se.createQuery("from AuAuthorities where parentId='"+authId+"'").list().size()>0){
				out.clear();
				out.print("error");
			}else if(se.createQuery("from AuFunction where auAuthorities.id='"+authId+"'").list().size()>0){
				out.clear();
				out.print("error");
			}
			else{
				try{
					//auAuthoritiesDao.delete(authId);
					
					auAuthoritiesDao.deletePhysics(authId);
					out.clear();
					out.print("success");
				}catch(Exception e){
					e.printStackTrace();	
					out.clear();
					out.print("error");
				}
			}
		}else if("genAuthAndFunc".equals(type)){
			String authName=request.getParameter("authName");
			String pAuthId=request.getParameter("pAuthId");
			String autOrder=request.getParameter("autOrder"); 
			if(StringUtils.isNotBlank(pAuthId))
				pAuthId=URLDecoder.decode(pAuthId,"utf-8");

			if(StringUtils.isNotBlank(authName))
				authName=URLDecoder.decode(authName,"utf-8");
			if(auAuthoritiesDao.findById(authId)!=null){
				out.clear();
				out.print("authIdExists");
			}else{
				Transaction ta= se.beginTransaction();
				try{
					AuAuthorities au=new AuAuthorities();
					au.setAuthorityId(authId);
					au.setParentId(pAuthId);
					au.setAuthorityName(authName);
					au.setAutOrder(Short.valueOf(autOrder));
					au.setEnabled("1");
					auAuthoritiesDao.insert(au);
					//AuFunction naf=new AuFunction();
					//naf.setAuAuthorities(au);
					//naf.setFunctionname(authId+"_BTN");
					//naf.setModuleKey(funcMethod);
					//auFunctionDao.insert(naf);
					ta.commit();
				}catch(Exception ae){
					ta.rollback();
				 	ae.printStackTrace();
				 	return;
				}
				out.clear();
				out.print("success");
			}
			
		
		}
		
		se.close();
%>