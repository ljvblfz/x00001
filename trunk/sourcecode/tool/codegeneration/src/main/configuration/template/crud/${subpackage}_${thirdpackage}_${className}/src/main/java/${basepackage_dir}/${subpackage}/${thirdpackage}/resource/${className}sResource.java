<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign signlePrmaryKey = table.signlePrmaryKey>   
<#assign signlePrmaryKeyLower = signlePrmaryKey?uncap_first>   

package ${basepackage}.${subpackage}.${thirdpackage}.resource;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ${commonpackage}.annotation.RestletResource;
import ${commonpackage}.page.PageResponse;
import ${commonpackage}.util.JsonUtils;
import ${commonpackage}.util.PMGridCopyUtil;
import ${basepackage}.${subpackage}.${thirdpackage}.dao.${className}DaoImpl;
import ${basepackage}.${subpackage}.${thirdpackage}.po.${className};
import ${basepackage}.${subpackage}.${thirdpackage}.vo.${className}SearchVO;
import ${basepackage}.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/${classNameLower}")
public class ${className}sResource extends SyBaseResource {
	
	private ${className}DaoImpl ${classNameLower}Dao;
	
	public void set${className}Dao(${className}DaoImpl ${classNameLower}Dao) {
		this.${classNameLower}Dao = ${classNameLower}Dao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		${className}SearchVO sVO=new ${className}SearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = ${classNameLower}Dao.findPage(getPageRequest(),fillDetachedCriteria(${className}.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("${signlePrmaryKeyLower}s");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			${classNameLower}Dao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		${className} ${classNameLower} = new ${className}();
		PMGridCopyUtil.copyGridToDto(${classNameLower}, form.getValuesMap());
		${classNameLower}Dao.add(${classNameLower});
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}