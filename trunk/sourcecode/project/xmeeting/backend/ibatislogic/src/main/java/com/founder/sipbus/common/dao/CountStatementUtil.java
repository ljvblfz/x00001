package com.founder.sipbus.common.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.ibatis.common.jdbc.exception.NestedSQLException;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.mapping.parameter.ParameterMap;
import com.ibatis.sqlmap.engine.mapping.result.AutoResultMap;
import com.ibatis.sqlmap.engine.mapping.result.BasicResultMap;
import com.ibatis.sqlmap.engine.mapping.result.ResultMap;
import com.ibatis.sqlmap.engine.mapping.sql.Sql;
import com.ibatis.sqlmap.engine.mapping.statement.BaseStatement;
import com.ibatis.sqlmap.engine.mapping.statement.ExecuteListener;
import com.ibatis.sqlmap.engine.mapping.statement.MappedStatement;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.mapping.statement.SelectStatement;
import com.ibatis.sqlmap.engine.scope.ErrorContext;
import com.ibatis.sqlmap.engine.scope.RequestScope;

public class CountStatementUtil {

	public static MappedStatement createCountStatement(
			MappedStatement selectStatement) {
		return new CountStatement((SelectStatement) selectStatement);
	}

	public static String getCountStatementId(String selectStatementId) {
		return "__" + selectStatementId + "Count__";
	}

}

class CountStatement extends SelectStatement {

	public CountStatement(SelectStatement selectStatement) {
		super();
		setId(CountStatementUtil.getCountStatementId(selectStatement.getId()));
		setResultSetType(selectStatement.getResultSetType());
		setFetchSize(1);
		setParameterMap(selectStatement.getParameterMap());
		setParameterClass(selectStatement.getParameterClass());
		setSql(selectStatement.getSql());
		setResource(selectStatement.getResource());
		setSqlMapClient(selectStatement.getSqlMapClient());
		setTimeout(selectStatement.getTimeout());
		
		Class<BaseStatement> c = (Class<BaseStatement>) BaseStatement.class;
		Field executeListenersField;
		List executeListeners=null;
		try {
			executeListenersField = c.getDeclaredField("executeListeners");
			ReflectionUtils.makeAccessible(executeListenersField);   
			executeListeners = (List)executeListenersField.get(selectStatement);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		List executeListeners = (List) ReflectUtil.getFieldValue(
//				selectStatement, "executeListeners", List.class);
		
		if (executeListeners != null) {
			for (Object listener : executeListeners) {
				addExecuteListener((ExecuteListener) listener);
			}
		}
		BasicResultMap resultMap = new AutoResultMap(
				((ExtendedSqlMapClient) getSqlMapClient()).getDelegate(), false);
		resultMap.setId(getId() + "-AutoResultMap");
		resultMap.setResultClass(Long.class);
		resultMap.setResource(getResource());
		setResultMap(resultMap);

	}

	protected void executeQueryWithCallback(RequestScope request,
			Connection conn, Object parameterObject, Object resultObject,
			RowHandler rowHandler, int skipResults, int maxResults)
			throws SQLException {
		ErrorContext errorContext = request.getErrorContext();
		errorContext
				.setActivity("preparing the mapped statement for execution");
		errorContext.setObjectId(this.getId());
		errorContext.setResource(this.getResource());

		try {
			parameterObject = validateParameter(parameterObject);

			Sql sql = getSql();

			errorContext.setMoreInfo("Check the parameter map.");
			ParameterMap parameterMap = sql.getParameterMap(request,
					parameterObject);

			errorContext.setMoreInfo("Check the result map.");
			ResultMap resultMap = getResultMap(request, parameterObject, sql);

			request.setResultMap(resultMap);
			request.setParameterMap(parameterMap);

			errorContext.setMoreInfo("Check the parameter map.");
			Object[] parameters = parameterMap.getParameterObjectValues(
					request, parameterObject);

			errorContext.setMoreInfo("Check the SQL statement.");
			String sqlString = getSqlString(request, parameterObject, sql);

			errorContext.setActivity("executing mapped statement");
			errorContext
					.setMoreInfo("Check the SQL statement or the result map.");
			RowHandlerCallback callback = new RowHandlerCallback(resultMap,
					resultObject, rowHandler);
			sqlExecuteQuery(request, conn, sqlString, parameters, skipResults,
					maxResults, callback);

			errorContext.setMoreInfo("Check the output parameters.");
			if (parameterObject != null) {
				postProcessParameterObject(request, parameterObject, parameters);
			}

			errorContext.reset();
			sql.cleanup(request);
			notifyListeners();
		} catch (SQLException e) {
			errorContext.setCause(e);
			throw new NestedSQLException(errorContext.toString(),
					e.getSQLState(), e.getErrorCode(), e);
		} catch (Exception e) {
			errorContext.setCause(e);
			throw new NestedSQLException(errorContext.toString(), e);
		}
	}

	private String getSqlString(RequestScope request, Object parameterObject,
			Sql sqlObj) {
//		String sqlString = sql.getSql(request, parameterObject);
//		int start = sqlString.toLowerCase().indexOf("from");
//		if (start >= 0) {
//			sqlString = "SELECT COUNT(*) AS c " + sqlString.substring(start);
//		}
		String sql = sqlObj.getSql(request, parameterObject);
		String sqlString = sql;
		if(sql.indexOf("union")==-1){
			sqlString = removeSelect(sql);
			sqlString = removeOrders(sqlString);
			sqlString = removeFetchKeyword(sqlString);
			sqlString = " select count(1) from (select 1 " + sqlString+")";
		}else{
			sqlString = " select count(1) from (" + sqlString+")";
		}
		
		
		return sqlString;
	}

	/**
	 * 去除select 子句，未考虑union的情况
	 * 
	 * @param hql
	 * @return
	 */
	protected static String removeSelect(String hql) {
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos != -1, " hql : " + hql
				+ " must has a keyword 'from'");
		return hql.substring(beginPos);
	}

	/**
	 * 去除orderby 子句
	 * 
	 * @param hql
	 * @return
	 */
	protected static String removeOrders(String hql) {
		String returnSql=hql;
		Assert.hasText(hql);
		int lastIndex = hql.lastIndexOf("order by");
//		System.out.println(lastIndex);
		if(lastIndex>0){
//			System.out.println(hql.substring(lastIndex,hql.length()));
//			System.out.println(hql.substring(lastIndex,hql.length()).indexOf("\\)"));
//			System.out.println(hql.substring(lastIndex,hql.length()).indexOf(")"));
			if(hql.substring(lastIndex,hql.length()).indexOf(")")==-1){
				returnSql=hql.substring(0,lastIndex);
			}
		}
		
//		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S|\\t|\\n|\\r]*",
//				Pattern.CASE_INSENSITIVE);
//		Matcher m = p.matcher(hql);
//		StringBuffer sb = new StringBuffer();
//		i0;
//		while (m.find()) {
//			System.out.println("match");
//			m.
//			m.appendReplacement(sb, "");
//		}
//		m.appendTail(sb);
		return returnSql;
	}
	public static void main(String[] args) {
		String hql="from mw_material_info a 			 			where 	 1=1  				 and del_flag=0 		 		 		 		 		 		 		 		 		 		 		 				order by a.update_dt       	 	  	";
		
		System.out.println(removeOrders(hql));
	}

	protected static String removeFetchKeyword(String hql) {
		return hql.replaceAll("(?i)fetch", "");
	}
	
	private ResultMap getResultMap(RequestScope request,
			Object parameterObject, Sql sql) {
		return getResultMap();
	}

}
