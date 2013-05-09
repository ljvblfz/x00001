/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.uploadify.resource;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.fileupload.FileItem;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.commonweb.base.SingleFileUploadResource;

/***
 * 去掉权限验证-->swfupload有限制
 * 
 * @author lu.zhen
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/system/uploadify/upload/{module}")
public class UploadifySupportResource extends SingleFileUploadResource {

	private String module = "fwkweb";

	@Override
	protected void doInit() throws ResourceException {
		String temp=this.getAttribute("module");
		if(null!=temp||!"".equals(temp.trim())){
			this.module =temp; 
		}
	}

	@Post
	public Representation post(Representation entity) throws ResourceException { 
		FileItem fi = this.getUploadSingleFileItem(entity);
		JSONObject jsonObject = this.saveSingleFile(fi, module, getRandomFileNamePrefix()); 
		JSON jp = JSONSerializer.toJSON(jsonObject, config);
		return getJsonGzipRepresentation(jp);
	}//end of post
}