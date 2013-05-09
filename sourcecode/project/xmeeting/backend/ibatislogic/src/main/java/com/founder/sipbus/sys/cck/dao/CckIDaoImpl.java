package com.founder.sipbus.sys.cck.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.BaseIDao;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.sys.cck.vo.DataBaseColumn;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMapping;

@Component
public class CckIDaoImpl extends BaseIDao {
	private Logger logger = LoggerFactory.getLogger(CckIDaoImpl.class);

	/**
	 *getTableColumns 方法
	 * <p>方法说明:</p>
	 *	从resultmetadata 获取 table的 column ArrayList<String>
	 *  @param tableName
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-27 上午9:46:33 
	 */
	public ArrayList<String> getTableColumns(String tableName) {
		ArrayList<String> column = null;
		try {
			Connection con = this.getSqlMapClient().getDataSource()
					.getConnection();
			Statement stmt = con.createStatement();
			StringBuilder stringBuilder = new StringBuilder("select * from ");
			stringBuilder.append(tableName).append(" where 1=2");
			ResultSet rs = stmt.executeQuery(stringBuilder.toString());
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int columnCount = rsMetaData.getColumnCount();
			column = new ArrayList<String>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				column.add(rsMetaData.getColumnName(i));
				if (logger.isDebugEnabled()) {
					logger.debug("field Name:" + rsMetaData.getColumnName(i)
							+ " field Type:" + rsMetaData.getColumnTypeName(i)
							+ " size:" + rsMetaData.getColumnDisplaySize(i));
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return column;

	}

	public HashSet<String> getTableColumnsSet(String tableName) {
		HashSet<String> column = null;
		try {
			Connection con = this.getSqlMapClient().getDataSource()
					.getConnection();

			StringBuilder stringBuilder = new StringBuilder("select * from ");
			stringBuilder.append(tableName).append(" where 1=2");
			// Statement stmt = con.createStatement();
			PreparedStatement stmt = con.prepareStatement(stringBuilder
					.toString());

			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int columnCount = rsMetaData.getColumnCount();
			column = new HashSet<String>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				column.add(rsMetaData.getColumnName(i));
//				System.out.println(rsMetaData.getColumnLabel(i) + " "
//						+ rsMetaData.getColumnClassName(i) + " "
//						+ rsMetaData.getColumnDisplaySize(i) + " "
//						+ rsMetaData.getPrecision(i) + " "
//						+ rsMetaData.getColumnType(i) + " "
//						+ rsMetaData.getColumnTypeName(i));
				if (logger.isDebugEnabled()) {
					logger.debug("field Name:" + rsMetaData.getColumnName(i)
							+ " field Type:" + rsMetaData.getColumnTypeName(i)
							+ " size:" + rsMetaData.getColumnDisplaySize(i));
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return column;

	}

	public HashSet<DataBaseColumn> getTableColumnDetsilsSet(String tableName) {
		tableName = tableName.toUpperCase();
		HashSet<DataBaseColumn> columns = null;
		try {
			Connection con = this.getSqlMapClient().getDataSource()
					.getConnection();

			StringBuilder stringBuilder = new StringBuilder("select * from ");
			stringBuilder.append(tableName).append(" where 1=2");
			// Statement stmt = con.createStatement();
			PreparedStatement stmt = con.prepareStatement(stringBuilder
					.toString());

			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int columnCount = rsMetaData.getColumnCount();
			columns = new HashSet<DataBaseColumn>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				DataBaseColumn c = new DataBaseColumn();
				c.setCatalogName(rsMetaData.getCatalogName(i));
				c.setColumnClassName(rsMetaData.getColumnClassName(i));
				c.setColumnDisplaySize(rsMetaData.getColumnDisplaySize(i));
				c.setColumnLabel(rsMetaData.getColumnLabel(i));
				c.setColumnName(rsMetaData.getColumnName(i));
				c.setColumnType(rsMetaData.getColumnType(i));
				c.setColumnTypeName(rsMetaData.getColumnTypeName(i));
				c.setPrecision(rsMetaData.getPrecision(i));
				c.setScale(rsMetaData.getScale(i));
				c.setSchemaName(rsMetaData.getSchemaName(i));
				c.setTableName(rsMetaData.getTableName(i));
				c.setIsAutoIncrement(rsMetaData.isAutoIncrement(i));
				c.setIsCaseSensitive(rsMetaData.isCaseSensitive(i));
				c.setIsCurrency(rsMetaData.isCurrency(i));
				c.setIsDefinitelyWritable(rsMetaData.isDefinitelyWritable(i));
				c.setIsNullable(rsMetaData.isNullable(i));
				c.setIsReadOnly(rsMetaData.isReadOnly(i));
				c.setIsSearchable(rsMetaData.isSearchable(i));
				c.setIsSigned(rsMetaData.isSigned(i));
				c.setIsWritable(rsMetaData.isWritable(i));
				c.setIsPrimaryKey(false);
				c.setIsSctGuid(false);
				columns.add(c);
				// System.out.println(
				// rsMetaData.getColumnLabel(i)+" "+rsMetaData.getColumnClassName(i)+" "+rsMetaData.getColumnDisplaySize(i)+" "+rsMetaData.getPrecision(i)+" "+rsMetaData.getColumnType(i)+" "+rsMetaData.getColumnTypeName(i));
				if (logger.isDebugEnabled()) {
					logger.debug("field Name:" + rsMetaData.getColumnName(i)
							+ " field Type:" + rsMetaData.getColumnTypeName(i)
							+ " size:" + rsMetaData.getColumnDisplaySize(i));
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return columns;

	}

	public List<DataBaseColumn> getTableColumnDetsilsList(String tableName) {
		tableName = tableName.toUpperCase();
		ArrayList<DataBaseColumn> columns = null;
		try {
			Connection con = this.getSqlMapClient().getDataSource()
					.getConnection();

			StringBuilder stringBuilder = new StringBuilder("select * from ");
			stringBuilder.append(tableName).append(" where 1=2");
			// Statement stmt = con.createStatement();
			PreparedStatement stmt = con.prepareStatement(stringBuilder
					.toString());

			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsMetaData = rs.getMetaData();
			int columnCount = rsMetaData.getColumnCount();
			columns = new ArrayList<DataBaseColumn>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				DataBaseColumn c = new DataBaseColumn();
				c.setCatalogName(rsMetaData.getCatalogName(i));
				c.setColumnClassName(rsMetaData.getColumnClassName(i));
				c.setColumnDisplaySize(rsMetaData.getColumnDisplaySize(i));
				c.setColumnLabel(rsMetaData.getColumnLabel(i));
				c.setColumnName(rsMetaData.getColumnName(i));
				c.setColumnType(rsMetaData.getColumnType(i));
				c.setColumnTypeName(rsMetaData.getColumnTypeName(i));
				c.setPrecision(rsMetaData.getPrecision(i));
				c.setScale(rsMetaData.getScale(i));
				c.setSchemaName(rsMetaData.getSchemaName(i));
				c.setTableName(rsMetaData.getTableName(i));
				c.setIsAutoIncrement(rsMetaData.isAutoIncrement(i));
				c.setIsCaseSensitive(rsMetaData.isCaseSensitive(i));
				c.setIsCurrency(rsMetaData.isCurrency(i));
				c.setIsDefinitelyWritable(rsMetaData.isDefinitelyWritable(i));
				c.setIsNullable(rsMetaData.isNullable(i));
				c.setIsReadOnly(rsMetaData.isReadOnly(i));
				c.setIsSearchable(rsMetaData.isSearchable(i));
				c.setIsSigned(rsMetaData.isSigned(i));
				c.setIsWritable(rsMetaData.isWritable(i));
				c.setIsPrimaryKey(false);
				c.setIsSctGuid(false);
				columns.add(c);
				// System.out.println(
				// rsMetaData.getColumnLabel(i)+" "+rsMetaData.getColumnClassName(i)+" "+rsMetaData.getColumnDisplaySize(i)+" "+rsMetaData.getPrecision(i)+" "+rsMetaData.getColumnType(i)+" "+rsMetaData.getColumnTypeName(i));
				if (logger.isDebugEnabled()) {
					logger.debug("field Name:" + rsMetaData.getColumnName(i)
							+ " field Type:" + rsMetaData.getColumnTypeName(i)
							+ " size:" + rsMetaData.getColumnDisplaySize(i));
				}

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return columns;

	}
	public boolean checkTableName(String tableName) {
		tableName = StringUtils.upperCase(tableName);
		try {
			Connection con = this.getSqlMapClient().getDataSource()
					.getConnection();

			StringBuilder stringBuilder = new StringBuilder("select * from ");
			stringBuilder.append(tableName).append(" where 1=2");
			PreparedStatement stmt = con.prepareStatement(stringBuilder
					.toString());

			ResultSet rs = stmt.executeQuery();

		} catch (SQLException e) {

			e.printStackTrace();
			if (e.getErrorCode() == 942)
				return false;

		}
		return true;

	}

	/**
	 *getColumnCommentsMap 方法
	 * <p>方法说明:</p>
	 *	获取 tableName 表的 所有列的 comment 返回 key=columnName ,value = comment  的Map
	 *  @param tableName
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-24 下午4:04:06 
	 */
	public HashMap<String, String> getColumnCommentsMap(String tableName) {
		tableName = StringUtils.upperCase(tableName);
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			Connection con = this.getSqlMapClient().getDataSource()
					.getConnection();

			PreparedStatement stmt = con
					.prepareStatement("select * from user_col_comments where table_name= ?");

			stmt.setString(1, tableName);// 从1开始
			ResultSet rst = stmt.executeQuery();
			while (rst.next()) {

				 
				map.put(rst.getString("COLUMN_NAME"), rst.getString("COMMENTS"));

			}

		} catch (SQLException e) {

			e.printStackTrace();

			return map;

		}
		return map;

	}

	/**
	 *getPrimaryKeyByTableName 方法
	 * <p>方法说明:</p>
	 *返回tableName 表的主键
	 *  @param tableName
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-24 下午4:05:07 
	 */
	public String getPrimaryKeyByTableName(String tableName) {
		tableName = StringUtils.upperCase(tableName);
		String primaryKey = null;
		try {
			Connection con = this.getSqlMapClient().getDataSource()
					.getConnection();

			ResultSet rst = con.getMetaData().getPrimaryKeys(null, null,
					tableName);
			if (!rst.isAfterLast()) {

				rst.next();
				primaryKey = rst.getString("COLUMN_NAME");
				// System.out.println(rst.getString("TABLE_NAME") + " " +
				//
				// rst.getString("COLUMN_NAME"));

			}
		} catch (SQLException e) {

			e.printStackTrace();

		}
		return primaryKey;

	}

	/**
	 *getIndexByTableName 方法
	 * <p>方法说明:</p>
	 *	获取unique索引 的column name ，用于指定unique字段
	 *  @param tableName
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-24 下午3:01:50 
	 */
	public HashSet<String> getIndexByTableName(String tableName) {
		tableName = StringUtils.upperCase(tableName);
		HashSet<String> indexSet = new HashSet<String>();
		try {
			Connection con = this.getSqlMapClient().getDataSource()
					.getConnection();

			ResultSet rs3 = con.getMetaData().getIndexInfo(null, null,
					tableName, true, true);
			while (rs3.next()) {  
				if (StringUtils.isNotBlank(rs3.getString(9))) {
				indexSet.add(rs3.getString(9));
			}
//                System.out.println("数据库名: "+ rs3.getString(1));  
//                System.out.println("表模式: "+ rs3.getString(2));  
//                System.out.println("表名称: "+ rs3.getString(3));  
//                System.out.println("索引值是否可以不唯一: "+ rs3.getString(4));  
//                System.out.println("索引类别: "+ rs3.getString(5));  
//                System.out.println("索引名称: "+ rs3.getString(6));  
//                System.out.println("索引类型: "+ rs3.getString(7));  
//                System.out.println("索引中的列序列号: "+ rs3.getString(8));  
//                System.out.println("列名称: "+ rs3.getString(9));  
//                System.out.println("列排序序列: "+ rs3.getString(10));  
//                System.out.println("TYPE为 tableIndexStatistic时它是表中的行数否则它是索引中唯一值的数量: "+ rs3.getString(11));  
//                System.out.println("TYPE为 tableIndexStatisic时它是用于表的页数否则它是用于当前索引的页数: "+ rs3.getString(12));  
//                System.out.println("过滤器条件: "+ rs3.getString(13));  
            }  
		} catch (SQLException e) {

			e.printStackTrace();

		}
		return indexSet;

	}

	public List<JSONObject> getStandsInLineLineinfo2(Map params) {
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("liguid", liguid);
		SqlMapClientTemplate client = this.getSqlMapClientTemplate();
		SqlMapClientImpl cc = (SqlMapClientImpl) client.getSqlMapClient();
	SqlMapExecutorDelegate d = cc.getDelegate();
	ParameterMap map2 = d.getParameterMap("sys.cck.query2");
	// map2.setParameters(arg0, arg1, arg2)
	ParameterMapping[] ddss = map2.getParameterMappings();
//	for (ParameterMapping parameterMapping : ddss) {
//		System.out.println(parameterMapping.getPropertyName()); 
//	}
		return this.getSqlMapClientTemplate().queryForList("sys.cck.query2",
				params);

	}

	/**
	 *queryGridByPage 方法
	 * <p>方法说明:</p>
	 *分页查询 数据表内容
	 *  @param pageRequest
	 *  @param params
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-24 下午4:06:12 
	 */
	public PageResponse queryGridByPage(PageRequest pageRequest, Map params) {
		return this.queryPageAuto(pageRequest, params, "sys.cck.query2");
	}
	public List queryAll(  Map params) {
 
		return  this.getSqlMapClientTemplate().queryForList( "sys.cck.query2", params) ;
	}
	/**
	 *insert 方法
	 * <p>方法说明:</p>
	 *	新建数据
	 *  @param map
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-24 下午4:06:45 
	 */
	public String insert(Map map) {
		return (String) this.getSqlMapClientTemplate().insert("sys.cck.insert",
				map);
	}

	/**
	 *update 方法
	 * <p>方法说明:</p>
	 *	更新数据表
	 *  @param map
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-24 下午4:06:30 
	 */
	public int update(Map map) {
		return this.getSqlMapClientTemplate().update("sys.cck.update", map);
	}

	/**
	 *findById 方法
	 * <p>方法说明:</p>
	 *	根据主键id 获取记录
	 *  @param map
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-24 下午4:06:59 
	 */
	public Map findById(Map<String, Object> map) {

		return (Map) this.getSqlMapClientTemplate().queryForObject(
				"sys.cck.queryById", map);
	}

	/**
	 *deleteContents 方法
	 * <p>方法说明:</p>
	 *	逻辑删除
	 *deletecolumn 为更新为1 的列名
	 *username 为用户名(n为小写)，当authority ==1 时 加入用户名作为判断条件
	 *map.sccGuids[] 为删除的id 数组[] !!
	 *  @param map
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-24 下午4:07:30 
	 */
	public int deleteContents(Map map) {

		return this.getSqlMapClientTemplate().update("sys.cck.deleteContents",
				map);

	}

	/**
	 *deleteContentsPhysical 方法
	 * <p>方法说明:</p>
	 *物理删除
	 *	map.sccGuids[] 为删除的id 数组[] !!
	 *  @param map
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-24 下午4:07:48 
	 */
	public int deleteContentsPhysical(Map map) {
		return this.getSqlMapClientTemplate().delete(
				"sys.cck.deleteContentsPhysical", map);

	}
	@Deprecated
	public List<Map<String, Object>> getReference(String sql) {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList( "sys.cck.queryForReference", sql) ;
		
	}
	@Deprecated
	public List<Map<String, Object>> getAutocomplete(Map map  ) {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList( "sys.cck.queryForAutocomplete", map) ;
		
	}

	public List<Map<String, Object>> checkUnique(Map<String, Object> subMap) {
		// TODO Auto-generated method stub
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList( "sys.cck.checkUnique", subMap) ;
	}

	public List<String> getChildrens(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return (List<String>)this.getSqlMapClientTemplate().queryForList( "sys.cck.getChildrens", map) ;
	}

}
