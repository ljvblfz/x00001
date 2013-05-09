
package com.founder.sipbus.syweb.au.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.routing.Filter;
import org.restlet.routing.Route;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.sipbus.common.constant.SessionAttrConstant;
import com.founder.sipbus.common.exception.FounderException;
import com.founder.sipbus.common.represenation.JsonRepresentation;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.LoginInfoUtils;
import com.founder.sipbus.syweb.au.dao.SysUserDaoImpl;
import com.founder.sipbus.syweb.au.po.AuFunction;
import com.founder.sipbus.syweb.au.util.ISSOManager;
import com.founder.sipbus.syweb.au.util.MemcachedUtil;
import com.founder.sipbus.syweb.au.util.SsoUtil;

/***
 * 
 * @author Tracy Chen
 */
public class AuthFilter extends Filter {

//	private Log log = LogFactory.getLog(AuthFilter.class);

	private Logger log = LoggerFactory.getLogger(Filter.class);
	private final static String DOMAIN="domain"; 
	private static String domain;
		
//	private HttpServletRequest httpRequest;
//	private HttpServletResponse httpResponse;
	
	private SysUserDaoImpl sysUserDao; 
	public void setSysUserDao(SysUserDaoImpl sysUserDao) {
		this.sysUserDao = sysUserDao;
	}
 
	private List<String> publicurl; 
	public void setPublicurl(List<String> publicurl) {
		this.publicurl = publicurl;
	}

	private boolean isPublicUrl(String path,String requestMethod,String requestResourceURI){ 
		if(null==publicurl){
			return false;
		}
		String[] pathDetail;
		for(String url:publicurl){
			pathDetail=url.split("#");
			if(requestResourceURI.equals(pathDetail[0])&&requestMethod.equals(pathDetail[1])){
				return true;
			}
		}
		return false;
	} 

