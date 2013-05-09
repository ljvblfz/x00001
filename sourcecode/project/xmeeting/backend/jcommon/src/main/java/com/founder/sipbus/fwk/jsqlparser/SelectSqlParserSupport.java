package com.founder.sipbus.fwk.jsqlparser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author lu.zhen http://www.th7.cn/Program/java/2012/06/29/82566.shtml
 * 
 */
public class SelectSqlParserSupport {
	private static final String INNER_JOIN = " INNER JOIN ";
	private static final String LEFT_JOIN = " LEFT JOIN ";
	static CCJSqlParserManager parserManager = new CCJSqlParserManager();
/*
	public static void testSelectItems() throws JSQLParserException {
		String statement = "SELECT myid AS MYID, mycol,  mytab.mycol2, myschema.mytab.mycol ,to_char(a.alarmdate,'yyyy-mm-dd hh24:mi:ss') as alarmdate,z.a1 FROM mytable x,mytable2 y ,(select a1,a2 from atable where 1=1) z WHERE 1=1 and mytable.col = 9";
		parseSqlText(statement);

	}

	public static void testSelectItems2() throws JSQLParserException {
		String sqltext = "SELECT e.last_name AS name,\n"
				+ " e.commission_pct comm,\n"
				+ " e.salary  \"Annual Salary\"\n"
				+ "FROM scott.employees AS e\n" + "WHERE e.salary > 1000\n"
				+ "ORDER BY\n" + " e.first_name,\n" + " e.last_name;";
		sqltext = "select sc.value_desc as text,sc.value_code as value from  sy_code_type sct  left join sy_code sc on sc.type_id =sct.type_id  where sct.type_name ='8013' ";
		parseSqlText(sqltext);
	}

	public static void testSelectItems3() throws JSQLParserException {
		// String sqltext
		// ="select sc.value_desc as text,sc.value_code as value from  sy_code_type sct  left join sy_code sc on sc.type_id =sct.type_id  where sct.type_name ='8013' ";
		String sqltext = "select  value_desc as text, value_code as value from  sy_code_type sct  left join sy_code sc on sc.type_id =sct.type_id and t=sct.t  where sct.type_name ='8013' and tf=? order by a.b desc ,c.c ,ddd";
		// String sqltext
		// =" select  value_desc as text, value_code as value from  sy_code_type sct  ,  sy_code sc where  sc.type_id =sct.type_id and 2=2 or sct.type_name ='8013' or (sct.n='d' and 1=1) ;";
		// String sqltext
		// =" select  value_desc as text, value_code as value from  sy_code_type,d where s=n ;";

		parseSqlText2(sqltext);
	}*/

/*	public static String parseSqlText(String sqltext)
			throws JSQLParserException {
		PlainSelect plainSelect = (PlainSelect) ((Select) parserManager
				.parse(new StringReader(sqltext))).getSelectBody();
		Distinct ddd = plainSelect.getDistinct();
		FromItem fromitem = plainSelect.getFromItem();

		List<SelectExpressionItem> listOfItems = (List<SelectExpressionItem>) plainSelect
				.getSelectItems();
		String selectSql = checkSelectExpressionItem(listOfItems);
		if (selectSql == null) {
			throw new JSQLParserException();
		}
		// for (SelectExpressionItem selectExpressionItem : listOfItems) {
		// Expression e = selectExpressionItem.getExpression();
		//
		//
		// selectExpressionItem.setExpression(e);
		// 
		// + selectExpressionItem.getAlias() + "--getExpression---->"
		// + selectExpressionItem.getExpression());
		//
		// }

		// first from item
		FromItem fromItem = plainSelect.getFromItem();
		System.out.println("-from-getAlias--->" + fromItem.getAlias()
				+ "--fromItem--->" + fromItem);
		fromItem.setAlias("sdsad");
		System.out.println("-from-getAlias--->" + fromItem.getAlias()
				+ "--fromItem--->2 " + fromItem);
		//
		List<Join> listOfJoin = plainSelect.getJoins();
		if (null != listOfJoin) {
			for (Join join : listOfJoin) {

				System.out.println("-from-join--->" + join);
			}
		}
		BinaryExpression expression = (BinaryExpression) plainSelect.getWhere();

		System.out.println("-where-expression-----left->"
				+ expression.getLeftExpression() + "--right->"
				+ expression.getRightExpression());
		ExpressionDeParser expressionDeParser = new ExpressionDeParser();
		StringBuffer stringBuffer = new StringBuffer();
		expressionDeParser.setBuffer(stringBuffer);
		expression.accept(expressionDeParser);
		System.out.println("-where-stringBuffer--->" + stringBuffer);
		return selectSql;

	}*/

