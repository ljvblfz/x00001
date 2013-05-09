package com.founder.sipbus.syweb.cck.service;

import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.jsqlparser.JSQLParserException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.DateJsonValueProcessor;
import com.founder.sipbus.fwk.jsqlparser.SelectSqlParserSupport;
import com.founder.sipbus.sys.cck.dao.CckIDaoImpl;
import com.founder.sipbus.sys.cck.vo.DataBaseColumn;
import com.founder.sipbus.syweb.au.service.SyCodeService;
import com.founder.sipbus.syweb.cck.dao.SyCckContentDaoImpl;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeButtonDaoImpl;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeDaoImpl;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeFieldDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckType;
import com.founder.sipbus.syweb.cck.po.SyCckTypeField;
import com.founder.sipbus.syweb.cck.util.SyCckUtil;
import com.founder.sipbus.syweb.refmgmt.po.SyWidgetReference;
import com.founder.sipbus.syweb.refmgmt.service.SyWidgetReferenceService;

@Component
public class SyCckService {
	private static final String TRUE_NUMBER = "1";
	private static final String FALSE_NUMBER = "0";
	private CckIDaoImpl cckIDao;
	private SyCckTypeDaoImpl syCckTypeDao;
	private SyCckTypeFieldDaoImpl syCckTypeFieldDao;
	private SyCckContentDaoImpl syCckContentDao;
	private SyCodeService syCodeService;
	private SyWidgetReferenceService syWidgetReferenceService;
	private SyCckTypeButtonDaoImpl syCckTypeButtonDao;
	public static final String KEY_GEN_PARAMS = "CCK_GRID_SQL_PARAMS_"; // list
																		// 用的参数
	public static final String KEY_GEN_CODE_TO_ID = "CCK_CODE_TO_ID_"; // code
																		// 和sctGUid
	public static final String KEY_GEN_BUTTONS = "CCK_BUTTONS_"; // 按钮
	public static final String KEY_GEN_FORMS = "CCK_FORMS_";// form

	public static final String COLUMN_CREATE_BY = "COLUMN_CREATE_BY";
	public static final String COLUMN_CREATE_DT = "COLUMN_CREATE_DT";
	public static final String COLUMN_UPDATE_BY = "COLUMN_UPDATE_BY";
	public static final String COLUMN_UPDATE_DT = "COLUMN_UPDATE_DT";
	public static final String COLUMN_SCT_GUID = "COLUMN_SCT_GUID";
	public static final String LEFT_JOIN = " left join ";
	public static final String INNER_JOIN = " inner join ";
	private static final String KEY_GEN_PARAMS_BUTTON = "CCK_BUTTON_";
	public static Integer INTEGER_ONE = new Integer(1);
	public static String[] format = { "yyyy-MM-dd" };

	// public static String KEY_GEN_FORMS_FIELDS = "CCK_FORMS_FIELDS_";
	private Logger logger = LoggerFactory
			.getLogger(SyCckTypeFieldDaoImpl.class);
	private String TABLE_NOT_FOUND = "表不存在！";
	private static int USER_NAME_SIZE = 40;
	private static int GUID_SIZE = 36;
	private static String DEL_FLAG = "DEL_FLAG";

	// private static HashMap<Integer, String> typeMap = new HashMap<Integer,
	// String>();
	// static {
	// typeMap.put(Types.VARCHAR, "1");
	// // typeMap.put(key, value)
	// }

	public SyCckTypeFieldDaoImpl getSyCckTypeFieldDao() {
		return syCckTypeFieldDao;
	}

	public void setSyCckTypeFieldDao(SyCckTypeFieldDaoImpl syCckTypeFieldDao) {
		this.syCckTypeFieldDao = syCckTypeFieldDao;
	}

	public SyCckTypeDaoImpl getSyCckTypeDao() {
		return syCckTypeDao;
	}

	public void setSyCckTypeDao(SyCckTypeDaoImpl syCckTypeDao) {
		this.syCckTypeDao = syCckTypeDao;
	}

	public SyCckContentDaoImpl getSyCckContentDao() {
		return syCckContentDao;
	}

	public void setSyCckContentDao(SyCckContentDaoImpl syCckContentDao) {
		this.syCckContentDao = syCckContentDao;
	}

	public CckIDaoImpl getCckIDao() {
		return cckIDao;
	}

	public void setCckIDao(CckIDaoImpl cckIDao) {
		this.cckIDao = cckIDao;
	}

	@SuppressWarnings("rawtypes")
	public PageResponse queryGridByPage(PageRequest pageRequest, Map map,
			String ccktypeguid) {

		return cckIDao.queryGridByPage(pageRequest, map);

	}

