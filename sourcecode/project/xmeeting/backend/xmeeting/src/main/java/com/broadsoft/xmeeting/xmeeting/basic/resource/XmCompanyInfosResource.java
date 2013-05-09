/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.basic.resource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.fileupload.FileItem;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmCompanyInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmCompanyInfo;
import com.broadsoft.xmeeting.xmeeting.basic.vo.XmCompanyInfoSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.commonweb.base.SingleFileUploadResource;

import com.founder.sipbus.syweb.au.service.SyCodeService;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmCompanyInfo")
public class XmCompanyInfosResource extends SingleFileUploadResource {
	private Logger logger = LoggerFactory.getLogger(XmCompanyInfosResource.class);
	
	private XmCompanyInfoDaoImpl xmCompanyInfoDao;
	
	public void setXmCompanyInfoDao(XmCompanyInfoDaoImpl xmCompanyInfoDao) {
		this.xmCompanyInfoDao = xmCompanyInfoDao;
	}
	

	private SyCodeService syCodeService;

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmCompanyInfoSearchVO sVO=new XmCompanyInfoSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmCompanyInfoDao.findPage(getPageRequest(),fillDetachedCriteria(XmCompanyInfo.class,sVO));
		List list = p.getList();
		for (int i = 0; i < list.size(); i++) {
			XmCompanyInfo xmCompanyInfo = (XmCompanyInfo) list.get(i);
			xmCompanyInfo.setXmciTypeLabel(syCodeService.getSyCodeName("3001", xmCompanyInfo.getXmciType()));// 从码表读出对应名称 
			xmCompanyInfo.setXmciStatusLabel(syCodeService.getSyCodeName("3002", xmCompanyInfo.getXmciStatus()));// 从码表读出对应名称 
		}
		
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmciGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmCompanyInfoDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	@Post
	public Representation post(Representation entity)
			throws ResourceException {  
		if(logger.isTraceEnabled()){
			logger.trace("post begin.");
		}
		form = new Form(entity);
		XmCompanyInfo xmCompanyInfo = new XmCompanyInfo();  
		//
		PMGridCopyUtil.copyGridToDto(xmCompanyInfo, form.getValuesMap());
		xmCompanyInfoDao.add(xmCompanyInfo);   
		if(logger.isTraceEnabled()){
			logger.trace("post end.");
		}
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
	
	
//	@Post
//	public Representation post(Representation entity)
//			throws ResourceException {  
//		if(logger.isTraceEnabled()){
//			logger.trace("post begin.");
//		}
//		XmCompanyInfo xmCompanyInfo = new XmCompanyInfo(); 
//		//
//		List<FileItem>  listOfFileItem=this.getUploadFileItems(entity); 
//		String navTabId="navTabId";
//		String callbackType="callbackType";
//		String callBackMethod="callBackMethod";
//		if(null!=listOfFileItem){ 
//			for(FileItem fileItem:listOfFileItem){
//				String fieldname = fileItem.getFieldName();
//				if(logger.isTraceEnabled()){
//					logger.trace("The field name is: {}.",fieldname);
//				}
//				if(fileItem.isFormField()){
//					if("xmciCompanyName".equals(fieldname)){
//						xmCompanyInfo.setXmciCompanyName(fileItem.getString());
//					}else  if("xmciType".equals(fieldname)){
//						xmCompanyInfo.setXmciType(fileItem.getString());
//						
//					}else  if("xmciStatus".equals(fieldname)){
//						xmCompanyInfo.setXmciStatus(fileItem.getString());
//						
//					}else  if("xmciDescription".equals(fieldname)){
//						xmCompanyInfo.setXmciDescription(fileItem.getString()); 
//					} else  if("navTabId".equals(fieldname)){
//						navTabId=fileItem.getString(); 
//						
//					}else  if("callbackType".equals(fieldname)){
//						callbackType=fileItem.getString(); 
//						
//					}else  if("callBackMethod".equals(fieldname)){
//						callBackMethod=fileItem.getString(); 
//					} 
//				}else{
//					if("xmciAttachmentFile".equals(fieldname)){
//						String fileUrlPath=saveSingleFile(fileItem,"xmeeting",this.getRandFileName());  
//						if(logger.isTraceEnabled()){
//							logger.trace("The saved file is: {}.",fileUrlPath);
//						}
//						xmCompanyInfo.setXmciAttachment(fileUrlPath);
//					}
//				}//end of elese
//			}
//			xmCompanyInfoDao.add(xmCompanyInfo);  
//		}//end of if
//		//
//		JSONObject jsonObject= JsonUtils.genReturnJson("200", "操作成功", navTabId,callbackType, callBackMethod);
//		if(logger.isTraceEnabled()){
//			logger.trace("post end.");
//		}
//		return getJsonGzipRepresentation(jsonObject);   
//	}
	
	
 
	  
	
//	private String saveSingleFile(FileItem fi,String module,String strSavedFileNamePrefix)  {
//		String contentType=fi.getContentType();
//		String strFileName=fi.getName();
//		String fileExtName=strFileName.substring(strFileName.lastIndexOf("."));
//		String saveFileName=strSavedFileNamePrefix+fileExtName; 
//		String fileUrlPath=this.getBaseUplodUrl()+module+"/"+saveFileName;
//		String fileDirectory=this.getBaseUplodDirectory()+module+"/";
//		
//		
//		//创建新目录
//		File fp=new File(fileDirectory);
//		if (!fp.exists()) 
//		{
//			fp.mkdirs();
//		}
//		//创建新文件 
//		File fullFile = new File(saveFileName);
//		File savedFile = new File(fp, fullFile.getName());
//		if (!savedFile.exists()) {
//			try {
//				savedFile.createNewFile();
//			} catch (IOException e) { 
//				e.printStackTrace();
//			}
//		} 
//		try {
//			fi.write(savedFile);
//		} catch (Exception e) { 
//			e.printStackTrace();
//		}  
//		//
//		return fileUrlPath;
//	}//end of saveSingleFile
}