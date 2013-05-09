package com.founder.sipbus.fwk.jsqlparser;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.Distinct;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author lu.zhen
 * http://www.th7.cn/Program/java/2012/06/29/82566.shtml
 *
 */
public class IBatisDynamicSqlParserSupport  { 
 
	private static String testSqlSource4="select t.lname,t.lbguid,t.lcompguid,t.ldepid,t.lticketrule  from line_linebasicinfo t where t.isdeleted=0 and t.issip=? order by t.lbguid";
	private static String testSqlSource6="select t.lname,t.lbguid,t.lcompguid,x.orgname,t.lticketrule from line_linebasicinfo   t,sys_org x  where 1=1 and t.ldepid=x.deptid and t.isdeleted = 0   and t.issip=? order by t.lbguid";
	 
	
	private static String testSqlSource5=" SELECT DISTINCT B.LNAME,L.LDIRECTION,NVL(T.LIGUID,' ') LIGUID FROM TB_BUSSTATUSINFO T INNER JOIN LINE_LINEINFO L ON T.LIGUID=L.LIGUID INNER JOIN LINE_LINEBASICINFO B ON B.LBGUID=L.LBGUID AND L.ISDELETED=0 AND B.ISDELETED=0 AND T.MODIFYDATE<(SYSDATE-1/48)  ";
	
	private static String selectSqlTemplate=" select {0} from {1} where 1=1    {2} {3}";  
	private static String countSelectSqlTemplate=" select count(1) from ( {0}  ) "; 

	
  