	private static String checkSelectExpressionItem(
			List<SelectExpressionItem> listOfItems) {
		HashSet<String> selectAliasSet = new HashSet<String>();
		StringBuilder stringBuilder = new StringBuilder();
		if (listOfItems.size() < 2) {
			return null;
		}
		for (SelectExpressionItem selectExpressionItem : listOfItems) {
			String aliasString = selectExpressionItem.getAlias();
			aliasString = StringUtils.upperCase(aliasString);

			if (selectAliasSet.add(aliasString)) {
				// selectExpressionItem.setAlias(aliasString);
				stringBuilder
						.append(selectExpressionItem.getExpression().toString()
								.toUpperCase()).append(" ").append(aliasString)
						.append(" ,");
				// selectExpressionItem.getExpression().toString().toUpperCase();
			} else {
				return null;
			}

		}
		if (selectAliasSet.contains("VALUE") && selectAliasSet.contains("TEXT")) {
			return stringBuilder.deleteCharAt(stringBuilder.length() - 1)
					.toString();
		}
		return null;

	}

	/*public static void parseSqlText2(String sqltext) throws JSQLParserException {

		PlainSelect plainSelect = (PlainSelect) ((Select) parserManager
				.parse(new StringReader(sqltext))).getSelectBody();
		Distinct ddd = plainSelect.getDistinct();
		FromItem fromitem = plainSelect.getFromItem();
		fromitem.setAlias("xxx");
		List<SelectExpressionItem> listOfItems = (List<SelectExpressionItem>) plainSelect
				.getSelectItems();
		for (SelectExpressionItem selectExpressionItem : listOfItems) {

			System.out.println("-selectitem-getAlias---->"
					+ selectExpressionItem.getAlias() + "--getExpression---->"
					+ selectExpressionItem.getExpression());

		}

		// first from item
		FromItem fromItem = plainSelect.getFromItem();
		System.out.println("-from-getAlias--->" + fromItem.getAlias()
				+ "--fromItem--->" + fromItem);
		fromItem.setAlias("sdsad");
		System.out.println("-from-getAlias--->" + fromItem.getAlias()
				+ "--fromItem--->2 " + fromItem);
		//
		//
		List dddd = plainSelect.getJoins();
		List<Join> listOfJoin = plainSelect.getJoins();
		if (null != listOfJoin) {
			for (Join join : listOfJoin) {
				join.isSimple();
				join.getOnExpression();
				join.getUsingColumns();
				System.out.println("-from-join--->" + join.getOnExpression()
						+ join);
			}
		}
		BinaryExpression expression = (BinaryExpression) plainSelect.getWhere();
		if (null != expression) {
			getWhereExpressions(expression, 0);
		}
		// System.out.println("-where-expression-----left->"
		// + expression.getLeftExpression() + "--right->"
		// + expression.getRightExpression());

		ExpressionDeParser expressionDeParser = new ExpressionDeParser();
		StringBuffer d = new StringBuffer();
		net.sf.jsqlparser.util.deparser.SelectDeParser selectDeParser = new SelectDeParser(
				expressionDeParser, d);
		StringBuffer stringBuffer = new StringBuffer();
		expressionDeParser.setBuffer(stringBuffer);
		StringBuffer stringBuffer2 = new StringBuffer();
		selectDeParser.setBuffer(stringBuffer2);
		expression.accept(expressionDeParser);
		StringBuffer stringBuffer3 = new StringBuffer();
		StatementVisitor statementVisitor = new StatementDeParser(stringBuffer3);
		// select.accept(statementVisitor) ;
		System.out.println("-where-stringBuffer--->" + stringBuffer + d
				+ stringBuffer2 + stringBuffer3);

	}*/

