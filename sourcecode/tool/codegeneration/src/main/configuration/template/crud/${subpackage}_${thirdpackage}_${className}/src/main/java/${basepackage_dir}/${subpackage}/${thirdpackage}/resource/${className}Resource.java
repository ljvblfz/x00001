<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign signlePrmaryKey = table.signlePrmaryKey>   
<#assign signlePrmaryKeyLower = signlePrmaryKey?uncap_first>   

package ${basepackage}.${subpackage}.${thirdpackage}.resource;

import java.util.Date;

import net.sf.json.*;

import org.restlet.data.*;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.*;
import org.restlet.resource.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ${commonpackage}.annotation.RestletResource;
import ${commonpackage}.resource.BaseResource;
import ${commonpackage}.util.*;
import ${basepackage}.${subpackage}.${thirdpackage}.dao.${className}DaoImpl;
import ${basepackage}.${subpackage}.${thirdpackage}.po.${className};
import ${basepackage}.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/${classNameLower}/{${signlePrmaryKeyLower}}")
public class ${className}Resource extends SyBaseResource{
	
	private String ${signlePrmaryKeyLower};
	
	private ${className}DaoImpl ${classNameLower}Dao;
	
	public void set${className}Dao(${className}DaoImpl ${classNameLower}Dao) {
		this.${classNameLower}Dao = ${classNameLower}Dao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		${signlePrmaryKeyLower}=getAttribute("${signlePrmaryKeyLower}");
		${className} ${classNameLower} =  ${classNameLower}Dao.findById(${signlePrmaryKeyLower});
		if(null==${classNameLower}){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(${classNameLower},config));
	}
	@Delete
	public Representation delete() {
		${classNameLower}Dao.delete(${signlePrmaryKeyLower});
		return new StringRepresentation(${signlePrmaryKeyLower});
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		${signlePrmaryKeyLower} =  form.getFirstValue("${signlePrmaryKeyLower}");
		${className} b= ${classNameLower}Dao.findById(${signlePrmaryKeyLower});
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}