	/**
	 * 
	 * @param sqltext
	 * @return
	 * @throws JSQLParserException
	 */
	public static JSONObject genIbatisSelectSqlWithParam(String sqltext,final Map<String,String> param)   { 
		PlainSelect plainSelect=SqlParserFactory.parseSqlSource(sqltext);
		 
		//查询结果item
		StringBuilder selectItemExpression=new StringBuilder();
		
//		System.out.println("getDistinct------->"+plainSelect.getDistinct());
		List<SelectExpressionItem> listOfSelectItem=plainSelect.getSelectItems();
		int indexOfSelectItem=0;
		for(SelectExpressionItem selectExpressionItem:listOfSelectItem){
			Expression expression=selectExpressionItem.getExpression();
			String alias=selectExpressionItem.getAlias();
			if(indexOfSelectItem>0){
				selectItemExpression.append(",");
			}
			if(indexOfSelectItem==0){ 
				Distinct distinct=plainSelect.getDistinct();
				if(null!=distinct){
					selectItemExpression.append(distinct.toString()+"  ");
					
				}
			}
			selectItemExpression.append(expression.toString());
			//ALIAS
			if(null!=alias&&!"".equals(alias)){
				selectItemExpression.append("  "+alias); 
			}
			indexOfSelectItem++; 
		}
		StringBuilder fromAndJoinItemExpression=new StringBuilder();
		//from
		FromItem fromItem=plainSelect.getFromItem();
		String strFromItem=fromItem.toString();
		String xStr=strFromItem.replaceAll(" AS ", " ");
		fromAndJoinItemExpression.append(xStr);
		//join
		List<Join> listofJoinItem=plainSelect.getJoins(); 
		if(null!=listofJoinItem){ 
			for(Join join:listofJoinItem){ 
				if(join.toString().toUpperCase().contains("JOIN")){
					fromAndJoinItemExpression.append("   "+join.toString()); 
				}else{
					fromAndJoinItemExpression.append("  , "+join.toString()); 
					
				}
			}
		}
		//查询条件item  
		StringBuilder whereItemExpression=new StringBuilder();
		Expression whereExpression=plainSelect.getWhere();
		if(null!=whereExpression){
			parseConditionItems(whereExpression,whereItemExpression,param); 
		}
		//group by
		StringBuilder groupByItemExpression=new StringBuilder(); 
		List<Column> listofGroupByItem=plainSelect.getGroupByColumnReferences();
		if(null!=listofGroupByItem){  
			groupByItemExpression.append(" group by ");
			int indexOfGroupBy=0;
			for(Column column:listofGroupByItem){
				if(indexOfGroupBy>0){
					groupByItemExpression.append(" , ");
				}
				groupByItemExpression.append(column.toString());
				indexOfGroupBy++;
			} 
		}
		//order by
		StringBuilder orderByItemExpression=new StringBuilder();
		List<OrderByElement> listofOrderByItem=plainSelect.getOrderByElements(); 
		if(null!=listofOrderByItem){ 
			orderByItemExpression.append(" order by ");
			int indexOfOrderBy=0;
			for(OrderByElement element:listofOrderByItem){
				if(indexOfOrderBy>0){
					orderByItemExpression.append(" , ");
				}
				orderByItemExpression.append(element.toString());
				indexOfOrderBy++;
			}  
			
		}
		//生成查询sql
		String[ ] variables = new String[4] ; 
		variables[0]=selectItemExpression.toString();

		String strFromAndJoinItemExpression=fromAndJoinItemExpression.toString();
		String xstrFromAndJoinItemExpression=strFromAndJoinItemExpression.replaceAll(" AS ", " ");
		variables[1]=xstrFromAndJoinItemExpression;
		variables[2]=whereItemExpression.toString();
		if(!"".equals(variables[2].trim())){
			variables[2]=" and "+variables[2];
		}
		variables[3]=groupByItemExpression.toString() +" "+orderByItemExpression.toString();
		String strSqltext = MessageFormat.format( selectSqlTemplate, variables );  
		
		//counter sql
		String[ ] variablesx = new String[1] ; 
		variablesx[0]=strSqltext;
		String strCountSqltext = MessageFormat.format( countSelectSqlTemplate, variablesx );  
		 
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("sqltext", strSqltext);;
		jsonObject.put("countsqltext", strCountSqltext);
		return jsonObject; 
	}
	
	
	
	
	private static void parseConditionItems(Expression whereExpression,final StringBuilder whereItemExpression,final Map<String,String> param) {
		if(whereExpression instanceof BinaryExpression){
			BinaryExpression bWhExpr=(BinaryExpression)whereExpression;
			Expression leftExpr=bWhExpr.getLeftExpression();
			Expression rightExpr=bWhExpr.getRightExpression();
			if(leftExpr instanceof BinaryExpression){
				parseConditionItems(leftExpr,whereItemExpression,param);
			}else{ 
				String strLeftExpr=leftExpr.toString().trim();
				String strRightExpr=rightExpr.toString().trim(); 
				
				if("?".equals(strRightExpr)){ 
					String strFieldName=strLeftExpr; 
					if(strLeftExpr.split("\\.").length==2){ 
						strFieldName=strLeftExpr.split("\\.")[1];  
					}  
					 
					if(!StringUtils.isBlank(param.get(strFieldName.toUpperCase()))){  
						
						whereItemExpression.append(" and "); 
						whereItemExpression.append(strLeftExpr);
						whereItemExpression.append(" = ");
//						whereItemExpression.append("#");
//						whereItemExpression.append(strFieldName); 
//						whereItemExpression.append("#"); 

						whereItemExpression.append("'");
						whereItemExpression.append(param.get(strFieldName.toUpperCase())); 
						whereItemExpression.append("'");
						
					}
				}else{
					if(!"1".equals(strLeftExpr)){
						whereItemExpression.append(" and "); 
					}
					whereItemExpression.append(whereExpression.toString());
				}
			} 
			if(rightExpr instanceof BinaryExpression){
				parseConditionItems(rightExpr,whereItemExpression,param);
			} 
		}//end of if
	}
 
  
	public static void main(String[] args) throws JSQLParserException {  
		Map<String,String> param=new HashMap<String,String>();
		param.put("ISSIP", "0");
//		parseSqlText(testSqlSource);
//		JSONObject jsonObject  =genIbatisSelectSqlWithParam(testSqlSource4,param);
//		JSONObject jsonObject  =genIbatisSelectSqlWithParam(testSqlSource5,param);
		JSONObject jsonObject  =genIbatisSelectSqlWithParam(testSqlSource6,param);
		
		
		System.out.println("jsonObject--->"+jsonObject);
	}

	 
}
