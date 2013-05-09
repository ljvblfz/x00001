package com.founder.sipbus.syweb.au.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.Properties;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.exception.FounderException;
import com.founder.sipbus.common.util.ESDecode;
import com.founder.sipbus.common.util.GuidUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.SysOrgmemberDaoImpl;
import com.founder.sipbus.syweb.au.dao.SysUserDaoImpl;
import com.founder.sipbus.syweb.au.po.SysUser;
import com.founder.sipbus.syweb.au.util.ISSOManager;
import com.founder.sipbus.syweb.au.util.SSOManager;
import com.founder.sipbus.syweb.au.util.SsoUtil;


/***
 * 
 * @author Steven
 * http://www.lifeba.org
 */
//@Path("bizFeeType")
@Component
@Scope(value="prototype")
@RestletResource(urls="/login")
public class LoginResource extends SyBaseResource{
	
	private static Log log=LogFactory.getLog(LoginResource.class);
	
	private SysUserDaoImpl sysUserDao;

	private SysOrgmemberDaoImpl sysOrgmemberDao;
	
	public void setSysOrgmemberDao(SysOrgmemberDaoImpl sysOrgmemberDao) {
		this.sysOrgmemberDao = sysOrgmemberDao;
	}

	@Properties(name="login.ipaddr")
	public String loginIpAddr;
 
	public void setLoginIpAddr(String loginIpAddr) {
		this.loginIpAddr = loginIpAddr;
	}

	public void setSysUserDao(SysUserDaoImpl sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	@Override
    protected void doInit() throws ResourceException {
//		if(getRequestAttributes()!=null){
//			bizTypeId =  (String) getRequestAttributes().get("bizTypeId");
//		}
	}
	
	@Get
	public Representation get(Representation entity) {
 		return new StringRepresentation(getLoginUser()==null?"":getLoginUser().getUserid());   
	}
	
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	@Delete
	public Representation delete() {
		// 删除Cookie中的SSO_ID
        SsoUtil.removeSsoIdFromCookie(getHttpRequest(),getHttpResponse());
        try{
	        ISSOManager sSOManager = getSSOManager(); 
	        if(sSOManager != null){
	            sSOManager.remove();
	        }
        }catch(Exception ae){
        	if(log.isDebugEnabled()){
        		log.debug(ae.getMessage());
        	}
        }
		//用户注销（登出）
		getHttpRequest().getSession().invalidate();
		return new StringRepresentation(String.valueOf(1));
	}

	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {
		//用户登入
		 Form form = new Form(entity); 
		 String userName = form.getFirstValue("username");
		 String password = form.getFirstValue("password");
		 SysUser syuser = sysUserDao.getUserById(userName);
		 if(syuser==null||!ESDecode.Decode2(syuser.getPassword()).equals(password)){
			 throw new FounderException(100128,"用户名或密码错误！");
		 }
		 syuser.setAuthorities(sysUserDao.getAuListByUserId(syuser.getUserid()));
		 
		 String org = sysOrgmemberDao.getUserOrg(syuser.getUserid());
		 
		 syuser.setOrgId(org);
		 //从配置文件取用户登录IP
		 syuser.setLoginIpAddr(StringUtils.isBlank(loginIpAddr)?getIpAddr(getHttpRequest()):loginIpAddr);
		 setSSOMemCache(syuser,getHttpRequest(),getHttpResponse());
//		 getHttpSession().setAttribute(SessionAttrConstant.LOGIN_USER, syuser);
		 getResponse().setStatus(Status.SUCCESS_OK);
		 return new StringRepresentation(userName+password);
	}
	
	
	/**
	 * SSO登录处理
	 * @author founder 2012-3-13
	 * @param sysUser
	 * @param httpRequest
	 * @param httpResponse
	 */
	private void setSSOMemCache(SysUser sysUser ,HttpServletRequest httpRequest,HttpServletResponse httpResponse){
		if(SsoUtil.getSSOManagerFromSession(httpRequest)!=null){
	        ISSOManager sSOManager = getSSOManager(); 
	        if(sSOManager != null && sSOManager.getSysUser()!=null){
	        	if(!sysUser.getUsername().equals(sSOManager.getSysUser().getUsername())){
	    	        SsoUtil.removeSsoIdFromCookie(httpRequest,httpResponse);
	        		sSOManager.remove();
//	    	        if(null!=httpRequest.getSession())
//	    	        	httpRequest.getSession().invalidate();
	        	}
	        }
		}	
		
		String ssoId = GuidUtil.getRandomGUID(true);
		ISSOManager sSOManager = new SSOManager();
	    sSOManager.setSsoId(ssoId);
	    sSOManager.setSysUser(sysUser);
	    sSOManager.insert();
	    
//	    SsoUtil.insertSSOManager((SSOManager)sSOManager);
	    // 保存到session中
	    SsoUtil.setSSOManagerToSession(httpRequest,sSOManager);
	    // 保存到cookie中
	    SsoUtil.setSsoIdToCookie(httpRequest,ssoId,httpResponse);
	}
}