	@SuppressWarnings("deprecation")
	@Override
	protected int doHandle(Request request, Response response) { 
		String path=request.getResourceRef().getPath(); 
		String requestResourceURI = ((org.restlet.routing.TemplateRoute) ((org.restlet.routing.Router) getNext())
				.getNext(request, response)).getTemplate().getPattern();
		if(isPublicUrl(path,request.getMethod().toString(),requestResourceURI)){
			log.trace("The public url is: {}.",path);
			return super.doHandle(request, response);
		}//end of if 
		
		//=================>
		try{
			HttpServletRequest httpRequest=ServletUtils.getRequest(request);   
			HttpServletResponse httpResponse=ServletUtils.getResponse(response);

			//((org.restlet.routing.Route)((org.restlet.routing.Router)getNext()).getNext(request, response)).getTemplate().getPattern()
//	        sSOManager.
	        
			if(null==domain){
				domain=httpRequest.getSession().getServletContext().getInitParameter(DOMAIN);
			}
			HttpSession session=httpRequest.getSession();
			
			if(session.getAttribute(SessionAttrConstant.DOMAIN)==null){
				httpRequest.getSession().setAttribute(SessionAttrConstant.DOMAIN, domain);
			}

			Reference ref = request.getResourceRef();
//			Object loginUser=session.getAttribute(SessionAttrConstant.LOGIN_USER);
//			String path=request.getResourceRef().getPath();
//			if(loginUser==null){
//				int port=httpRequest.getServerPort();
//				if(path.indexOf("login")==-1){
			 ISSOManager sSOManager =null;
			if(isProtectedUri(path)){
					 // 从Cookie中获取SSOID
			        String ssoId = SsoUtil.getSsoIdFromCookie(httpRequest);

//					log.error("sessionto----"+path+"----------domain:--"+domain+"-------------ssoiId---"+ssoId);
			        if (ssoId == null) {
			            //SsoUtil.removeSSOManagerFromSession(httpRequest);
	                    SsoUtil.removeAllObjectsFromSession(httpRequest);
//			        	if(null!=httpRequest.getSession())
//			        		httpRequest.getSession().invalidate();
			            //超时
//						log.error("sessionto----"+path+"----------domain:--"+domain+"---------------timeout1-");
			            response.setEntity(new JsonRepresentation(JsonUtils.genReturnJson("301", "", "", "", "")));
						response.setStatus(Status.REDIRECTION_PERMANENT);
						return CONTINUE;
			        } else {
			            // 从HttpSession中取得SSOManager
			        	String oldSsoId = SsoUtil.getSSOManagerFromSession(httpRequest);
//						log.error("sessionto----"+path+"----------domain:--"+domain+"-------------oldSsoId1---"+oldSsoId);
			        	sSOManager=(ISSOManager)MemcachedUtil.getMemCachedClient().get(oldSsoId);
//						log.error("sessionto----"+path+"----------domain:--"+domain+"-------------oldsSOManager1---"+sSOManager);
			            if (sSOManager != null) {
			                // 查看页面传入的ssoId和Session的是否一致
			                if (ssoId.equals(sSOManager.getSsoId())) {
//								log.error("sessionto----"+path+"---------domain:--"+domain+"-------------equals 2 ssoid---");
			                    // 查看是否需要同期Os
			                    if (sSOManager.needRefresh()) {
			                        // 刷新expireDate
			                        sSOManager.refresh();
			                    }
			                } else {
//								log.error("sessionto----"+path+"----------domain:--"+domain+"-------------notequals ssoid---");
			                    // 从memcachedServer取得SSOManger对象
			                    SsoUtil.removeAllObjectsFromSession(httpRequest);
			                    sSOManager = MemcachedUtil.getSSOManager(httpRequest);
//								log.error("sessionto----"+path+"----------domain:--"+domain+"-------------sSOManager1---"+sSOManager);
			                    if (sSOManager == null) {
			                        SsoUtil.removeSsoIdFromCookie(httpRequest, httpResponse);   
			                        //超时
//									log.error("sessionto----"+path+"----------domain:--"+domain+"---------------timeout2-");
			                        response.setEntity(new JsonRepresentation(JsonUtils.genReturnJson("301", "", "", "", "")));
									response.setStatus(Status.REDIRECTION_PERMANENT);
									return CONTINUE;
			                    } else {
			                        SsoUtil.setSSOManagerToSession(httpRequest, sSOManager);
			                    }
			                }
			            } else {
			                // 从memcachedServer取得SSOManger对象
			                sSOManager = MemcachedUtil.getSSOManager(httpRequest);
//							log.error("sessionto----"+path+"----------domain:--"+domain+"-------------sSOManager2---"+sSOManager);
			                if (sSOManager == null) {
			                    SsoUtil.removeSsoIdFromCookie(httpRequest, httpResponse);
			                    //超时
//								log.error("sessionto----"+path+"----------domain:--"+domain+"---------------timeout3-");
			                    response.setEntity(new JsonRepresentation(JsonUtils.genReturnJson("301", "", "", "", "")));
								response.setStatus(Status.REDIRECTION_PERMANENT);
								return CONTINUE;
			                } else {
			                    SsoUtil.setSSOManagerToSession(httpRequest, sSOManager);
			                }
			            }
			        }
			        LoginInfoUtils.setUser(sSOManager.getSysUser().getUserid());
//				}
//			}
					//获取请求的具体URL 做权限匹配
//					String requestURI=((Route)((Router)getNext()).getNext(request, response)).getTemplate().getPattern();
//					boolean isAuth=sysUserDao.hasAuth(sSOManager.getSysUser().getUserid(), requestURI, request.getMethod().toString());
//					if(!isAuth){
//						response.setStatus(Status.SERVER_ERROR_INTERNAL);
//						response.setEntity(new JsonRepresentation(JSONObject.fromObject("{statusCode:401,message:'没有操作权限'}")));
//						return CONTINUE;
//					}
//					//记录访问日志
//					accessLog(null);
			}
//			System.out.println(path+"=====path");
//			ref.setBaseRef(request.getRootRef());
//			if(true){
//				response.setStatus(Status.REDIRECTION_PERMANENT);
//				return 0;
//			}
			
//			if (request.getMethod().equals(Method.GET)
//					|| isProtectedUri(ref.getRemainingPart())) {
				// accept(request, response);
				//return CONTINUE;
				return super.doHandle(request, response);
//			} else {
//				return super.doHandle(request, response);
//			}
		}catch(Exception e){
			//e.printStackTrace();
			e.printStackTrace();
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new FounderException(199999,"系统未知错误");
		}
	}

	
	
	private void accessLog(AuFunction af){
//		String ipAddr = httpRequest.getRemoteAddr();
//		
//		System.out.println(ipAddr);
	}
	
	private boolean isProtectedUri(String uri) {
		// do some validation to check the uri is protected
		if(uri.indexOf("rs/login")!=-1){
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		ClientResource client = new ClientResource("http://localhost:8080/syWeb/rs/login");
		Representation responses = client.get();
		try {
			String loginUserStr=responses.getText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.release();
	}
}
