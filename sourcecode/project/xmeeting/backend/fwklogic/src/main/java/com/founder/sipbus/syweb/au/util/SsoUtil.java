package com.founder.sipbus.syweb.au.util;

import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.restlet.routing.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.founder.sipbus.common.framework.repository.NetSettingRepository;
import com.founder.sipbus.common.util.NumUtil;
import com.founder.sipbus.syweb.au.po.SysUser;

/**
 * [����]<br>
 * SsoUtil<br>
 * <br>
 * [���ܸ�Ҫ]<br>
 * SSO������<br>
 * <br>
 * [�������]<br>
 * 2009/7/1 ver1.00 �½� weifeng<br>
 * <br>
 * 
 * @author FOUNDER CORPORATION
 */
public final class SsoUtil {

	private static Logger debugLog = LoggerFactory.getLogger(SsoUtil.class);

    private SsoUtil() {

    }

    /**
     * SSOID
     */
    public static final String SSO_ID = "SSO_ID";

    /**
     * ��֤�ɹ����URL
     */
    public static final String AUTH_OK_URL = "SESSION_AUTH_OK_URL";

    /**
     * SSOManager������HttpSession���KEY
     */
    public static final String SSO_MANAGER_KEY = "SSO_MANAGER_KEY";

    /**
     * ��HttpSession�л�ȡSSOManager����
     * 
     * @return SSO�������
     */
    public static String getSSOManagerFromSession(HttpServletRequest request) {

        return (String) request.getSession().getAttribute(SSO_MANAGER_KEY);
    }

    /**
     * ��HttpSession�л�ȡSSOManager����
     * 
     * @return SSO�������
     */
    public static String getSSOManagerFromSession(HttpSession session) {

        return (String) session.getAttribute(SSO_MANAGER_KEY);
    }

    /**
     * ��sSOManager�ŵ�Session��
     * 
     * @param sSOManager
     *            SSO�������
     */
    public static void setSSOManagerToSession(HttpServletRequest request, ISSOManager sSOManager) {

        request.getSession().setAttribute(SSO_MANAGER_KEY, sSOManager.getSsoId());
    }

    public static void insertSSOManager(SSOManager sSOManager){
    	  Map<String, String> memcachedServer = NetSettingRepository.getMap(NetSettingRepository.MEMCACHED_SERVER_CONFIG);
          // 取得对象超时时间
          String timeout = memcachedServer.get("timeout");
          MemCachedClient mcc = MemcachedUtil.getMemCachedClient();
          mcc.set(sSOManager.getSsoId(), sSOManager, new Date(NumUtil.convertToInt(timeout) * 60 * 1000));
          mcc.get("66a1dc02e3623d4708a1a3854e84fec0");
          // 取得同期间隔
          String updateTime = memcachedServer.get("updateTime");
		sSOManager.setUpdateDate(new Date(new Date().getTime() + NumUtil.convertToLong(updateTime) * 60 * 1000));
    }
    

    public static void refreshSSOManager(SSOManager sSOManager){
      Map<String, String> memcachedServer = NetSettingRepository.getMap(NetSettingRepository.MEMCACHED_SERVER_CONFIG);
      // 取得对象超时时间
      String timeout = memcachedServer.get("timeout");
      MemCachedClient mcc = MemcachedUtil.getMemCachedClient();
      mcc.replace(sSOManager.getSsoId(), sSOManager, new Date(NumUtil.convertToInt(timeout) * 60 * 1000));

      // 取得同期间隔
      String updateTime = memcachedServer.get("updateTime");
      sSOManager.setUpdateDate(new Date(new Date().getTime() + NumUtil.convertToLong(updateTime) * 60 * 1000));
    }
    
    /**
     * ��HttpSession��ɾ��SSOManager����
     * 
     * @param request
     * @return
     */
    public static void removeSSOManagerFromSession(HttpServletRequest request) {
        request.getSession().removeAttribute(SSO_MANAGER_KEY);
    }

