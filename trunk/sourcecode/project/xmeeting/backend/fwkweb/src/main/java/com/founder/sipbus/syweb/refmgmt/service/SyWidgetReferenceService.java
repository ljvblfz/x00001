package com.founder.sipbus.syweb.refmgmt.service;

import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.JSQLParserException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.fwk.jsqlparser.SelectSqlParserSupport;
import com.founder.sipbus.sys.reference.dao.ReferenceIDaoImpl;
import com.founder.sipbus.syweb.cck.service.SyCckService;
import com.founder.sipbus.syweb.refmgmt.dao.SyWidgetReferenceDaoImpl;
import com.founder.sipbus.syweb.refmgmt.po.SyWidgetReference;

/**
 * SyWidgetReferenceService
 * @author zjl
 *
 */
@Component
public class SyWidgetReferenceService {
private static final String	KEY_GEN_REFERENCE="SY_WIDGET_REFERENCE_";

private ReferenceIDaoImpl referenceIDao;
	private SyWidgetReferenceDaoImpl syWidgetReferenceDao;

	public void setSyWidgetReferenceDao(
			SyWidgetReferenceDaoImpl syWidgetReferenceDao) {
		this.syWidgetReferenceDao = syWidgetReferenceDao;
	}

	/**
	 *add 方法
	 *	新增
	 *  @param syWidgetReference
	 *  @return null为正确 其他为错误消息
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午2:14:35 
	 */
	public String add(SyWidgetReference syWidgetReference) {
		// TODO 检测唯一性

		String reference = syWidgetReference.getReferenceValue();
		if (checkReferenceSQL(reference)) {
//			try {
				syWidgetReferenceDao.add(syWidgetReference);
				if (createCache(syWidgetReference.getSwrCode())) {
					return null;
				}
				
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				return "sql错误";
//			}
//		 
		}
		
		return "sql错误";
		
		
	}

	/**
	 *checkReferenceSQL 方法
	 * <p>方法说明:</p>
	 *检查sql是否符合要求
	 *  @param sqlText
	 *  @return 是否正确
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午2:14:49 
	 */
	public boolean checkReferenceSQL(String sqlText) {
		try {
			return SelectSqlParserSupport.testSqlText(sqlText);

		} catch (JSQLParserException e) {
			e.printStackTrace();
			return false;
		}

	}
	
	/**
	 *调用SelectSqlParserSupport.getAutocompleteSQLMap 解析sql语句。
	 *@see com.founder.sipbus.fwk.jsqlparser.SelectSqlParserSupport#getAutocompleteSQLMap
	 *  @param sqlText sql语句
	 *  @param index （未使用）
	 *  @return 参数Map
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午2:20:20 
	 */
	public Map<String, String> getAutocompleteSQLMap(
			String sqlText, int index) {
		try {
			return		SelectSqlParserSupport.getAutocompleteSQLMap(sqlText, "", index);
		} catch (JSQLParserException e) {
			e.printStackTrace();
			return null;
		}
		 
	}
	
	/**
	 *getCache 
	 *根据 code 获取引用数据的缓存
	 *若不存在则生成缓存 {@link #createCache(String)}
	 *  @param code
	 *  @return 引用数据 
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午3:23:31 
	 */
	public   Map<String, Object> getCache(
			String code) {
		Map<String, Object> map = (Map<String, Object>) StdMemCacheUtil.getMemCachedClient().get(KEY_GEN_REFERENCE + code);
			 if (null==map) {
				if (createCache(code)) {//还未缓存则 新建缓存
					 map = (Map<String, Object>) StdMemCacheUtil.getMemCachedClient().get(KEY_GEN_REFERENCE + code);
					 return map;	
				} 
				 return null; 
			}
			 return map;
	 
		 
	}
	