	/**
	 * 新建缓存
	 * 
	 * @param params
	 * @param ccktypeguid
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:25:43
	 */
	public boolean createParamsForList(Map<String, Object> params,
			String ccktypeguid) {
		SyCckType type = syCckTypeDao.findById(ccktypeguid);
		if (null == type) {
			return false;
		}
		params.put("cckName", type.getName());
		params.put("sctGuid", ccktypeguid);
		boolean flag = false;
		// boolean isdelphysically=true;
//		String delFlagColumn = null;
		String createByColumn = null;
//		String sctGuidColumn = null;
		String primaryKey = null;
		String nameColumn = type.getNameColumn();
		// if (StringUtils.isBlank(nameColumn)) {
		//
		// }

		if (null != type) {
			String tableName = type.getTypetable();
			if (StringUtils.isNotBlank(tableName)) {
				// HashSet<String> columns =
				// CckIDao.getTableColumnsSet(tableName);
				HashSet<DataBaseColumn> columnDetails = cckIDao
						.getTableColumnDetsilsSet(tableName);
				if (null != columnDetails) {
					List<SyCckTypeField> list = syCckTypeFieldDao
							.findByHql(
									"from SyCckTypeField t where t.sctGuid =? and t.delFlag <> 1 order by t.fieldListsortno ",
									ccktypeguid);
				 setBaseTypes(list, params,  type );
					// 添加 基础信息
				 primaryKey=(String) params.get("primaryKey");
					if (null == primaryKey) {
						return false;
					}
//					 delFlagColumn =(String) params.get("deletemodecolumn");
					 createByColumn = (String) params.get( COLUMN_CREATE_BY );
//					 sctGuidColumn =(String) params.get("sctGuidColumn");	
						if (null != createByColumn
								// && StringUtils.equals("1", type.getAuthority())
								) {// q权限类型1的时候加上用户名为查询条件
									params.put("authority", type.getAuthority());
									params.put("createByColumn", createByColumn);
								} else {
									params.put("authority", 0);
								}

					StringBuilder selescFieldssb = new StringBuilder();
					/** list中显示字段 */
					List<SyCckTypeField> listtoshow = new ArrayList<SyCckTypeField>();
					/** 搜索条件字段用于创建// input等 */
					List<SyCckTypeField> searchs = new ArrayList<SyCckTypeField>();			
					/** 搜索条件的MAP用于填充 sql条件 */
					List<HashMap<String, Object>> searchsMap = new ArrayList<HashMap<String, Object>>();
					/**搜索条件的MAP */
					List<HashMap<String, Object>> searchsMap2 = new ArrayList<HashMap<String, Object>>();
					/**码表字段，搜索结果 需要转换 */ 
					List<SyCckTypeField> codeFields = new ArrayList<SyCckTypeField>();																  
					/**  referenceFields字段 */												 
					List<SyCckTypeField> referenceFields = new ArrayList<SyCckTypeField>();
					/** referenceFields用Map */				
					List<Map<String, String>> referenceFieldsMaps = new ArrayList<Map<String, String>>(); 

					HashMap<String, Map<String, String>> referenceSQLMap = new HashMap<String, Map<String, String>>();
					for (int index = 0; index < list.size(); index++) {
						SyCckTypeField syCckTypeField = list.get(index);
						String fieldType = syCckTypeField.getFieldType();
						int fieldTypeInt = -1;
						if (StringUtils.isNumeric(fieldType)) {
							fieldTypeInt = Integer.parseInt(fieldType);
						}
						selescFieldssb.append("t.").append(syCckTypeField.getFieldColumn())
								.append(" ,");

						// if (syCckTypeField.getFieldAlias() != null ) { //TODO
						// 是否需要加上 alias
						// sb.append(syCckTypeField.getFieldColumn()).append(" ").append(syCckTypeField.getFieldAlias()).append(" ,");
						// }

						// sb.append("t.").append(syCckTypeField.getFieldColumn()).append(" ").append(syCckTypeField.getFieldAlias()
						// != null ?
						// syCckTypeField
						// .getFieldAlias() : "").append(",");
						if (SyCckUtil.TYPE_INT_PK == fieldTypeInt) {
							selescFieldssb.append("t.")
									.append(syCckTypeField.getFieldColumn())
									.append(" \"__ID__\" ,");
						} else if (SyCckUtil.TYPE_INT_PARENT_ID == fieldTypeInt) {
							selescFieldssb.append("t.")
									.append(syCckTypeField.getFieldColumn())
									.append(" \"__PID__\" ,");
						}

						// else if (SyCckUtil.TYPE_INT_NAME==fieldTypeInt)
						// {//TODO gai
						// sb.append("t.").append(syCckTypeField.getFieldColumn())
						// .append("  \"__NAME__\",");
						// }
						String sqlString = null;
						if ("1".equals(syCckTypeField.getFieldIslistdisplay())) {// 是否在list中显示
							listtoshow.add(syCckTypeField);
							if (SyCckUtil.TYPE_INT_CODE == fieldTypeInt) {// TODO
								// 更改
								if (StringUtils.isNotBlank(syCckTypeField
										.getFieldTypeReference())
										&& null != syCodeService
												.getSyCodeByTypeName(syCckTypeField
														.getFieldTypeReference())) {
									SyCckTypeField temp = new SyCckTypeField();
									// if (StringUtils.isBlank(syCckTypeField
									// .getFieldAlias())) {
									temp.setFieldColumn(syCckTypeField
											.getFieldColumn().toUpperCase());
									// } else {
									// temp.setFieldColumn(syCckTypeField
									// .getFieldAlias().toUpperCase());
									//
									// }
									temp.setFieldTypeReference(syCckTypeField
											.getFieldTypeReference());

									codeFields.add(temp);
								} else {//未找到字段日志
									doNotFoundReferenceLog(tableName,
											syCckTypeField);
								}

							} else if (SyCckUtil.TYPE_INT_REFERENCE == fieldTypeInt) {
								createReferenceParams(syCckTypeField, sqlString, fieldTypeInt, referenceFields, referenceFieldsMaps, searchsMap2, sqlString);
							}

						}
						if ("1".equals(syCckTypeField.getFieldIssearchfield())) {

							HashMap<String, Object> map = new HashMap<String, Object>(
									4);
							map.put("name", syCckTypeField.getFieldColumn()
									.toUpperCase());
							if (SyCckUtil.TYPE_INT_REFERENCE == fieldTypeInt) {
								if (2 != syCckTypeField
										.getFieldTypeReferenceType()) {
									map.put("type", "2");
									searchsMap.add(map);
								}
							} else {
								if (SyCckUtil.TYPE_INT_TEXT == fieldTypeInt) {// TODO
									// 更改1:like ,2:=
									map.put("type", "1");
								}  
								else {
									map.put("type", "2");
								}
								searchsMap.add(map);
							}
							searchs.add(syCckTypeField);
						}
						if (SyCckUtil.TYPE_INT_REFERENCE == fieldTypeInt
								&& checkReferenceSQL(sqlString)) {
							Map<String, String> tempreferenceMap = getAutocompleteSQLMap(
									sqlString, index);
							referenceSQLMap.put(syCckTypeField.getSctfGuid(),
									tempreferenceMap);
						}

					}
					StringBuilder fromSqlBuilder = new StringBuilder(tableName);
					fromSqlBuilder.append(" t ");
					StringBuilder whereSqlBuilder = new StringBuilder(" ( ");
					boolean haveWhere = false;
					for (Map<String, String> map : referenceFieldsMaps) {
						selescFieldssb.append(map.get("selectSql"));
						fromSqlBuilder.append(" ").append(map.get("joinType"));//join 类型
						fromSqlBuilder.append(map.get("joinSql"));//join 语句
						fromSqlBuilder.append(map.get("onSql"));// on语句
						String whereString = map.get("whereSql");//join on 后面的 where 语句
						if (null != whereString) {
							if (haveWhere) {
								whereSqlBuilder.append(" AND ");
							}
							haveWhere = true;
							whereSqlBuilder.append(whereString);
						}
					}
					if (checkIsNameColumnRight(list,type)) {//检查 用于显示tree 的name字段是否正确
						selescFieldssb.append("t.").append(nameColumn)
								.append("  \"__NAME__\",");
					} else {//不正确则显示主键。
						selescFieldssb.append("t.").append(primaryKey)
								.append("  \"__NAME__\",");
					}
					selescFieldssb.deleteCharAt(selescFieldssb.length() - 1);
					params.put("fromSql", fromSqlBuilder.toString());
					params.put("selectFields", selescFieldssb.toString());
					params.put("tableName", tableName);
					params.put("fieldlist", list);
					params.put("referenceSQLMap", referenceSQLMap);

					JsonConfig config = new JsonConfig();
					config.setIgnoreDefaultExcludes(false);
					config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
					config.registerJsonValueProcessor(Date.class,
							new DateJsonValueProcessor("yyyy-MM-dd"));  
					config.registerJsonValueProcessor(java.sql.Date.class,
							new DateJsonValueProcessor("yyyy-MM-dd"));
					
					params.put("fieldsToShow",
							JSONSerializer.toJSON(listtoshow, config).toString());
					params.put("searchs", JSONSerializer
							.toJSON(searchs, config).toString());
					params.put("codeFields",
							JSONSerializer.toJSON(codeFields, config));
					params.put("searchlist", searchsMap);
					params.put("searchlist2", searchsMap2);
					params.put("primaryKey", primaryKey);
					params.put("showType", type.getShowType());
					params.put("defaultOrderBySql",
							getDefaultOrderBySql(ccktypeguid));// order sql
					if (null != params.get("parentIdColumn")) {
						String parentIdType = type.getParentIdType();
						params.put("parentIdType", parentIdType);
					}

					ArrayList<Map<String, Object>> listnew = new ArrayList<Map<String, Object>>(
							list.size());
					for (DataBaseColumn column : columnDetails) {
						String name = column.getColumnName();
						Map<String, Object> subMap = new HashMap<String, Object>(
								3);
						// 日期 类型的 转换
						if (!name.equals(params.get("primaryKey"))
								&& !name.equals(params.get("guidColumn"))
								&& !name.equals(params.get("sctGuidColumn"))
								&& !name.equals(params.get("masterIdColumn"))
								&& !name.equals(params.get("parentIdColumn"))) {
							subMap.put("name", name.toUpperCase());
							int dbtype = column.getColumnType();
							subMap.put("dbtype", dbtype);
							if (dbtype == Types.DATE
									|| dbtype == Types.TIMESTAMP) {// 数据库类型为date时
								// 要转换为date类型
								setIsDateType(name, searchsMap);
							}
							listnew.add(subMap);
						}

					}
					params.put("maplist", listnew);// 查询用 map list
					params.put("uniqueSet", getUniqueFieldSet(list));// 唯一字段 Set
					StdMemCacheUtil.getMemCachedClient().set(
							KEY_GEN_PARAMS + ccktypeguid, params);
					return true;
				}

			}

		}

		return flag;

	}