    /**
     * ���Session�е����ж���
     * 
     * @param request
     */
    public static void removeAllObjectsFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        @SuppressWarnings("rawtypes")
		Enumeration enums = session.getAttributeNames();
        if (enums.hasMoreElements()) {
            String sessionKey = (String) enums.nextElement();
            session.removeAttribute(sessionKey);
            if (debugLog.isDebugEnabled()) {
                debugLog.debug("session.removeAttribute:" + sessionKey);
            }
        }
    }

    /**
     * ��Cookie��ȡ��SSOID
     * 
     * @return SSOID
     */
    public static String getSsoIdFromCookie(HttpServletRequest request) {

        String value = null;
//        debugLog.error("----------------------request----------"+request);
        Cookie[] cookies = request.getCookies();
//        debugLog.error("----------------------cookies----------"+cookies);
        if (cookies != null) {
            for (Cookie cookie : cookies) {
            	if(null!=cookie){
	                String cookieName = cookie.getName();
	                if (SSO_ID.equals(cookieName)) {
	                    value = cookie.getValue();
	                    break;
	                }
            	}
            }
        }
//        debugLog.error("----------------------value1----------"+value);

        if (value == null) {
            value = (String) request.getAttribute(SSO_ID);
        }

//        debugLog.error("----------------------value2----------"+value);
        return value;
    }

    /**
     * ��SSOID���õ�Cookie��
     * 
     * @param ssoId
     *            SSOID
     * @return
     */
    public static void setSsoIdToCookie(HttpServletRequest request, String ssoId,HttpServletResponse response) {

//        HttpServletResponse response = ServletActionContext.getResponse();
        String serverDomain = NetSettingRepository.getString(NetSettingRepository.WEB_SERVER_DOMAIN);

        Cookie cookie = new Cookie(SSO_ID, ssoId);
        cookie.setDomain(serverDomain);
        cookie.setPath("/");
        response.addCookie(cookie);
        request.setAttribute(SSO_ID, ssoId);
    }

    /**
     * ɾ��Cookie�е�SSO_ID
     * 
     * @return
     */
    public static void removeSsoIdFromCookie(HttpServletRequest request, HttpServletResponse response) {

        String serverDomain = NetSettingRepository.getString(NetSettingRepository.WEB_SERVER_DOMAIN);

        Cookie cookie = new Cookie(SSO_ID, "");
        cookie.setDomain(serverDomain);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        request.removeAttribute(SSO_ID);
    }

    /**
     * ������ת����½����ǰ����Ҫ���ʵ�URL��Ϣ
     * 
     * @param request
     * @param mapping
     */
//    public static void setAuthOKUrl(HttpServletRequest request) {
//
//        String authOKUrl = getPath(request) + createQueryString(request);
//        request.getSession().setAttribute(AUTH_OK_URL, authOKUrl);
//    }

    /**
     * ���ص�¼�ɹ���URL��Ϣ
     * 
     * @return ��½����Ҫ��ת��URL
     */
    public static String getAuthOKUrl(HttpServletRequest request) {

        String result = (String) request.getSession().getAttribute(AUTH_OK_URL);
        if (result != null) {
            request.getSession().removeAttribute(AUTH_OK_URL);
        }

        return result;
    }

    /**
     * get the request parameter which submit by get mothed
     * 
     * @return query string
     */
//    public static String createQueryString(HttpServletRequest request) {
//
//        request.getQueryString();
//        String queryStr = "";
//        Enumeration paraEnu = request.getParameterNames();
//        while (paraEnu.hasMoreElements()) {
//            String paraName = (String) paraEnu.nextElement();
//            String[] values = request.getParameterValues(paraName);
//            if (values != null) {
//                for (int i = 0; i < values.length; i++) {
//                    queryStr += "&" + paraName + "=" + ResponseUtils.encodeURL(values[i]);
//                }
//            } else {
//                String value = request.getParameter(paraName);
//                queryStr += "&" + paraName + "=" + ResponseUtils.encodeURL(value);
//            }
//        }
//        if (!queryStr.equals("")) {
//            queryStr = "?" + queryStr.substring(1);
//        }
//        return queryStr;
//    }

    /**
     * WebSphere �� Tomcat getPathInfo��getServletPath}����ķ���ֵ��һ�£���Ҫ���⴦��
     * 
     * @param request
     *            HttpServletRequest
     * @return ����ʱ��action·��
     */
    public static String getPath(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null)
            return request.getServletPath();

        // Websphere 6.1 is a bit wonky (see TAPESTRY-1713), and tends to return the empty string
        // for the servlet path, and return the true path in pathInfo.

        return pathInfo.length() == 0 ? "/" : pathInfo;
    }
    
    
    //===============
    public static SysUser getLoginUser(HttpServletRequest request) {
		return getSSOManager(request).getSysUser();
	}

	public static ISSOManager getSSOManager(HttpServletRequest request) {
		String key=getSSOManagerFromSession(request);
		ISSOManager ssoManager = (ISSOManager) MemcachedUtil.getMemCachedClient().get(key);
		return ssoManager;
	}

}