	/*private static void getWhereExpressions(Expression expression, int i) {
		
		if (expression instanceof BinaryExpression) {
			BinaryExpression expression2 = (BinaryExpression) expression;

			getWhereExpressions(expression2.getLeftExpression(), i++);

			getWhereExpressions(expression2.getRightExpression(), i++);
		} else if (expression instanceof Parenthesis) {
			Parenthesis new_name = (Parenthesis) expression;
			getWhereExpressions(new_name.getExpression(), i++);

			// System.out.println(new_name);
		} else {
			
			if (expression instanceof Column) {
				Column column = (Column) expression;

				Table aTable = new Table();
				aTable.setAlias("ddd");
				aTable.setName("sname");
				column.setTable(aTable);
			}
			
		}

	}
*/
	
	/**
	 *	测试sql是否符合引用的规范 含有ID NAME 别名
	 *  @param sqltext
	 *  @return
	 *  @throws JSQLParserException
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:20:21 
	 */
	public static boolean testSqlText(String sqltext)
			throws JSQLParserException {
		PlainSelect plainSelect = (PlainSelect) ((Select) parserManager
				.parse(new StringReader(sqltext))).getSelectBody();
		List<SelectExpressionItem> listOfItems = (List<SelectExpressionItem>) plainSelect
				.getSelectItems();
		int matchCount1 = 0;
		int matchCount2 = 0;
		
		for (Object selectExpressionItem : listOfItems) {
			if (selectExpressionItem instanceof AllColumns) {
				return false;
			}
			if (StringUtils.equalsIgnoreCase(((SelectExpressionItem)selectExpressionItem).getAlias(),
					"id")) {
				matchCount1++;
			} else if (StringUtils.equalsIgnoreCase(((SelectExpressionItem)selectExpressionItem).getAlias(), "name")) {
				matchCount2++;

			}
		}
		if (matchCount1 == 1 && matchCount2 == 1) {
			return true;
		} else
			return false;
	}

	/**
	 *	获取combox comboxcascade引用参数map
	 *  @param sqltext sql语句
	 *  @param idColumn ID
	 *  @param columnName
	 *  @param index
	 *  @return
	 *  @throws JSQLParserException
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:21:31 
	 */
	public static Map<String, String> getReferenceSQLMap(String sqltext,
			String idColumn, String columnName, int index)
			throws JSQLParserException {
		Map<String, String> resultMap = new HashMap<String, String>();
		sqltext = StringUtils.upperCase(sqltext);
		idColumn = StringUtils.upperCase(idColumn);
		StringBuilder selectSqlBuilder = new StringBuilder();
		StringBuilder joinSqlBuilder = new StringBuilder();
		PlainSelect plainSelect = (PlainSelect) ((Select) parserManager
				.parse(new StringReader(sqltext))).getSelectBody();
		HashMap<String, String> aliasMap = new HashMap<String, String>();
		Table fromItem = (Table) plainSelect.getFromItem();
		String tableName0 = fromItem.getName();
		String alias0 = "T_" + tableName0 + "_" + index + "_0";
		String idColumn2 = "";
		aliasMap.put(fromItem.getAlias(), alias0);

		List<Join> listOfJoin = plainSelect.getJoins();
		HashMap<Join, Expression> onExpressions = new HashMap<Join, Expression>();
		if (null != listOfJoin) {
			int i = 1;
			for (Join join : listOfJoin) {
				Table joinTable = (Table) join.getRightItem();
				String joinTableName = joinTable.getName();

				String joinTableAliasNew = "T_" + joinTableName + "_" + index
						+ "_" + i;
				aliasMap.put(joinTable.getAlias(), joinTableAliasNew);
				
				Expression expression = join.getOnExpression();
				if (expression != null) {
					onExpressions.put(join, expression);
				}

				i++;
			}

		}
		
		List<SelectExpressionItem> selectExpressionItems = (List<SelectExpressionItem>) plainSelect
				.getSelectItems();
		for (SelectExpressionItem selectExpressionItem : selectExpressionItems) {
			Column column = (Column) selectExpressionItem.getExpression();
			column.getTable()
					.setName(aliasMap.get(column.getTable().getName()));
			if (StringUtils.equalsIgnoreCase("ID",
					selectExpressionItem.getAlias())) {
				idColumn2 = selectExpressionItem.getExpression().toString();
			}
			String alias = selectExpressionItem.getAlias();
			if (null == alias) {
				alias = column.getColumnName() + "_";
			}
			if (StringUtils.equalsIgnoreCase("name",
					selectExpressionItem.getAlias())) {
				selectSqlBuilder.append(selectExpressionItem.getExpression())
						.append(" ").append(columnName).append("__")
						.append(alias).append(",");
			}
		}
		String onSqlString = " on to_char(" + idColumn + ") = to_char("
				+ idColumn2 + ") ";
		
	 Expression whereExpression =   plainSelect
				.getWhere();
		if (null != whereExpression) {
			changeExpressionAlias(aliasMap, whereExpression);
			String whereSqlString = "( " + whereExpression.toString() + ")";
			
			onSqlString += " and ";
			onSqlString += whereSqlString;
			// resultMap.put("whereSql", whereSqlString);
		}

		joinSqlBuilder.append("(").append(tableName0).append(" ")
				.append(alias0);
		if (null != listOfJoin) {

			for (Join join : listOfJoin) {
				if (join != null) {
					Expression onExpression = onExpressions.get(join);
					if (null != onExpression) {
						changeExpressionAlias(aliasMap, onExpression);
					}

					Table joinTable = (Table) join.getRightItem();
					if (join.isInner()) {
						joinSqlBuilder.append(INNER_JOIN);
					} else if (join.isLeft()) {
						joinSqlBuilder.append(LEFT_JOIN);
					} else if (join.isSimple()) {
						joinSqlBuilder.append(INNER_JOIN);
					}
					String aliasString = aliasMap.get(joinTable.getAlias());
					joinSqlBuilder.append(joinTable.getName()).append(" ")
							.append(aliasString);

					StringBuilder sb = new StringBuilder();
					String relatedWhereExpressionString = getRelatedWhereExpressionString(
							sb, whereExpression, aliasString, alias0);
					if (!join.isSimple()
							|| StringUtils
									.isNotBlank(relatedWhereExpressionString)) {
						joinSqlBuilder.append(" on ");
						if (null != onExpression) {
							joinSqlBuilder.append(onExpression);
						} else {
							joinSqlBuilder.append(relatedWhereExpressionString);
							// resultMap.remove("whereSql");
						}

					}

				}
			}

		}
		joinSqlBuilder.append(")");
		
		
		resultMap.put("joinSql", joinSqlBuilder.toString());
		resultMap.put("selectSql", selectSqlBuilder.toString());
		resultMap.put("onSql", onSqlString);
		return resultMap;
	}
	