	/**
	 *	新建引用的 参数 
	 *  @param syCckTypeField syCckTypeField
	 *  @param sqlString sql
	 *  @param index 第几个
	 *  @param referenceFields 传入list
	 *  @param referenceFieldsMaps map
	 *  @param searchsMap2 搜索条件maplist
	 *  @param tableName 表名
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-4-9 上午9:59:04 
	 */
	private boolean createReferenceParams(SyCckTypeField syCckTypeField,
			String sqlString, int index, List<SyCckTypeField> referenceFields,
			List<Map<String, String>> referenceFieldsMaps,
			List<HashMap<String, Object>> searchsMap2, String tableName) {
		// 如果是reference
		String code = syCckTypeField.getFieldTypeReference();
		SyWidgetReference bean = syWidgetReferenceService.getBean(code);
		if (bean == null) {
			return false;
		}
		if (bean.getWidgetType() != null) {
			syCckTypeField.setFieldTypeReferenceType(bean.getWidgetType()
					.intValue());
		}
		if (bean.getAutocompleteConfig() != null) {
			syCckTypeField.setFieldAutocompleteType(bean
					.getAutocompleteConfig().intValue());
		}
		sqlString = bean.getReferenceValue();
		if (StringUtils.isNotBlank(syCckTypeField.getFieldTypeReference())
				&& checkReferenceSQL(sqlString)) {
			SyCckTypeField temp = new SyCckTypeField();
			temp.setFieldColumn(syCckTypeField.getFieldColumn().toUpperCase());
			temp.setFieldTypeReference(syCckTypeField.getFieldTypeReference());
			Map<String, String> fieldReferenceMap = getReferenceSQLMap(
					sqlString, syCckTypeField.getFieldColumn(), index);
			// 1 inner join 其他为 left join
			fieldReferenceMap.put("joinType", INTEGER_ONE.equals(syCckTypeField
					.getFieldJoinType()) ? INNER_JOIN : LEFT_JOIN);
			referenceFields.add(temp);
			referenceFieldsMaps.add(fieldReferenceMap);
			//修改casecade的搜索条件
			if (2 == syCckTypeField.getFieldTypeReferenceType()) { 
				addTableAliasAndReferenceName(syCckTypeField,
						fieldReferenceMap, searchsMap2);
			}
		} else {
			doNotFoundReferenceLog(tableName, syCckTypeField);
			return false;
		}
		return true;

	}

	private void doNotFoundReferenceLog(String tableName,
			SyCckTypeField syCckTypeField) {
		if (logger.isErrorEnabled()) {
			logger.error(tableName + " " + syCckTypeField.getFieldColumn()
					+ " " + syCckTypeField.getFieldTypeReference()
					+ " code 不存在");
		}

	}

	/**
	 *	tree界面显示 字段名 是否正确 （可以不用）
	 *  @param list
	 *  @param type
	 *  @return 是否正确
	 *  
	 * @author zjl 
	 * @date : 2013-4-9 下午1:20:27 
	 */
	private boolean checkIsNameColumnRight(List<SyCckTypeField> list,SyCckType type){
		  boolean isNameColumnRight= false;
		for (SyCckTypeField syCckTypeField : list) {
		
			String columnName = syCckTypeField.getFieldColumn();
			if (StringUtils.equals(columnName, type.getNameColumn())) {// TODO
				    isNameColumnRight = true;
			}
		}
		return isNameColumnRight;
		}
	private void setBaseTypes(List<SyCckTypeField> list,
			Map<String, Object> params,  
			SyCckType type) {
		for (SyCckTypeField syCckTypeField : list) {
			// if (!columns.contains(syCckTypeField.getFieldColumn()
			// .toUpperCase())) {//TODO 检查是否有重复column
			// return false;
			// }
			String columnName = syCckTypeField.getFieldColumn();
	 
			String fieldType = syCckTypeField.getFieldType();
			if (StringUtils.isNumeric(fieldType)) {
				int typeint = Integer.parseInt(fieldType);
				if (SyCckUtil.TYPE_INT_DEL_FLAG == typeint) {
					// 逻辑删除标志
					params.put("deletemode", "1");
					params.put("deletemodecolumn", columnName);
			
				} else if (SyCckUtil.TYPE_INT_CREATE_BY == typeint) {
					params.put(COLUMN_CREATE_BY, columnName);
				} else if (SyCckUtil.TYPE_INT_CREATE_DT == typeint) {
					params.put(COLUMN_CREATE_DT, columnName);
				} else if (SyCckUtil.TYPE_INT_UPDATE_BY == typeint) {
					params.put(COLUMN_UPDATE_BY, columnName);
				} else if (SyCckUtil.TYPE_INT_UPDATE_DT == typeint) {
					params.put(COLUMN_UPDATE_DT, columnName);
				}

				else if (SyCckUtil.TYPE_INT_SCT_GUID == typeint) {
					params.put("sctGuidColumn", columnName);
				} else if (SyCckUtil.TYPE_INT_PK == typeint) {
					params.put("primaryKey", columnName);
					
				} else if (SyCckUtil.TYPE_INT_GUID == typeint) {
					params.put("guidColumn", columnName);
				} else if (SyCckUtil.TYPE_INT_MASTERID == typeint) {
					params.put("masterIdColumn", columnName);
				} else if (SyCckUtil.TYPE_INT_PARENT_ID == typeint) {
					params.put("parentIdColumn", columnName);
				}
			}
		}

	}

	/**
	 * 添加别名 和引用名
	 * 
	 * @param syCckTypeField
	 * @param fieldReferenceMap
	 * @param searchsMap2
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午5:29:07
	 */
	private void addTableAliasAndReferenceName(SyCckTypeField syCckTypeField,
			Map<String, String> fieldReferenceMap,
			List<HashMap<String, Object>> searchsMap2) {
		if ("1".equals(syCckTypeField.getFieldIssearchfield())) {
			String name = syCckTypeField.getFieldColumn().toUpperCase();
			HashMap<String, Object> map = new HashMap<String, Object>(4);
			String column = fieldReferenceMap.get("selectSql").trim();
			column = StringUtils.split(column, " ")[0];
			map.put("name", name);
			map.put("column", column);
			searchsMap2.add(map);
		}

	}

	/**
	 * 获取 条件为唯一 的字段
	 * 
	 * @param list
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:28:45
	 */
	private HashSet<String> getUniqueFieldSet(List<SyCckTypeField> list) {
		HashSet<String> set = new HashSet<String>();
		for (SyCckTypeField syCckTypeField : list) {
			if ("1".equals(syCckTypeField.getFieldIsunique())) {
				set.add(syCckTypeField.getFieldColumn());
			}
		}
		return set;
	}

	/**
	 * 为需要转换的date类型加上type = 3
	 * 
	 * @param name
	 * @param searchsMap
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:29:48
	 */
	private void setIsDateType(String name,
			List<HashMap<String, Object>> searchsMap) {
		for (HashMap<String, Object> hashMap : searchsMap) {
			if (name.equals(hashMap.get("name"))) {
				// hashMap.put("isDate", "true");
				hashMap.put("type", "3");
			}
		}

	}

