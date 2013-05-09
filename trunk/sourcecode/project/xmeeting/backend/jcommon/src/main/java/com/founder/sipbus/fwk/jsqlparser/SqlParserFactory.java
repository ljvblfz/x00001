package com.founder.sipbus.fwk.jsqlparser;

import java.io.StringReader;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class SqlParserFactory {
	private static CCJSqlParserManager parserManager = new CCJSqlParserManager();
	
	public static CCJSqlParserManager getSqlParserManager(){
		return parserManager;
	}
	
	public static PlainSelect parseSqlSource(String sqlSource){
		PlainSelect plainSelect=null;
		try {
			plainSelect = (PlainSelect) ((Select) parserManager.parse(new StringReader(sqlSource))).getSelectBody();
		} catch (JSQLParserException e) { 
			e.printStackTrace();
		}
		return plainSelect;
	}

}