	/**
	 *createCache 
	 *	根据code 新建缓存
	 *	需要调用 {@link #getAutocompleteSQLMap(String, int)}
	 *  @param code
	 *  @return 是否成功
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午3:23:55 
	 */
	public boolean createCache(String code) {
	 SyWidgetReference syWidgetReference = syWidgetReferenceDao.findByCode(code);
	 if (null!=syWidgetReference) {
			Map autocompleteSQLMap =getAutocompleteSQLMap(syWidgetReference.getReferenceValue(), 0);
			autocompleteSQLMap.put("bean", syWidgetReference);
			StdMemCacheUtil.getMemCachedClient().set(KEY_GEN_REFERENCE + code,autocompleteSQLMap);
			 return true;
	 }else {
		return false;
	}
		
	}

	/**
	 *clearCache 
	 *根据code 删除缓存
	 *  @param code
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午3:27:35 
	 */
	public void clearCache(String code) {

		StdMemCacheUtil.getMemCachedClient().delete(KEY_GEN_REFERENCE + code);
	List<String> list = referenceIDao.querySctGuids(code);
	String sctGuid;
	if (null!=list) {
		for (int i = 0; i <list.size(); i++) {
			sctGuid=list.get(i);
			StdMemCacheUtil.getMemCachedClient().delete(
					SyCckService.KEY_GEN_PARAMS + sctGuid);
			StdMemCacheUtil.getMemCachedClient().delete(
					SyCckService.KEY_GEN_FORMS + sctGuid);

		}
	}
		
		
	}


	public ReferenceIDaoImpl getReferenceIDao() {
		return referenceIDao;
	}

	public void setReferenceIDao(ReferenceIDaoImpl referenceIDao) {
		this.referenceIDao = referenceIDao;
	}

	/**
	 *	查询数据库获得Autocomplete所需数据
	 * @see ReferenceIDaoImpl#getAutocomplete(Map)
	 *  @param code 引用code(未使用)
	 *  @param map 参数Map
	 *  @return Autocomplete所需数据
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午3:39:00 
	 */
	public   List<Map<String, Object>> getAutocomplete(  Map map) {
		return referenceIDao.getAutocomplete(map);
	}

	/**
	 *查询数据库获得Combox ComboxCascade 所需数据
	 *@see ReferenceIDaoImpl#getReference(String)
	 *  @param sqlString sql语句
	 *  @return Combox ComboxCascade 所需数据
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午3:39:51 
	 */
	public List<Map<String, Object>> getReference(String sqlString) {
		return referenceIDao.getReference(sqlString);
	}

	/**
	 *clearCacheById 
	 *根据id 删除缓存
	 *  @param id
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午3:28:16 
	 */
	public void clearCacheById(String id) {
			SyWidgetReference syWidgetReference = syWidgetReferenceDao.findById(id);
			if (syWidgetReference!=null && StringUtils.isNotBlank(syWidgetReference.getSwrCode())) {
				clearCache(syWidgetReference.getSwrCode());
			}
		
	}

	/**
	 *update 
	 *	更新
	 *  @param swrGuid
	 *  @param valuesMap
	 *  @return null为成功，其他为失败消息
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午3:24:42 
	 */
	public String update(String swrGuid, Map<String, String> valuesMap) {
		SyWidgetReference b= syWidgetReferenceDao.findById(swrGuid);
		valuesMap.remove("swrGuid");
		String oldcode=b.getSwrCode();
		if(checkReferenceSQL(valuesMap.get("referenceValue"))){
			PMGridCopyUtil.copyGridToDto(b, valuesMap);}else {
				return "SQL不正确！";
			}
		clearCache(oldcode);
		createCache(b.getSwrCode());
		return null;
	}

	/**
	 *getBean 方法
	 *	获取SyWidgetReference对象，从缓存或数据库中
	 *  @param code
	 *  @return SyWidgetReference
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午2:18:16 
	 */
	public SyWidgetReference getBean(String code) {
		Map map=getCache(code);
		if (null!=map) {
			SyWidgetReference	syWidgetReference = (SyWidgetReference) map.get("bean");
			if (null!=syWidgetReference) {
				return syWidgetReference;
			}
			
		}
		return null;
		
	}
}