	/**
	 * 生成 orderby 语句
	 * @param ccktypeguid
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:30:39
	 */
	private String getDefaultOrderBySql(String ccktypeguid) {
		StringBuilder defaultOrderBySql = new StringBuilder();
		List<SyCckTypeField> list2 = syCckTypeFieldDao
				.findByHql(
						"from SyCckTypeField t where t.sctGuid =? and t.orderType is not null and t.delFlag <> 1 order by t.orderOrder asc ",
						ccktypeguid);
		if (list2.size() < 1) {
			return null;
		}
		for (SyCckTypeField syCckTypeField : list2) {
			defaultOrderBySql.append("t.").append(
					syCckTypeField.getFieldColumn());
			if ("1".equals(syCckTypeField.getOrderType())) {
				defaultOrderBySql.append(" desc");
			} else {
				defaultOrderBySql.append(" asc");
			}
			defaultOrderBySql.append(",");
		}
		defaultOrderBySql.deleteCharAt(defaultOrderBySql.length() - 1);
		return defaultOrderBySql.toString();
	}

	/**
	 * 获取 解析 引用 的sql语句
	 * 
	 * @param sqlString
	 * @param fieldColumn
	 * @param index
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:31:02
	 */
	private Map<String, String> getReferenceSQLMap(String sqlString,
			String fieldColumn, int index) {
		try {

			if (StringUtils.isBlank(sqlString)) {
				return null;
			}
			return SelectSqlParserSupport.getReferenceSQLMap(sqlString, "t."
					+ fieldColumn, fieldColumn, index);
		} catch (JSQLParserException e) {
			e.printStackTrace();
			return null;
		}

	}