	/**
	 *	获取与join 关联的 where条件语句
	 *  @param sb
	 *  @param expression
	 *  @param alias
	 *  @param alias1
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午6:02:50 
	 */
	private static String getRelatedWhereExpressionString(StringBuilder sb,
			Expression expression, String alias, String alias1) {
		if (expression instanceof EqualsTo) {
			Expression left = ((BinaryExpression) expression)
					.getLeftExpression();
			Expression right = ((BinaryExpression) expression)
					.getRightExpression();
			boolean isrelatedTable1 = false;
			boolean isrelatedTable2 = false;
			if (left instanceof Column) {
				Column column = (Column) left;
				String name = column.getTable().getName();
				if (StringUtils.equalsIgnoreCase(alias, name)) {
					isrelatedTable1 = true;
				} else if (StringUtils.equalsIgnoreCase(alias1, name)) {
					isrelatedTable2 = true;
				}
			}
			if (isrelatedTable2||isrelatedTable1) {
				if (right instanceof Column) {
					Column column = (Column) right;
					String name = column.getTable().getName();
					if (StringUtils.equalsIgnoreCase(alias, name)) {
						isrelatedTable1 = true;
					} else if (StringUtils.equalsIgnoreCase(alias1, name)) {
						isrelatedTable2 = true;
					}
				}
				if (isrelatedTable2 && isrelatedTable1) {
					sb.append(expression);
				}
			}
		} else if (expression instanceof BinaryExpression) {
			BinaryExpression expression2 = (BinaryExpression) expression;
			getRelatedWhereExpressionString(sb,
					expression2.getLeftExpression(), alias, alias1);
			getRelatedWhereExpressionString(sb,
					expression2.getRightExpression(), alias, alias1);
		} else if (expression instanceof Parenthesis) {
			Parenthesis new_name = (Parenthesis) expression;
			getRelatedWhereExpressionString(sb, new_name.getExpression(),
					alias, alias1);
		}
		return sb.toString();

	}

