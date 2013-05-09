package com.founder.sipbus.syweb.au.util;

import com.founder.sipbus.syweb.au.po.SysUser;


/**
 * [类名]<br>
 * ISSOManager<br>
 * <br>
 * [功能概要]<br>
 * SSO管理对象的接口类<br>
 * <br>
 * [变更履历]<br>
 * 2009/7/1 ver1.00 新建 weifeng<br>
 * <br>
 * 
 * @author FOUNDER CORPORATION
 */
public interface ISSOManager {
    /**
     * 是否需要同期处理
     * 
     * @return 是否需要同期
     */
    boolean needRefresh();

    /**
     * 将userEntity对象上传到memcached服务器
     */
    void insert();

    /**
     * 从memcached服务器上删除userEntity对象
     */
    void remove();

    /**
     * 刷新同期时间
     */
    void refresh();

    /**
     * SSOIDを取得します。
     * 
     * @return SSOID
     */
    String getSsoId();

    /**
     * SSOIDを設定します。
     * 
     * @param ssoId
     *            SSOID
     */
    void setSsoId(String ssoId);

    /**
     * @return the userInfoTO
     */
    SysUser getSysUser();

    /**
     * @param userInfoTO
     *            the userInfoTO to set
     */
    void setSysUser(SysUser SysUser);
}
