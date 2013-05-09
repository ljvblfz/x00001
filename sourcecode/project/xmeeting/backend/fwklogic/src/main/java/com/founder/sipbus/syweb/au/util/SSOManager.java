package com.founder.sipbus.syweb.au.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.founder.sipbus.common.framework.repository.NetSettingRepository;
import com.founder.sipbus.common.util.NumUtil;
import com.founder.sipbus.syweb.au.po.SysUser;

/**
 * [类名]<br>
 * SSOManager<br>
 * <br>
 * [功能概要]<br>
 * SSO管理对象的实现类<br>
 * <br>
 * [变更履历]<br>
 * 2009/7/1 ver1.00 新建 weifeng<br>
 * <br>
 * 
 * @author FOUNDER CORPORATION
 */
public class SSOManager implements ISSOManager,Serializable {

    public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3547204524155757421L;

    /**
     * SSOID
     */
    private String ssoId = null;

    /**
     * 同期时间
     */
    private Date updateDate = null;

    /**
     * 用户认证信息
     */
    private SysUser sysUser = null;

    /**
     * 是否需要同期
     * 
     * @return 是否需要同期
     */
    public boolean needRefresh() {
        if (updateDate == null) {
            return true;
        }

        return new Date().after(updateDate);
    }

    /**
     * 将loginId对象上传到memcached服务器
     */
    public void insert() {
        Map<String, String> memcachedServer = NetSettingRepository.getMap(NetSettingRepository.MEMCACHED_SERVER_CONFIG);
        // 取得对象超时时间
        String timeout = memcachedServer.get("timeout");
        MemCachedClient mcc = MemcachedUtil.getMemCachedClient();
        mcc.set(ssoId, this, new Date(NumUtil.convertToInt(timeout) * 60 * 1000));
        // 取得同期间隔
        String updateTime = memcachedServer.get("updateTime");
        updateDate = new Date(new Date().getTime() + NumUtil.convertToLong(updateTime) * 60 * 1000);
    }

    /**
     * 从memcached服务器上删除loginId对象
     */
    public void remove() {
        MemCachedClient mcc = MemcachedUtil.getMemCachedClient();
        mcc.delete(ssoId);
    }

    /**
     * 刷新同期时间
     */
    public void refresh() {
        Map<String, String> memcachedServer = NetSettingRepository.getMap(NetSettingRepository.MEMCACHED_SERVER_CONFIG);
        // 取得对象超时时间
        String timeout = memcachedServer.get("timeout");
        MemCachedClient mcc = MemcachedUtil.getMemCachedClient();
        mcc.replace(ssoId, this, new Date(NumUtil.convertToInt(timeout) * 60 * 1000));

        // 取得同期间隔
        String updateTime = memcachedServer.get("updateTime");
        updateDate = new Date(new Date().getTime() + NumUtil.convertToLong(updateTime) * 60 * 1000);

    }

    /**
     * SSOIDを取得します。
     * 
     * @return SSOID
     */
    public String getSsoId() {
        return ssoId;
    }

    /**
     * SSOIDを設定します。
     * 
     * @param ssoId
     *            SSOID
     */
    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }

    /**
     * @return the sysUser
     */
    public SysUser getSysUser() {
        return sysUser;
    }

    /**
     * @param sysUser
     *            the sysUser to set
     */
    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }

}