	/**
	 *替换 字段 前 表名 的别名
	 *  @param aliasMap
	 *  @param expression
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:23:01 
	 */
	private static void changeExpressionAlias(HashMap<String, String> aliasMap,
			Expression expression) {
		if (expression instanceof BinaryExpression) {
			BinaryExpression expression2 = (BinaryExpression) expression;
			changeExpressionAlias(aliasMap, expression2.getLeftExpression());
			changeExpressionAlias(aliasMap, expression2.getRightExpression());
		} else if (expression instanceof Parenthesis) {
			Parenthesis new_name = (Parenthesis) expression;
			changeExpressionAlias(aliasMap, new_name.getExpression());
		} else if (expression instanceof IsNullExpression) {
			IsNullExpression new_name = (IsNullExpression) expression;
			changeExpressionAlias(aliasMap, new_name.getLeftExpression());
		} else {
			if (expression instanceof Column) {
				Column column = (Column) expression;
				Table table = column.getTable();
				column.getTable().setName(aliasMap.get(table.getName()));
			}
		}

	}
	/*public static void main(String[] args) throws JSQLParserException {
		// testSelectItems();
		// testSelectItems2();
		// testSelectItems3();
		String sqltext2 = "select  value_desc as name, value_code as id ,ddd as dsww from  sy_code_type    left join sy_code sc on sc.type_id = type_id and t= sct.dsad inner join sd aaa on aaa.sss=sss  where type_name ='8013' and tf=?";
//		String sqltext2 = "select iii,  value_desc as name, value_code as id ,ddd as dsww from  sy_code_type ,ss ss where ss.id=id and id<1000  order by a.b desc ,c.c ,ddd ";

		// getReferenceSQLMap(sqltext, "t.testCol", "TESTCOL", 0);
		getAutocompleteSQLMap(sqltext2,   "TESTCOL2", 0);
	}*/
	/**
	 *		为Autocomplete引用生成对应的 sql 参数map
	 *  @param sqltext sql 语句
	 *  @param columnName column 名（未使用）
	 *  @param index 第几个
	 *  @return
	 *  @throws JSQLParserException
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午2:21:12 
	 */
	public static Map<String, String> getAutocompleteSQLMap(String sqltext,
		  String columnName, int index)
			throws JSQLParserException {
		Map<String, String> resultMap = new HashMap<String, String>();
		sqltext = StringUtils.upperCase(sqltext);
		String idColumn="";														// alias为ID的列名
		String nameColumn="";													// alias为NAME的列名
		StringBuilder selectSqlBuilder = new StringBuilder();					//SELECT 语句
		StringBuilder joinSqlBuilder = new StringBuilder();						//JOIN 语句
		PlainSelect plainSelect = (PlainSelect) ((Select) parserManager
				.parse(new StringReader(sqltext))).getSelectBody();
		List<OrderByElement> orderBylist = plainSelect.getOrderByElements();	// order by 语句
		HashMap<String, String> aliasMap = new HashMap<String, String>();
		Table fromItem = (Table) plainSelect.getFromItem();
		String tableName0 = fromItem.getName();									//第一个 表名
		String alias0 = "t";													//第一个 表 的 别名 固定为 小写t
		aliasMap.put(fromItem.getAlias(), alias0);
		List<Join> listOfJoin = plainSelect.getJoins();
		HashMap<Join, Expression> onExpressions = new HashMap<Join, Expression>();
		if (null != listOfJoin) {
			int i = 1;
			for (Join join : listOfJoin) {
				Table joinTable = (Table) join.getRightItem();
				String joinTableName = joinTable.getName();
				String joinTableAliasNew = "T_" + joinTableName + "_" + index
						+ "_" + i;
				aliasMap.put(joinTable.getAlias(), joinTableAliasNew);
				Expression expression = join.getOnExpression();
				if (expression != null) {
					onExpressions.put(join, expression);
				}
				i++;
			}
		}
		List<SelectExpressionItem> selectExpressionItems = (List<SelectExpressionItem>) plainSelect
				.getSelectItems();
		int aliasIndex=0;
		for (SelectExpressionItem selectExpressionItem : selectExpressionItems) {
			Column column = updateTableName(selectExpressionItem.getExpression(),aliasMap,aliasIndex);
			if (StringUtils.equalsIgnoreCase("ID",
					selectExpressionItem.getAlias())) {
				idColumn=selectExpressionItem.getExpression().toString();
			}else if (StringUtils.equalsIgnoreCase("NAME",
					selectExpressionItem.getAlias())) {
				nameColumn =selectExpressionItem.getExpression().toString();
			}
			String alias = selectExpressionItem.getAlias();
			if (null == alias) {
				alias = column.getColumnName() ;
			}
				selectSqlBuilder.append(selectExpressionItem.getExpression())
						.append(" ").append(alias).append(",");
		}
		Expression whereExpression =  plainSelect
				.getWhere();
		if (null != whereExpression) {
			changeExpressionAlias(aliasMap, whereExpression);
			String whereSqlString = "( " + whereExpression.toString() + ")";
			resultMap.put("whereSql", whereSqlString);
		}
		StringBuilder orderByBuilder = new StringBuilder();
		
		boolean isfirstOrderByField=true;
		if (null!=orderBylist) {
			for (OrderByElement object : orderBylist) {
				changeExpressionAlias(aliasMap,object.getExpression());//更改alias 防止重复
				if (isfirstOrderByField) {
					orderByBuilder.append(object.toString());
					isfirstOrderByField= false;
				}else {
					orderByBuilder.append(",").append(object.toString());
				}
				
				
			}
		}
		joinSqlBuilder.append(tableName0).append(" ")
				.append(alias0);
		if (null != listOfJoin) {

			for (Join join : listOfJoin) {
				if (join != null) {
					Expression onExpression = onExpressions.get(join);
					if (null != onExpression) {
						changeExpressionAlias(aliasMap, onExpression);
					}

					Table joinTable = (Table) join.getRightItem();
					if (join.isInner()) {
						joinSqlBuilder.append(INNER_JOIN);
					} else if (join.isLeft()) {
						joinSqlBuilder.append(LEFT_JOIN);
					} else if (join.isSimple()) {
						joinSqlBuilder.append(",");
					}
					String aliasString = aliasMap.get(joinTable.getAlias());
					joinSqlBuilder.append(joinTable.getName()).append(" ")
							.append(aliasString);

//					StringBuilder sb = new StringBuilder();
//					String relatedWhereExpressionString = getRelatedWhereExpressionString(
//							sb, whereExpression, aliasString, alias0);
					if (!join.isSimple() &&null != onExpression  ) {
						joinSqlBuilder.append(" on ");
							joinSqlBuilder.append(onExpression);
					}
				}
			}

		}
		
		resultMap.put("fromSql", joinSqlBuilder.toString());		//解析后的from 语句
		selectSqlBuilder.deleteCharAt(selectSqlBuilder.length()-1); 
		resultMap.put("selectSql", selectSqlBuilder.toString());	//解析后的select 语句
//		resultMap.put("fromSql", from.toString());
		resultMap.put("orderBySql", orderByBuilder.toString());		//解析后的orderby 语句
		resultMap.put("originSql",sqltext);							//原始sql 
		resultMap.put("idColumn",idColumn);
		resultMap.put("nameColumn",nameColumn);
		return resultMap;
	}

	/**
	 *替换字段前表名的别名
	 *  @param expression
	 *  @param aliasMap
	 *  @param aliasIndex
	 *  @return 第一个column的name
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:23:49 
	 */
	private static Column updateTableName(
			Expression expression ,
			HashMap<String, String> aliasMap,int aliasIndex) {
		if( expression instanceof Column ){
			{
				Column column = (Column) expression;
				column.getTable()
						.setName(aliasMap.get(column.getTable().getName()));
				return column;
			}
		}else

			if (expression instanceof Concat) {
				Concat concat =(Concat)expression;
//				String string=concat.getStringExpression();
			Expression leftExpression = concat.getLeftExpression();
			Expression rightExpression = concat.getRightExpression() ;
			updateTableName(rightExpression, aliasMap,aliasIndex);
				return updateTableName(leftExpression, aliasMap,aliasIndex);
			}else {
				return new Column(null, "__ALIAS"+aliasIndex++);
			}
	}

}
