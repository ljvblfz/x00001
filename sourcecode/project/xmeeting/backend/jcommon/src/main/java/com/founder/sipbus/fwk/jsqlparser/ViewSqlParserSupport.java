package com.founder.sipbus.fwk.jsqlparser;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

/**
 * 
 * @author lu.zhen
 * http://www.th7.cn/Program/java/2012/06/29/82566.shtml
 *
 */
public class ViewSqlParserSupport  { 

  
	private static String testSqlSource="select a.x as ax,a.y,a.z,a.m,d.c from xtable a,ytable b,(select c.x,c.y,c.d from ztable c where c.z=1) d where a.x=1 and a.x=b.x and b.y=? and b.z=?  and b.xz=? and a.x=c.x and a.x=d.x  group by a.x,a.y order by a.y";
	private static String testSqlSource2="select a.x as ax,a.y,a.z,a.m from xtable a,ytable b where a.x=1 and a.x=b.x and b.y=?   order by a.y"; 
	private static String testSqlSource3="select a.x as ax,a.y,a.z,a.m from xtable a,ytable b where a.x=1 and a.x=b.x and b.y=?   group by a.x,a.y order by a.y";
	private static String testSqlSource4="select a.x as ax,a.y,a.z,a.m from xtable a  where a.x=1   order by a.y";
	private static String testSqlSource5=" SELECT DISTINCT B.LNAME,L.LDIRECTION,NVL(T.LIGUID,' ') LIGUID FROM TB_BUSSTATUSINFO T  INNER JOIN LINE_LINEINFO L ON T.LIGUID=L.LIGUID INNER JOIN LINE_LINEBASICINFO B ON B.LBGUID=L.LBGUID AND L.ISDELETED=0 AND B.ISDELETED=0 AND T.MODIFYDATE<(SYSDATE-1/48) ";
	
	public static JSONObject parseSqlTextWithSql(String sqlSourceText)   { 
		JSONObject jsonObject=parseSqlText(sqlSourceText);
		String countSql=generateCountSql(jsonObject);
		jsonObject.put("countSqlText", countSql);
		jsonObject.put("originalSqlText", sqlSourceText);
		
		return jsonObject; 
	}

	/**
	 * 
	 * @param sqltext
	 * @return
	 * @throws JSQLParserException
	 */
	public static JSONObject parseSqlText(String sqltext)   {
		JSONObject jsonObject=new JSONObject();
		PlainSelect plainSelect=SqlParserFactory.parseSqlSource(sqltext);
		//查询结果item
		List<SelectExpressionItem> listOfSelectItem=plainSelect.getSelectItems();
		JSONArray jsonSelectItemArray=new JSONArray();
		parseSelectItems(listOfSelectItem,jsonSelectItemArray);
		jsonObject.put("listOfSelectItem", jsonSelectItemArray); 
		//from
		FromItem fromItem=plainSelect.getFromItem();
		jsonObject.put("fromItemExpression", fromItem.toString());
		//join
		List<Join> listofJoinItem=plainSelect.getJoins(); 
		if(null!=listofJoinItem){
			parseJoinItems(jsonObject, listofJoinItem); 
		}
		//查询条件item
		JSONArray jsonConditionItemArray=new JSONArray();
		Expression whereExpression=plainSelect.getWhere();
		if(null!=whereExpression){
			parseConditionItems(whereExpression,jsonConditionItemArray);
			jsonObject.put("listOfConditionItem", jsonConditionItemArray);
			jsonObject.put("whereExpression", whereExpression.toString());
		}
		//order by
		List<OrderByElement> listofOrderByItem=plainSelect.getOrderByElements(); 
		if(null!=listofOrderByItem){
			jsonObject.put("listofOrderByItem", listofOrderByItem.toString()); 
			
		}
		//
		List<Column> listofGroupByItem=plainSelect.getGroupByColumnReferences();
		if(null!=listofGroupByItem){ 
			StringBuilder sbGroupBy=new StringBuilder();
			int indexOfGroupBy=0;
			for(Column column:listofGroupByItem){
				if(indexOfGroupBy>0){
					sbGroupBy.append(" , ");
				}
				sbGroupBy.append(column.toString());
				indexOfGroupBy++;
			}
			jsonObject.put("groupByExpression", sbGroupBy.toString());
		}
		//
//		System.out.println("jsonObject--->"+jsonObject);
		
		return jsonObject; 
	}


