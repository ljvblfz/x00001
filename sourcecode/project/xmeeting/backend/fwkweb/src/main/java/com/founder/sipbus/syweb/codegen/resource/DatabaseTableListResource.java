package com.founder.sipbus.syweb.codegen.resource;

import java.util.List;

import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.metadata.TableMetaDataContext;
import org.springframework.jdbc.core.metadata.TableMetaDataProvider;
import org.springframework.jdbc.core.metadata.TableMetaDataProviderFactory;
import org.springframework.jdbc.core.metadata.TableParameterMetaData;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/codegen/database/tablelist")
public class DatabaseTableListResource extends SyBaseResource {
	 

	@Override
	protected void doInit() throws ResourceException {  
		
	}

	@Get
	public Representation get(Representation entity)  {
		TableMetaDataContext context=new TableMetaDataContext();
		context.setSchemaName("SIPBUS2");
		context.setTableName("hr_position");
		TableMetaDataProvider tableMetaDataProvider=TableMetaDataProviderFactory.createMetaDataProvider(businessDataSource, context); 
		List<TableParameterMetaData> listOf=tableMetaDataProvider.getTableParameterMetaData(); 
		
		JSONArray jsonArray=new JSONArray();
		for(TableParameterMetaData tableParameterMetaData:listOf){
			System.out.println("----tableParameterMetaData--------->"+tableParameterMetaData.getParameterName());
			jsonArray.add(tableParameterMetaData.getParameterName()+"--"+tableParameterMetaData.getSqlType()+"--"+tableParameterMetaData.isNullable());
		}
		JSONObject tableMetadataJSON=new JSONObject();
		tableMetadataJSON.put("columns", jsonArray);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(tableMetadataJSON));
	}
	
	//=============================
	private DataSource businessDataSource; 
	public void setBusinessDataSource(DataSource businessDataSource) {
		this.businessDataSource = businessDataSource;
	}
	
	 

}