	// private String getReferenceValue(SyCckTypeField syCckTypeField) {
	// String reference =syCckTypeField.getFieldTypeReference();
	//
	// if (StringUtils.isBlank(reference)) {
	// return null;
	// }
	// Map map=syWidgetReferenceService.getCache(reference);
	// if (null==map) {
	// return null;
	// }
	// SyWidgetReference syWidgetReference=(SyWidgetReference) map.get("bean");
	// if (syWidgetReference==null) {
	// return null;
	// }
	// String sqlString=syWidgetReference.getReferenceValue();
	// if (StringUtils.isBlank(sqlString)) {
	// return null;
	// }
	// return sqlString;
	// }
	/**
	 * 获取 解析 Autocomplete引用 的sql语句
	 * 
	 * @param sqlString
	 * @param index
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:31:30
	 */
	private Map<String, String> getAutocompleteSQLMap(String sqlString,
			int index) {
		try {

			// String sqlString=getReferenceValue(syCckTypeField);
			if (StringUtils.isBlank(sqlString)) {
				return null;
			}
			return SelectSqlParserSupport.getAutocompleteSQLMap(sqlString, "",
					index);

		} catch (JSQLParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 检查引用的sql语句是否符合条件
	 * 
	 * @param sqlString
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:31:55
	 */
	private boolean checkReferenceSQL(String sqlString) {

		if (StringUtils.isBlank(sqlString)) {
			return false;
		}
		try {
			return SelectSqlParserSupport.testSqlText(sqlString);

		} catch (JSQLParserException e) {
			e.printStackTrace();
			return false;
		}

	}

	public SyCodeService getSyCodeService() {
		return syCodeService;
	}

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	/**
	 * 清除缓存
	 * 
	 * @param sctfGuids
	 *            sctfGuid数组
	 * 
	 * @author zjl
	 * @date : 2012-12-26 上午10:16:12
	 */
	public void clearCacheBySctfGuids(String[] sctfGuids) {
		List<String> list = syCckTypeFieldDao.findBySctGuids(sctfGuids);
		for (String string : list) {
			clearCache(string);
		}

	}

	/**
	 * 删除 sctGuid 的缓存
	 * 
	 * @param sctGuid
	 *            sctGuid
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:32:23
	 */
	public void clearCache(String sctGuid) {
		StdMemCacheUtil.getMemCachedClient().delete(KEY_GEN_PARAMS + sctGuid);
		StdMemCacheUtil.getMemCachedClient().delete(KEY_GEN_FORMS + sctGuid);
		StdMemCacheUtil.getMemCachedClient().delete(KEY_GEN_BUTTONS + sctGuid);
	}

	/**
	 * 获取ccktypeguid 的字段
	 * 
	 * @param ccktypeguid
	 * @return 字段列表
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:32:38
	 */
	public List<SyCckTypeField> getFields(String ccktypeguid) {
		SyCckType type = syCckTypeDao.findById(ccktypeguid);

		if (null != type) {
			String tableName = type.getTypetable();
			if (StringUtils.isNotBlank(tableName)) {
				HashSet<String> columns = cckIDao.getTableColumnsSet(tableName);
				List<SyCckTypeField> list = syCckTypeFieldDao
						.findByHql(
								"from SyCckTypeField t where t.sctGuid =? and t.delFlag <> 1 order by t.fieldFormposition ",
								ccktypeguid);
				for (SyCckTypeField syCckTypeField : list) {
					if (!columns.contains(syCckTypeField.getFieldColumn()
							.toUpperCase())) {// 检查是否有重复column
						return null;
					}
				}
				return list;
			}
		}
		return null;
	}

	/**
	 * 插入数据
	 * 
	 * @param map
	 *            参数Map
	 * @return
	 */
	public String insert(Map<Object, Object> map) {
		if (createFormParams(map, 0)) {
			return cckIDao.insert(map);
		} else
			return null;

		// for (SyCckTypeField syCckTypeField : list) {
		//
		// }

	}

	/**
	 * 更新数据
	 * 
	 * @param map
	 * @return -1为失败，其他为更新条数
	 */
	public int update(Map<Object, Object> map) {

		if (createFormParams(map, 1)) {

			return cckIDao.update(map);
		} else
			return -1;

		// for (SyCckTypeField syCckTypeField : list) {
		//
		// }

	}

	/**
	 * 更新 ParentId
	 * 
	 * @param map
	 * @return
	 * 
	 * @author zjl
	 * @date : 2012-12-26 上午10:17:20
	 */
	public int updateParentId(Map<Object, Object> map) {

		if (createFormParams(map, 2)) {

			return cckIDao.update(map);
		} else
			return -1;

	}

	/**
	 * 生成 修改 新建 等操作的 参数Map
	 * 
	 * @param map
	 *            原参数map
	 * @param i
	 *            1：更新，0：新建
	 * @return true 成功false 字段错误
	 * 
	 * @author zjl
	 * @date : 2012-12-20 下午1:54:54
	 */
	private boolean createFormParams(Map<Object, Object> map, int i) {

		Date d = new Date();

		String sctGuid = (String) map.get("SCT_GUID");
		// TODO a delete cache
		// StdMemCacheUtil.getMemCachedClient().delete(KEY_GEN_PARAMS +
		// sctGuid);
		@SuppressWarnings("unchecked")
		HashMap<String, Object> params = (HashMap<String, Object>) StdMemCacheUtil
				.getMemCachedClient().get(KEY_GEN_PARAMS + sctGuid);
		if (null == params) {
			params = new HashMap<String, Object>();

			if (!createParamsForList(params, sctGuid)) {
				return false;
			}
		}
		// 检查unique 更新ParentId 不检查 除非
		String id = (String) map.get(params.get("primaryKey"));
		@SuppressWarnings("unchecked")
		HashSet<String> set = (HashSet<String>) params.get("uniqueSet");
		if (i == 0) {
			id = " ";
		}
		if (!checkUniqueBeforeUpdateOrCreate(id, sctGuid, set, map)) {
			return false;
		}

		if (i == 0) {
			map.put(params.get(COLUMN_CREATE_BY), map.get("userName"));
			map.put(params.get(COLUMN_CREATE_DT), d);
			// map.put(params.get("masterIdColumn"), map.get("masterId"));
		} else {
			map.remove(params.get(COLUMN_SCT_GUID)); // 此处已经移除 sct_GUID
														// 之后采用sctGuid 替代
														// map.remove(params.get("primaryKey"));
			// map.remove(params.get("masterId"));
			map.remove("masterId");

		}
		String updateByColumn = (String) params.get(COLUMN_UPDATE_BY);
		String updateDtColumn = (String) params.get(COLUMN_UPDATE_DT);
		map.put(updateByColumn, map.get("userName"));
		map.put(updateDtColumn, d);

		map.put("tableName", params.get("tableName"));
		map.put("primaryKey", params.get("primaryKey"));
		map.put("sctGuidColumn", params.get("sctGuidColumn"));
		map.put("masterIdColumn", params.get("masterIdColumn"));
		map.put("guidColumn", params.get("guidColumn"));
		map.put("sctGuid", sctGuid);
		if ("1".equals(params.get("deletemode"))) {// 新建的时候 填充
			// 删除标志，更新时不能更新，删除采用别的方法。
			String deletemodecolumn = (String) params.get("deletemodecolumn");
			if (i == 0) {
				map.put(StringUtils.isNotBlank(deletemodecolumn) ? deletemodecolumn
						: DEL_FLAG, 0);
			} else {
				map.remove(deletemodecolumn);// 去掉删除标志，更新时不能更新 删除标志。
			}
		}

		// if (i != 2) {
		// List<SyCckTypeField> list = (List<SyCckTypeField>)
		// params.get("fieldList");
		@SuppressWarnings("unchecked")
		ArrayList<Map<String, Object>> mapList = (ArrayList<Map<String, Object>>) params
				.get("maplist");

		for (Map<String, Object> map2 : mapList) {// 填入 更新字段 name value
			Object vObject = map.get(map2.get("name"));
			if (i == 2) {
				String nameString = (String) map2.get("name");
				boolean isub = nameString.equals(updateByColumn);
				boolean isud = nameString.equals(updateDtColumn);
				if (!isub && !isud) {
					continue;
				}
			}
			if (null != vObject) {
				int dbtype = ((Integer) map2.get("dbtype")).intValue();
				if (dbtype == Types.DATE || dbtype == Types.TIMESTAMP) {// 数据库类型为date时
																		// 要转换为date类型
					if (vObject.getClass().toString()
							.equals(String.class.toString())) {
						try {
							Date date = DateUtils.parseDate((String) vObject,
									format);
							map2.put("value", date);
						} catch (ParseException e) {
							e.printStackTrace();
							map2.put("value", null);
							continue;
						}
					} else {
						map2.put("value", vObject);
					}

				} else {
					map2.put("value", vObject);
				}
			}

		}
		// 主键生产方式 0新建
		if (i == 0) {
			map.put("SCC_GUID",
					SyCckUtil.generateID((String) params.get("tableName")));
		}
		String parentIdColumn = (String) params.get("parentIdColumn");

		if (StringUtils.isNotBlank(parentIdColumn)) {
			map.put("parentIdColumn", parentIdColumn);
			String parentId = (String) map.get(parentIdColumn);
			if (i == 2) {
				parentId = (String) map.get("parentId");
			}
			if (StringUtils.isBlank(parentId)) {
				Object parentIdType = params.get("parentIdType");
				if ("1".equals(parentIdType)) {
					parentId = (String) map.get("SCC_GUID");
				} else if ("2".equals(parentIdType)) {
					parentId = "0";
				}
			}

			if (i == 2) {
				Map<String, Object> parentIdMap = new HashMap<String, Object>(2);
				parentIdMap.put("name", parentIdColumn);
				parentIdMap.put("value", parentId);
				mapList.add(parentIdMap);
			} else if (i == 0) {
				map.put("parentId", parentId);
			} else {
				map.remove("parentId");
			}
		}

		map.put("valuelist", mapList);

		// }else {
		// ArrayList<Map<String, Object>> mapList = new
		// ArrayList<Map<String,Object>>();
		// Map<String, Object> map2=new HashMap<String, Object>();
		//
		// map.put("valuelist", mapList);
		// }

		return true;
	}

	/**
	 * 新建，更新 前检查 唯一性。
	 * 
	 * @param id
	 * @param sctGuid
	 * @param set
	 * @param map
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-4-2 下午2:30:41
	 */
	private boolean checkUniqueBeforeUpdateOrCreate(String id, String sctGuid,
			HashSet<String> set, Map<Object, Object> map) {
		for (String string : set) {
			Object value = map.get(string);
			if (!checkUnique(sctGuid, id, map, (String) value, string)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param sctGuid
	 * @param sccGuid
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-4-2 下午2:31:19
	 */
	public JSON findById(String sctGuid, String sccGuid) {
		// TODO a delete cache
		// StdMemCacheUtil.getMemCachedClient().delete(
		// KEY_GEN_PARAMS + sctGuid );
		Map params = (Map) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_PARAMS + sctGuid);
		if (null == params) {
			params = new HashMap<String, Object>();

			if (!createParamsForList(params, sctGuid)) {
				return null;
			}
		}
		Map<String, Object> subMap = new HashMap<String, Object>(7);
		subMap.put("tableName", params.get("tableName"));
		subMap.put("primaryKey", params.get("primaryKey"));
		subMap.put("sctGuidColumn", params.get("sctGuidColumn"));
		// subMap.put("masterIdColumn", params.get("masterIdColumn"));
		// subMap.put("masterId", masterId);

		subMap.put("sccGuid", sccGuid);
		subMap.put("sctGuid", sctGuid);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.registerJsonValueProcessor(java.sql.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd")); // date
															// processor
															// register
		return JSONObject.fromObject(cckIDao.findById(subMap), config);
	}

	public int delete(Map<String, Object> map) {
		// TODO a加入userName 字段名判断
		// Date d = new Date();

		String sctGuid = (String) map.get("sctGuid");
		String[] sccGuids = (String[]) map.get("sccGuids");
		Map params = (Map) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_PARAMS + sctGuid);
		if (params == null) {
			params = new HashMap<String, Object>();
			if (!createParamsForList(params, sctGuid)) {
				return -1;
			}
		}
		map.put("sctGuidColumn", params.get("sctGuidColumn"));
		map.put("masterIdColumn", params.get("masterIdColumn"));

		map.put("createByColumn", params.get("createByColumn"));// create user
																// name column
																// name
		map.put("primaryKey", params.get("primaryKey"));
		map.put("authority", params.get("authority"));
		map.put("tableName", params.get("tableName"));
		String delFlagColumn = (String) params.get("deletemodecolumn");
		if (StringUtils.isNotBlank(delFlagColumn)) {
			map.put("deletemodecolumn", delFlagColumn);
		}
		if (params.get("parentIdColumn") != null) {
			map.put("parentIdColumn", params.get("parentIdColumn"));
			return deleteCascade(sccGuids, map);
		} else {

			if (StringUtils.isNotBlank(delFlagColumn)) {// 逻辑删除

				map.put("deletecolumn", delFlagColumn);
				return cckIDao.deleteContents(map);
			} else {// 物理删除
				// map.get("sccGuids");
				return cckIDao.deleteContentsPhysical(map);
			}
		}

	}

	private int deleteCascade(String[] sccGuids, Map<String, Object> map) {
		String delFlagColumn = (String) map.get("deletemodecolumn");
		boolean isPhysicalDelete = true;
		if (StringUtils.isNotBlank(delFlagColumn)) {
			map.put("deletecolumn", delFlagColumn);
			isPhysicalDelete = false;
		}
		int totaldeleted = 0;
		if (isPhysicalDelete) {// 物理删除
			totaldeleted += cckIDao.deleteContentsPhysical(map);

		} else {// 逻辑删除
			// map.get("sccGuids");
			totaldeleted += cckIDao.deleteContents(map);
		}
		for (String id : sccGuids) {
			deleteCascadeById(id, map, isPhysicalDelete, totaldeleted);
		}

		return totaldeleted;
	}

	private void deleteCascadeById(String id, Map<String, Object> map,
			boolean isPhysicalDelete, int totaldeleted) {
		map.put("pId", id);
		List<String> childrensList = cckIDao.getChildrens(map);
		if (childrensList != null && childrensList.size() > 0) {
			for (String cid : childrensList) {
				deleteCascadeById(cid, map, isPhysicalDelete, totaldeleted);

			}

			map.put("sccGuids", childrensList);
			if (isPhysicalDelete) {// 逻辑删除
				totaldeleted += cckIDao.deleteContentsPhysical(map);

			} else {// 物理删除
				// map.get("sccGuids");
				totaldeleted += cckIDao.deleteContents(map);
			}
		}

	}

	// private String getTotalSccGuidsToDelete(String[] sccGuids,
	// Map<String, Object> map) {
	// // String [] ids=StringUtils.split(sccGuids, ",");
	// String [] ids= sccGuids ;
	// for (String string : ids) {
	//
	// }
	// return ids.toString();
	// }

	/**
	 * 新建 ccktype 并生成 fields,
	 * 
	 * @param syCckType
	 * @return
	 * 
	 * @author zjl
	 * @date : 2012-12-21 下午4:26:08
	 */
	public String addCckType(SyCckType syCckType) {
		String tableName = syCckType.getTypetable();

		if (StringUtils.isNotBlank(tableName)) {

			tableName = StringUtils.upperCase(tableName);
			if (cckIDao.checkTableName(tableName)) {
				syCckTypeDao.add(syCckType);
				String sctGuid = syCckType.getSctGuid();
				ArrayList<SyCckTypeField> syCckTypeFields = CreateFields(
						tableName, sctGuid);
				if (null != syCckTypeFields) {
					for (SyCckTypeField syCckTypeField : syCckTypeFields) {
						syCckTypeFieldDao.add(syCckTypeField);
					}
				}

			} else {
				return TABLE_NOT_FOUND;
			}

		} else {
			return TABLE_NOT_FOUND;
		}
		return null;

	}

	/**
	 * CreateFields 方法
	 * <p>
	 * 方法说明:
	 * </p>
	 * 新建字段
	 * 
	 * @param tableName
	 * 
	 * @author zjl
	 * @param sctGuid
	 * @return
	 * @date : 2012-12-24 下午4:13:27
	 */
	private ArrayList<SyCckTypeField> CreateFields(String tableName,
			String sctGuid) {
		tableName = StringUtils.upperCase(tableName);
		String primaryKey = cckIDao.getPrimaryKeyByTableName(tableName);
		if (StringUtils.isNotBlank(primaryKey)) {
			List<DataBaseColumn> colums = cckIDao
					.getTableColumnDetsilsList(tableName);
			HashMap<String, String> commentsMap = cckIDao
					.getColumnCommentsMap(tableName);
			HashSet<String> indexSet = cckIDao.getIndexByTableName(tableName);
			ArrayList<SyCckTypeField> syCckTypeFields = new ArrayList<SyCckTypeField>(
					colums.size());
			for (int i = 0; i < colums.size(); i++) {

				DataBaseColumn dataBaseColumn = colums.get(i);
				if (primaryKey.equals(dataBaseColumn.getColumnName())) {
					dataBaseColumn.setIsPrimaryKey(true);
				}
				String labelString = commentsMap.get(dataBaseColumn
						.getColumnName());
				if (StringUtils.isBlank(labelString)) {
					labelString = dataBaseColumn.getColumnName();
				}
				dataBaseColumn.setColumnLabel(labelString);// comments => label
				if (indexSet.contains(dataBaseColumn.getColumnName())) {
					dataBaseColumn.setIsUnique(1);
				} else {
					dataBaseColumn.setIsUnique(0);
				}
				SyCckTypeField syCckTypeField = createSyCckTypeField(
						dataBaseColumn, sctGuid, i + 1);
				syCckTypeFields.add(syCckTypeField);
			}
			return syCckTypeFields;

		} else {

		}
		return null;

	}

	private boolean checkGuidColumn(DataBaseColumn dataBaseColumn) {
		int type = dataBaseColumn.getColumnType();
		int size = dataBaseColumn.getColumnDisplaySize();
		if (size >= GUID_SIZE
				&& (type == Types.VARCHAR || Types.NVARCHAR == type)) {

			return true;
		} else if (Types.CHAR == type || Types.NCHAR == type) {
			if (GUID_SIZE == size) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// get string datatype from int datatype
	/*
	 * private static String getDataType(int type, int scale) { String dataType
	 * = "";
	 * 
	 * switch (type) { // case Types.LONGVARCHAR: //-1 // dataType="Long"; //
	 * break; case Types.CHAR: // 1 dataType = "Character"; break; case
	 * Types.NUMERIC: // 2 switch (scale) { case 0: dataType = "Digits"; break;
	 * default: dataType = "Number"; } break; case Types.VARCHAR: // 12 dataType
	 * = "String"; break; case Types.NVARCHAR: // 12 dataType = "String"; break;
	 * case Types.DATE: // 91 dataType = "Date"; break; case Types.TIMESTAMP: //
	 * 93 dataType = "Date"; break; case Types.BLOB: dataType = "Blob"; break;
	 * default: dataType = "String"; } return dataType; }
	 */

	/**
	 * 根据数据库字段类型转换为文本 整数 小数 日期等类型
	 * 
	 * @param type
	 * @param scale
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:39:34
	 */
	private String changeTypeString(int type, int scale) {
		String dataType = null;

		switch (type) {
		// case Types.LONGVARCHAR: //-1
		// dataType="Long";
		// break;
		case Types.CHAR: // 1
			dataType = SyCckUtil.TYPE_TEXT;
			break;
		case Types.NUMERIC: // 2
			switch (scale) {
			case 0:
				dataType = SyCckUtil.TYPE_DIGITS;
				break;
			default:
				dataType = SyCckUtil.TYPE_NUMBER;
			}
			break;
		case Types.VARCHAR: // 12
			dataType = SyCckUtil.TYPE_TEXT;
			break;
		case Types.NVARCHAR: // 12
			dataType = SyCckUtil.TYPE_TEXT;
			break;
		case Types.DATE: // 91
			dataType = SyCckUtil.TYPE_DATE;
			break;
		case Types.TIMESTAMP: // 93
			dataType = SyCckUtil.TYPE_DATE;
			break;
		default:
			dataType = SyCckUtil.TYPE_TEXT;
		}
		return dataType;
	}

	/**
	 * createSyCckTypeField 方法
	 * <p>
	 * 方法说明:
	 * </p>
	 * 生成 SyCckTypeField 对象
	 * 
	 * @param column
	 *            数据库中获取的 column 信息
	 * @param index
	 *            排序
	 * @return
	 * 
	 * @author zjl
	 * @param sctGuid
	 * @date : 2012-12-24 下午4:14:13
	 */
	private SyCckTypeField createSyCckTypeField(DataBaseColumn column,
			String sctGuid, int index) {
		SyCckTypeField field = new SyCckTypeField();
		field.setFieldColumn(column.getColumnName());
		field.setFieldAlias(column.getColumnName());
		field.setDelFlag(0);
		field.setFieldFormposition(index);
		field.setOrderOrder(String.valueOf(index));
		field.setFieldIsformdisplay(TRUE_NUMBER);
		field.setFieldIslistdisplay(TRUE_NUMBER);
		field.setFieldIsrequired(column.getIsNullable() == 0 ? TRUE_NUMBER
				: FALSE_NUMBER);
		// field.setFieldIssearchfield(column.getIsSearchable() ? TRUE_NUMBER:
		// FALSE_NUMBER);
		field.setFieldIssearchfield(FALSE_NUMBER);// 默认不是搜索条件
		field.setFieldIsunique(String.valueOf(column.getIsUnique()));
		field.setFieldLabel(column.getColumnLabel());
		field.setFieldListsortno(index);
		int displaySize = column.getColumnDisplaySize();

		field.setFieldType(getFieldTypeType(column));
		String type = field.getFieldType();
		int typeint = -1;
		if (StringUtils.isNumeric(type)) {
			typeint = Integer.parseInt(type);
		}

		if (SyCckUtil.TYPE_INT_PK == typeint
				|| SyCckUtil.TYPE_INT_SCT_GUID == typeint
				|| SyCckUtil.TYPE_INT_DEL_FLAG == typeint
				|| SyCckUtil.TYPE_INT_GUID == typeint
				|| SyCckUtil.TYPE_INT_CREATE_BY == typeint
				|| SyCckUtil.TYPE_INT_CREATE_DT == typeint
				|| SyCckUtil.TYPE_INT_UPDATE_BY == typeint
				|| SyCckUtil.TYPE_INT_UPDATE_DT == typeint) {// 不在form 和 list
																// 中显示
			field.setFieldIsformdisplay(FALSE_NUMBER);
			field.setFieldIslistdisplay(FALSE_NUMBER);
		} else if (SyCckUtil.TYPE_INT_CREATE_BY == typeint
				|| SyCckUtil.TYPE_INT_CREATE_DT == typeint
				|| SyCckUtil.TYPE_INT_UPDATE_BY == typeint
				|| SyCckUtil.TYPE_INT_UPDATE_DT == typeint) {// 这四种不在form中显示
			field.setFieldIsformdisplay(FALSE_NUMBER);
		}
		// TODO a更改varchar的长度
		// int type2 = column.getColumnType();
		// if ((Types.CHAR == type || Types.VARCHAR ==
		// type)&&!"7".equals(field.getFieldType())) {//CHAR VARCHAR 中文长度/3
		// displaySize/=3;
		// }
		if (SyCckUtil.TYPE_INT_DATE == typeint
				|| SyCckUtil.TYPE_INT_CREATE_DT == typeint
				|| SyCckUtil.TYPE_INT_UPDATE_DT == typeint) { // date类型不用添加最大长度
			field.setFieldMaxlength(null);
		} else {
			field.setFieldMaxlength(String.valueOf(displaySize));
		}

		field.setSctGuid(sctGuid);

		return field;

	}

	private String getFieldTypeType(DataBaseColumn column) {
		int colunType = column.getColumnType();
		int scale = column.getScale();
		String columnName = column.getColumnName();
		if (column.isPrimaryKey) {
			if (checkIsGuid(column)) {
				return SyCckUtil.TYPE_PK;
			} else {
				return null;
			}

		} else if ("SCT_GUID".equals(columnName)) {
			if (checkIsGuid(column)) {
				return SyCckUtil.TYPE_SCT_GUID;
			} else {
				return null;
			}

		} else if ("CREATE_BY".equals(columnName)
				|| "CREATEBY".equals(columnName)
				|| "CREATEUSER".equals(columnName)) {
			if (checkIsUserName(column)) {
				return SyCckUtil.TYPE_CREATE_BY;
			} else {
				return null;
			}
		} else if ("CREATE_DT".equals(columnName)
				|| "CREATEDT".equals(columnName)
				|| "CREATEDATE".equals(columnName)) {
			if (checkIsDate(column)) {
				return SyCckUtil.TYPE_CREATE_DT;
			} else {
				return null;
			}
		} else if ("UPDATE_BY".equals(columnName)
				|| "UPDATEBY".equals(columnName)
				|| "UPDATEUSER".equals(columnName)) {
			if (checkIsUserName(column)) {
				return SyCckUtil.TYPE_UPDATE_BY;
			} else {
				return null;
			}
		} else if ("UPDATE_DT".equals(columnName)
				|| "UPDATEDT".equals(columnName)
				|| "UPDATEDATE".equals(columnName)) {
			if (checkIsDate(column)) {
				return SyCckUtil.TYPE_UPDATE_DT;
			} else {
				return null;
			}
		} else if ("DEL_FLAG".equals(columnName)
				|| "DELFLAG".equals(columnName)
				|| "ISDELETED".equals(columnName)) {
			if (checkIsDelFlag(column)) {
				return SyCckUtil.TYPE_DEL_FLAG;
			} else {
				return null;
			}
		} else if ("PARENT_ID".equals(columnName)) {
			if (checkIsGuid(column)) {
				return SyCckUtil.TYPE_PARENT_ID;
			} else {
				return null;
			}
		} else if ("GUID".equals(columnName)) {
			if (checkIsGuid(column)) {
				return SyCckUtil.TYPE_GUID;
			} else {
				return null;
			}
		} else {

			return changeTypeString(colunType, scale);
		}
	}

	/**
	 * 是否是 DelFlag类型 （number，varchar类型 长度至少1， 不能是char，char的话只能长度为1）
	 * 
	 * @param column
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-4-2 下午2:13:02
	 */
	private boolean checkIsDelFlag(DataBaseColumn column) {
		// number，varchar类型 长度至少1， 不能是char，char的话只能长度为1
		int type = column.getColumnType();
		int size = column.getColumnDisplaySize();
		int scale = column.getScale();
		if ((scale == 0 && size >= 1 && (type == Types.NUMERIC || type == Types.VARCHAR))
				|| (type == Types.CHAR && size == 1)) {
			return true;
		}
		return false;

	}

	/**
	 * 检查该字段是否是 Date类型
	 * 
	 * @param column
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-4-2 下午2:08:06
	 */
	private boolean checkIsDate(DataBaseColumn column) {
		int type = column.getColumnType();
		// TODO 是否要加上String类型的
		if (Types.DATE == type || Types.TIMESTAMP == type) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 检查是否是
	 * 
	 * @param column
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-4-2 下午2:13:40
	 */
	private boolean checkIsUserName(DataBaseColumn column) {
		int type = column.getColumnType();
		int size = column.getColumnDisplaySize();
		if (size >= USER_NAME_SIZE
				&& (type == Types.VARCHAR || Types.NVARCHAR == type)) {

			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断该字段是否是 GUID 类型
	 * 
	 * @param column
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午5:26:05
	 */
	private boolean checkIsGuid(DataBaseColumn column) {
		int type = column.getColumnType();
		int size = column.getColumnDisplaySize();
		if (size >= GUID_SIZE
				&& (type == Types.VARCHAR || Types.NVARCHAR == type)) {

			return true;
		} else if (Types.CHAR == type || Types.NCHAR == type) {
			if (GUID_SIZE == size) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void deleteType(String id) {
		syCckTypeDao.delete(id);
		syCckTypeFieldDao.deleteBySctGuid(id);

	}

	@Deprecated
	public List<Map<String, Object>> getReference(String sctGuid,
			String sctfGuid) {
		// TODO a delete cache
		// StdMemCacheUtil.getMemCachedClient().delete(
		// KEY_GEN_PARAMS + sctGuid );
		Map params = (Map) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_PARAMS + sctGuid);
		if (null == params) {
			params = new HashMap<String, Object>();

			if (!createParamsForList(params, sctGuid)) {
				return null;
			}
		}
		HashMap<String, HashMap<String, String>> referenceSQLMap = (HashMap<String, HashMap<String, String>>) params
				.get("referenceSQLMap");
		String sql = referenceSQLMap.get(sctfGuid).get("originSql");
		if (StringUtils.isNotBlank(sql)) {
			return cckIDao.getReference(sql);
		} else {
			return new ArrayList<Map<String, Object>>();
		}

	}

	@Deprecated
	public List<Map<String, Object>> getAutocomplete(String sctGuid,
			String sctfGuid, Map map) {
		// TODO Auto-generated method stub
		Map params = (Map) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_PARAMS + sctGuid);
		if (null == params) {
			params = new HashMap<String, Object>();

			if (!createParamsForList(params, sctGuid)) {
				return null;
			}
		}
		HashMap<String, HashMap<String, String>> referenceSQLMap = (HashMap<String, HashMap<String, String>>) params
				.get("referenceSQLMap");
		HashMap<String, String> queryMap = referenceSQLMap.get(sctfGuid);
		// map.put("selectSql", queryMap.get("selectSql"));
		// map.put("orderBySql", queryMap.get("orderBySql"));
		// map.put("fromSql", queryMap.get("fromSql"));
		// map.put("whereSql", queryMap.get("whereSql"));
		// map.put("selectSql", queryMap.get("selectSql"));
		// map.put("selectSql", queryMap.get("selectSql"));
		queryMap.put("q", (String) map.get("q"));
		queryMap.put("limit", (String) map.get("limit"));

		return cckIDao.getAutocomplete(queryMap);

	}

	public List<Map> queryAll(Map map, String ccktypeguid) {
		return cckIDao.queryAll(map);
	}

	public SyWidgetReferenceService getSyWidgetReferenceService() {
		return syWidgetReferenceService;
	}

	public void setSyWidgetReferenceService(
			SyWidgetReferenceService syWidgetReferenceService) {
		this.syWidgetReferenceService = syWidgetReferenceService;
	}

	public boolean checkUnique(String sctGuid, String id, Map map, String code,
			String column) {
		// TODO a delete cache
		// StdMemCacheUtil.getMemCachedClient().delete(
		// KEY_GEN_PARAMS + sctGuid );
		Map params = (Map) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_PARAMS + sctGuid);
		if (null == params) {
			params = new HashMap<String, Object>();

			if (!createParamsForList(params, sctGuid)) {
				return false;
			}
		}
		// String sccGuid=(String) map.get(params.get("primaryKey"));
		if (StringUtils.isBlank(id)) {
			id = " ";

		}
		Map<String, Object> subMap = new HashMap<String, Object>(7);
		subMap.put("tableName", params.get("tableName"));
		subMap.put("primaryKey", params.get("primaryKey"));
		subMap.put("deletemodecolumn", params.get("deletemodecolumn"));
		subMap.put("masterIdColumn", params.get("masterIdColumn"));
		subMap.put("sctGuidColumn", params.get("sctGuidColumn"));

		subMap.put("sctGuid", sctGuid);
		subMap.put("id", id);
		subMap.put("column", column);
		subMap.put("columnvalue", code);
		List<Map<String, Object>> list = cckIDao.checkUnique(subMap);
		if (list == null || list.size() == 0) {
			return true;
		} else
			return false;

	}

	public Map<String, Object> getCachedCckTypeParamsForList(String sctGuid) {
		Map params = (Map) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_PARAMS + sctGuid);
		if (null == params) {
			params = new HashMap<String, Object>();

			if (!createParamsForList(params, sctGuid)) {
				return null;
			}
		}
		return params;
	}

	public Map<String, Object> getCachedCckTypeParamsForListByCode(String code) {

		String sctGuid = (String) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_CODE_TO_ID + code);
		if (StringUtils.isBlank(sctGuid)) {
			List<String> list = syCckTypeDao.findByHqlNoEntityType(
					"select t.sctGuid from SyCckType t where t.code =?", code);
			if (list == null || list.size() < 1) {
				return null;
			} else {
				sctGuid = list.get(0);
				StdMemCacheUtil.getMemCachedClient().set(
						KEY_GEN_CODE_TO_ID + code, sctGuid);
			}
		}
		Map params = (Map) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_PARAMS + sctGuid);

		if (null == params) {
			params = new HashMap<String, Object>();

			if (!createParamsForList(params, sctGuid)) {
				return null;
			}
		}
		return params;
	}

	/**
	 * 获取cck 缓存 by code
	 * 
	 * @param code
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-29 上午10:51:42
	 */
	public String getCachedCckTypeGuidForListByCode(String code) {
		StdMemCacheUtil.getMemCachedClient().delete(KEY_GEN_CODE_TO_ID + code);
		String sctGuid = (String) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_CODE_TO_ID + code);
		if (StringUtils.isBlank(sctGuid)) {
			@SuppressWarnings("unchecked")
			List<String> list = syCckTypeDao
					.findByHqlNoEntityType(
							"select t.sctGuid from SyCckType t where t.code =? and t.delFlag <> '1' ",
							code);
			if (list == null || list.size() < 1) {
				return null;
			} else {
				sctGuid = list.get(0);
				StdMemCacheUtil.getMemCachedClient().set(
						KEY_GEN_CODE_TO_ID + code, sctGuid);
			}
		}
		return sctGuid;
	}

	// public Map<String, Object> getCachedCckTypeByCode(String code) {
	//
	// String sctGuid = (String) StdMemCacheUtil.getMemCachedClient().get(
	// KEY_GEN_CODE_TO_ID + code);
	// if (StringUtils.isBlank(sctGuid)) {
	// List<String> list = syCckTypeDao.findByHqlNoEntityType(
	// "select t.sctGuid from SyCckType t where t.code =?", code);
	// if (list == null || list.size() < 1) {
	// return null;
	// } else {
	// sctGuid = list.get(0);
	// StdMemCacheUtil.getMemCachedClient().set(
	// KEY_GEN_CODE_TO_ID + code,sctGuid);
	// }
	// }
	// Map params = (Map) StdMemCacheUtil.getMemCachedClient().get(
	// KEY_GEN_PARAMS + sctGuid);
	//
	// if (null == params) {
	// params = new HashMap<String, Object>();
	//
	// if (!createParamsForList(params, sctGuid)) {
	// return null;
	// }
	// }
	// return params;
	// }

	/**
	 * 检查 code 是否唯一
	 * 
	 * @param code
	 * @param sctGuid
	 * @return
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午3:40:16
	 */
	public boolean checkCckTypeCodeUnique(String code, String sctGuid) {
		List<SyCckType> list = syCckTypeDao
				.findByHql(
						"from SyCckType t where t.code = ? and t.sctGuid <> ? and t.delFlag <> '1'",
						code, sctGuid);
		if (list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除 cckcode 对应 sctGuid 的缓存
	 * 
	 * @param code
	 * 
	 * @author zjl
	 * @date : 2013-3-28 下午5:25:08
	 */
	public void clearCodeCache(String code) {

		StdMemCacheUtil.getMemCachedClient().delete(KEY_GEN_CODE_TO_ID + code);

	}

	public SyCckTypeButtonDaoImpl getSyCckTypeButtonDao() {
		return syCckTypeButtonDao;
	}

	public void setSyCckTypeButtonDao(SyCckTypeButtonDaoImpl syCckTypeButtonDao) {
		this.syCckTypeButtonDao = syCckTypeButtonDao;
	}

	public JSON getButtonCache(String sctGuid) {
		return (JSON) StdMemCacheUtil.getMemCachedClient().get(
				KEY_GEN_PARAMS_BUTTON + sctGuid);
	}

	public void setButtonCache(String sctGuid, JSON jp) {
		StdMemCacheUtil.getMemCachedClient().set(
				KEY_GEN_PARAMS_BUTTON + sctGuid, jp);
	}

	public void clearButtonCache(String sctGuid) {
		StdMemCacheUtil.getMemCachedClient().delete(
				KEY_GEN_PARAMS_BUTTON + sctGuid);
	}

}