	public static String generateCountSql(String sqltext){
		JSONObject jsonObject=parseSqlText(sqltext);
		StringBuilder sbCountSql=new StringBuilder();
		sbCountSql.append(" select count(1)  ");
		sbCountSql.append(" from "+jsonObject.get("fromItemExpression"));
		Object joinItemExpression=jsonObject.get("joinItemExpression");
		if(null!=joinItemExpression){
			sbCountSql.append(" , "+ joinItemExpression);
			
		}
		Object whereExpression= jsonObject.get("whereExpression");
		if(null!=whereExpression){
			sbCountSql.append(" where "+whereExpression);  
		}
		Object groupByExpression= jsonObject.get("groupByExpression");
		if(null!=whereExpression){
			sbCountSql.append(" group by "+groupByExpression);  
		}
		 
		System.out.println("sbCountSql--->"+sbCountSql);
		 
		return sbCountSql.toString();
		
	}
	
	
	public static String generateCountSql(JSONObject jsonObject){ 
		StringBuilder sbCountSql=new StringBuilder();
		sbCountSql.append(" select count(1)  ");
		sbCountSql.append(" from "+jsonObject.get("fromItemExpression"));
		Object joinItemExpression=jsonObject.get("joinItemExpression");
		if(null!=joinItemExpression){
			sbCountSql.append("  "+ joinItemExpression);
			
		}
		Object whereExpression= jsonObject.get("whereExpression");
		if(null!=whereExpression){
			sbCountSql.append(" where "+whereExpression);  
		}
		Object groupByExpression= jsonObject.get("groupByExpression");
		if(null!=whereExpression){
			sbCountSql.append(" group by "+groupByExpression);  
		}
		 
		System.out.println("sbCountSql--->"+sbCountSql);
		 
		return sbCountSql.toString();
		
	}
	
	private static void parseJoinItems(JSONObject jsonObject,
			List<Join> listofJoinItem) {
		StringBuilder joinItemExpression=new StringBuilder();  
		int indexOfJoin=0;
		for(Join joinItem:listofJoinItem){ 
			if(indexOfJoin>0){
				joinItemExpression.append(" , ");
			}
			joinItemExpression.append(joinItem.toString());  
			indexOfJoin++;
		}
		jsonObject.put("joinItemExpression", joinItemExpression.toString()); 
//		jsonObject.put("listofJoinItem", listofJoinItem.toString());
	}
	

	private static void parseConditionItems(Expression whereExpression,final JSONArray jsonConditionItemArray) {
		if(whereExpression instanceof BinaryExpression){
			BinaryExpression bWhExpr=(BinaryExpression)whereExpression;
			Expression leftExpr=bWhExpr.getLeftExpression();
			Expression rightExpr=bWhExpr.getRightExpression();
			if(leftExpr instanceof BinaryExpression){
				parseConditionItems(leftExpr,jsonConditionItemArray);
			}else{ 
				if("?".equals(rightExpr.toString().trim())){ 
					String strLeftExpr=leftExpr.toString().trim();
					if(strLeftExpr.split("\\.").length==2){
						jsonConditionItemArray.add(strLeftExpr.split("\\.")[1]);
					}else{
						jsonConditionItemArray.add(strLeftExpr);
						
					}
				}
			} 
			if(rightExpr instanceof BinaryExpression){
				parseConditionItems(rightExpr,jsonConditionItemArray);
			} 
		}//end of if
	}
	
	/**
	 * 
	 * @param plainSelect
	 * @return
	 */
	private static void parseSelectItems(List<SelectExpressionItem> listOfSelectItem,final JSONArray jsonSelectItemArray) {
		
		for(SelectExpressionItem selectItem:listOfSelectItem){
			JSONObject jsonSelectItemObject=new JSONObject();
			 
			String alias=selectItem.getAlias();
			jsonSelectItemObject.put("alias", alias);
			Expression expression=selectItem.getExpression();
			jsonSelectItemObject.put("expression", expression.toString());
			if(expression instanceof Column){
				Column column=(Column)expression;
				String columnName=column.getColumnName();
				String wholeColumnName=column.getWholeColumnName();
				jsonSelectItemObject.put("columnName", columnName);
				jsonSelectItemObject.put("wholeColumnName", wholeColumnName);
			}else{
				jsonSelectItemObject.put("columnName", alias);
				jsonSelectItemObject.put("wholeColumnName", expression.toString());
				
			}
			jsonSelectItemArray.add(jsonSelectItemObject);
		} 
	}
  
	public static void main(String[] args) throws JSQLParserException {  
//		parseSqlText(testSqlSource);
		JSONObject jsonObject=parseSqlTextWithSql(testSqlSource);
		System.out.println("jsonObject--->"+jsonObject);
	}

	 
}
