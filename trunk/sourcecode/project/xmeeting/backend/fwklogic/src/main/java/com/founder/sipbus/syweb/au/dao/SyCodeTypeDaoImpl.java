/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus.syweb.au.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.syweb.au.po.SyCodeType;

@Component
public class SyCodeTypeDaoImpl extends DefaultBaseDaoImpl<SyCodeType, String> {
	private static String KEY_GEN = "SY_TYPE_";

	private SyCodeDaoImpl syCodeDao;

	public void setSyCodeDao(SyCodeDaoImpl syCodeDao) {
		this.syCodeDao = syCodeDao;
	}

	public boolean checkIfExistsTypeName(String tableName, String columnName,
			String typeName) {
		String hql = "select count(*) from " + tableName + " t where t."
				+ columnName + " = ?";
		Long count = (Long) findUniqueByHqlNoEntityType(hql, typeName);
		if (count.intValue() > 0) {
			return true;
		} else {
			return false;
		}
	}

//	/**
//	 * 获取码表信息
//	 * 
//	 * @author founder 2012-7-9
//	 * @param typeId
//	 * @return
//	 */
//	public SyCodeType getSyCodeType(String typeId) {
//		SyCodeType syCodeType = (SyCodeType) StdMemCacheUtil
//				.getMemCachedClient().get(KEY_GEN + typeId);
//		if (null == syCodeType) {
//			syCodeType = findById(typeId);
//			if (StringUtils.isNotBlank(syCodeType.getRedOnyFlg())) {
//				syCodeType.setRedOnlyFlaStr(syCodeDao.getSyCodeName("1002",
//						syCodeType.getRedOnyFlg()));
//			}
//
//			if (StringUtils.isNotBlank(syCodeType.getDomain())) {
//				syCodeType.setDomainStr(syCodeDao.getSyCodeName("1001",
//						syCodeType.getDomain()));
//			}
//			syCodeType
//					.setSyCodeList(syCodeDao.getSyCode(syCodeType.getTypeId()));
//			StdMemCacheUtil.getMemCachedClient().set(KEY_GEN + typeId,
//					syCodeType);
//		}
//		return syCodeType;
//	}

	/**
	 * 获取码表信息
	 * 
	 * @author founder 2012-7-9
	 * @param typeId
	 * @return
	 */
	public SyCodeType getSyCodeType1(String typeName) {
		SyCodeType syCodeType = findUniqueByHql(
				"from SyCodeType where typeName=? and del_flag = 0", typeName);
		if (StringUtils.isNotBlank(syCodeType.getRedOnyFlg())) {
			syCodeType.setRedOnlyFlaStr(syCodeDao.getSyCodeName("1002",
					syCodeType.getRedOnyFlg()));
		}

		if (StringUtils.isNotBlank(syCodeType.getDomain())) {
			syCodeType.setDomainStr(syCodeDao.getSyCodeName("1001",
					syCodeType.getDomain()));
		}
		syCodeType.setSyCodeList(syCodeDao.getSyCode(syCodeType.getTypeId()));
		return syCodeType;
	}
	
	public void cacheUpdateSyCode(SyCodeType b) {
		if (null == b) {
			return;
		}
		SyCodeType cachedCodeType = (SyCodeType) StdMemCacheUtil
				.getMemCachedClient().get(KEY_GEN + b.getTypeId());
		if (cachedCodeType == null) {
			b.setSyCodeList(syCodeDao.getSyCode(b.getTypeId()));
		} else {
			b.setSyCodeList(cachedCodeType.getSyCodeList());
		}
		StdMemCacheUtil.getMemCachedClient().set(KEY_GEN + b.getTypeId(), b);

	}

	public Map<String, String> getAllTypeName() {
		String hql = "select from SyCodeType t where t.delFlag = 0";
		List list = findByHql(hql);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			SyCodeType syCodeType = (SyCodeType) list.get(i);
			map.put(syCodeType.getTypeName(), syCodeType.getTypeId());
		}
		return map;
	}
}
