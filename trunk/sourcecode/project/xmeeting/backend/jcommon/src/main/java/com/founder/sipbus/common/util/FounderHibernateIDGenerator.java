package com.founder.sipbus.common.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.exception.JDBCExceptionHelper;
import org.hibernate.id.Configurable;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.mapping.Table;
import org.hibernate.type.Type;

public class FounderHibernateIDGenerator extends SequenceGenerator implements
		PersistentIdentifierGenerator, Configurable {
	public static final String SEQUENCE = "sequence";
	public static final String TABLENAME = "tableName";
	public static final String PARAMETERS = "parameters";
	private String sequenceName;
//	private String tableName;
	private String parameters;
	private Type identifierType;
	private String sequenceSql;
	private static final Log log = LogFactory
			.getLog(FounderHibernateIDGenerator.class);

	public void configure(Type type, Properties params, Dialect dialect)
			throws MappingException {
		this.sequenceName = "GLOBAL_ID_GEN_SEQ";// PropertiesHelper.getString("sequence",
												// params,
												// "hibernate_sequence");
		this.parameters = params.getProperty("parameters");
		String schemaName = params.getProperty("schema");
		// this.tableName= params.getProperty("tableName");
		String catalogName = params.getProperty("catalog");

		if (this.sequenceName.indexOf(46) < 0) {
			this.sequenceName = Table.qualify(catalogName, schemaName,
					this.sequenceName);
		}

		this.identifierType = type;
		this.sequenceSql = dialect.getSequenceNextValString(this.sequenceName);
	}

	public Serializable generate(SessionImplementor session, Object obj)
			throws HibernateException { 
		String tableName = ""; 
		
		//get table name from po configuration
		javax.persistence.Table table = obj.getClass().getAnnotation(
				javax.persistence.Table.class);
		tableName = table.name(); 
		//Get sequence value
		Serializable seqValue = IDGenerationAgorithmn.getSequenceValueBySessionImplementor(session,this.sequenceSql);
		String id = IDGenerationAgorithmn.generateID(tableName,seqValue);
		return id;
	}

	
 


	public String[] sqlCreateStrings(Dialect dialect) throws HibernateException {
		String[] ddl = dialect.getCreateSequenceStrings(this.sequenceName);
		if (this.parameters != null) {
			int tmp28_27 = (ddl.length - 1);
			String[] tmp28_23 = ddl;
			tmp28_23[tmp28_27] = tmp28_23[tmp28_27] + ' ' + this.parameters;
		}
		return ddl;
	}

	public String[] sqlDropStrings(Dialect dialect) throws HibernateException {
		return dialect.getDropSequenceStrings(this.sequenceName);
	}

	public Object generatorKey() {
		return this.sequenceName;
	}

	public String getSequenceName() {
		return this.sequenceName;
	}
}