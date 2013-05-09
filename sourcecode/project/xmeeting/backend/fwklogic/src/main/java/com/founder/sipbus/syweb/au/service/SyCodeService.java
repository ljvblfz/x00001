package com.founder.sipbus.syweb.au.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.syweb.au.dao.SyCodeDaoImpl;
import com.founder.sipbus.syweb.au.dao.SyCodeTypeDaoImpl;
import com.founder.sipbus.syweb.au.po.SyCode;
import com.founder.sipbus.syweb.au.po.SyCodeType;

@Component
public class SyCodeService {

	private static String KEY_GEN = "SY_TYPE_";

	private SyCodeTypeDaoImpl syCodeTypeDao;

	private SyCodeDaoImpl syCodeDao;

	public void setSyCodeTypeDao(SyCodeTypeDaoImpl syCodeTypeDao) {
		this.syCodeTypeDao = syCodeTypeDao;
	}

	public void setSyCodeDao(SyCodeDaoImpl syCodeDao) {
		this.syCodeDao = syCodeDao;
	}

	public String getSyCodeName(String typename, String code) {
		SyCodeType syCodeType = getSyCodeType(typename);
		if (null != syCodeType && null != code) {
			List<SyCode> l = syCodeType.getSyCodeList();
			for (SyCode sycode : l) {
				if (code.equals(sycode.getValueCode())) {
					return sycode.getValueName();
				}
			}
		}
		return "";

		// String sycodeName = "";
		// Map<String, Map<String, String>> syTypeCodeNameMap =
		// getSyTypeCodeNameMap();
		//
		// if (syTypeCodeNameMap.containsKey(typename)
		// && syTypeCodeNameMap.get(typename).containsKey(code)) {
		// sycodeName = syTypeCodeNameMap.get(typename).get(code);
		// } else {
		// sycodeName = code;
		// }
		// return sycodeName;
	}

	private SyCodeType getSyCodeType(String typename) {

		SyCodeType syCodeType = (SyCodeType) StdMemCacheUtil
				.getMemCachedClient().get(KEY_GEN + typename);
		if (null == syCodeType) {
			syCodeType = syCodeTypeDao.getSyCodeType1(typename);
			StdMemCacheUtil.getMemCachedClient().set(KEY_GEN + typename,
					syCodeType);
		}

		return syCodeType;
	}

	public SyCodeType getSyCodeTypeById(String typeId) {
		SyCodeType syCodeType = syCodeTypeDao.findById(typeId);
		SyCodeType cachedSyCodeType = (SyCodeType) StdMemCacheUtil
				.getMemCachedClient().get(KEY_GEN + syCodeType.getTypeName());
		if (null != cachedSyCodeType) {
			return cachedSyCodeType;
		} else {
			syCodeType
					.setSyCodeList(syCodeDao.getSyCode(syCodeType.getTypeId()));
			StdMemCacheUtil.getMemCachedClient().set(
					KEY_GEN + syCodeType.getTypeName(), syCodeType);
		}
		return syCodeType;
	}

	public void reflushCodeType(String typeId) {
		SyCodeType syCodeType = syCodeTypeDao.findById(typeId);
		syCodeType.setSyCodeList(syCodeDao.getSyCode(syCodeType.getTypeId()));
		StdMemCacheUtil.getMemCachedClient().delete(
				KEY_GEN + syCodeType.getTypeName());
		StdMemCacheUtil.getMemCachedClient().set(
				KEY_GEN + syCodeType.getTypeName(), syCodeType);
	}

	public void deleteSyCodeType(String typeId) {
		SyCodeType syCodeType = syCodeTypeDao.findById(typeId);
		if (syCodeType != null) {
			StdMemCacheUtil.getMemCachedClient().delete(
					KEY_GEN + syCodeType.getTypeName());
			syCodeTypeDao.delete(syCodeType);
		}

	}

	public List<SyCode> getSyCodeByTypeName(String typeName) {
		SyCodeType sycodetype = getSyCodeType(typeName);
		if (null != sycodetype) {
			return sycodetype.getSyCodeList();
		}
		return null;
	}

	public void removeCache(String typeName) {
		StdMemCacheUtil.getMemCachedClient().delete(KEY_GEN + typeName);
	}

	public SyCodeType findById(String typeId) {
		return syCodeTypeDao.findById(typeId);
	}

	// private Map<String, Map<String, String>> getSyTypeCodeNameMap() {
	// Map<String, Map<String, String>> cache_syTypeCodeNameMap = (Map<String,
	// Map<String, String>>) MemcachedUtil
	// .getMemCachedClient().get("syTypeCodeNameMap");
	// if (null == cache_syTypeCodeNameMap) {
	// cache_syTypeCodeNameMap = setSyTypeCodeNameMap();
	// }
	// return cache_syTypeCodeNameMap;
	// }
	//
	// private Map<String, Map<String, String>> setSyTypeCodeNameMap() {
	// Map<String, Map<String, String>> db_syTypeCodeNameMap =
	// findSyTypeCodeNameMap();
	// MemcachedUtil.getMemCachedClient().set("syTypeCodeNameMap",
	// db_syTypeCodeNameMap);
	// return db_syTypeCodeNameMap;
	// }
	//
	//
	// private Map<String, Map<String, String>> findSyTypeCodeNameMap() {
	//
	// Map<String, Map<String, String>> syTypeCodeNameMap = new HashMap<String,
	// Map<String, String>>();
	//
	// Map<String, String> typenameMap = syCodeTypeDao.getAllTypeName();
	// Set<String> typenameKeySet = typenameMap.keySet();
	//
	// Iterator<String> typenameKeyIterator = typenameKeySet.iterator();
	// while (typenameKeyIterator.hasNext()) {
	// String typenameKey = typenameKeyIterator.next();
	// Map<String, String> codenameMap = syCodeDao
	// .getAllCodeName(typenameKey);
	// syTypeCodeNameMap.put(typenameKey, codenameMap);
	// }
	//
	// return syTypeCodeNameMap;
	// }
}
