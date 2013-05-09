package com.founder.sipbus.common.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.exception.JDBCExceptionHelper;

/**
 * PK Generation agorithmn
 * @author Administrator
 *
 */
public class IDGenerationAgorithmn {
	
	
	public static String generateID(String tableName,Serializable seqID) {
		String id8 = seqID.toString();

		while (id8.length() < 8) {
			id8 = "0" + id8;
		}
		if (id8.length() > 8) {
			id8 = id8.substring(id8.length() - 8, id8.length());
		}

		String id = getTableStr(tableName) + getDateStr() + id8;
		while (id.length() < 36)
			id = "0" + id;
		return id;
	}
	
	
	public   static Serializable getSequenceValueBySessionImplementor(SessionImplementor sessionImplementor ,String sequenceSql) {
		Serializable seqValue=null;
		try {
			PreparedStatement st = sessionImplementor.getBatcher().prepareSelectStatement(sequenceSql);
			try {
				ResultSet rs = st.executeQuery();
				try {
					rs.next();
					Serializable result = rs.getBigDecimal(1).toString();
 
					seqValue = result; 
					//rs.close();
				
				} finally {
					rs.close();
				}
			} finally {
				sessionImplementor.getBatcher().closeStatement(st);
			}
		} catch (SQLException sqle) {
			throw JDBCExceptionHelper.convert(sessionImplementor.getFactory()
					.getSQLExceptionConverter(), sqle,
					"could not get next sequence value", sequenceSql);
		}
		return seqValue;
	}
	
	
	
	public   static Serializable getSequenceValueBySession(Session session ,String sequenceSql) {
		Serializable seqValue=null;
		try {
			Connection cnn = session.connection(); 
			PreparedStatement st = cnn.prepareStatement(sequenceSql);
			try {
				ResultSet rs = st.executeQuery();
				try {
					rs.next();
					Serializable result = rs.getBigDecimal(1).toString();
 
					seqValue = result;  
				
				} finally { 
					rs.close();
				}
			} finally { 
				if(null!=st){
					st.close();
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(  "could not get next sequence value for sql: "+sequenceSql);
		}
		return seqValue;
	}
	

	public static String getSequenceNextValStringOnOracle(String sequenceName) {
		if(null==sequenceName||"".equals(sequenceName)){
			sequenceName="GLOBAL_ID_GEN_SEQ";
		}
		return "select " + getSelectSequenceNextValString( sequenceName ) + " from dual";
	}
	
	private static String getSelectSequenceNextValString(String sequenceName) {
		return sequenceName + ".nextval";
	}
	

	private static String getTableStr(String table_name) {
		table_name = table_name.replaceAll("_", "");
		if (table_name.length() > 18) {
			table_name = table_name.substring(0, 18);
		}
		return table_name;
	}

	private static String getDateStr() {
		return new SimpleDateFormat("yyMMdd").format(new Date());
	}
}
