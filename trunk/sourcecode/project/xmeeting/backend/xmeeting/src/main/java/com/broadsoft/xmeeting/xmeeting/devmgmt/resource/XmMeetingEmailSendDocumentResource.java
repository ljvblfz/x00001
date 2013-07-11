/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.broadsoft.xmeeting.xmeeting.devmgmt.resource;

import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.integration.email.MailSenderInfo;
import com.broadsoft.integration.email.SendMailSupport;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingDocumentDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingEmailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingEmailHistoryLogDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingDocument;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingEmail;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingEmailHistoryLog;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingInfo;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.fwk.config.Environment;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/xmMeetingEmailSendDocument/xmmiGuid/{xmmiGuid}")
public class XmMeetingEmailSendDocumentResource extends SyBaseResource {
	private Logger logger = LoggerFactory .getLogger(XmMeetingEmailSendDocumentResource.class);

	private String xmmiGuid;

	@Override
	protected void doInit() throws ResourceException {
		xmmiGuid = getAttribute("xmmiGuid");
	}

	@Get
	public Representation get(Representation entity) {
		//

		XmMeetingInfo xmMeetingInfo=xmMeetingInfoDao.findById(xmmiGuid); 
		//
		StringBuilder sbCCAddress=new StringBuilder();
		List<XmMeetingEmail> listOfXmMeetingEmail = xmMeetingEmailDao.findByXmmiGuid(xmmiGuid);
		if(null==listOfXmMeetingEmail){
			return getJsonGzipRepresentation(JsonUtils.genFailureReturnJson(null, null)); 
		}
		for(int i=0;i<listOfXmMeetingEmail.size();i++){
			XmMeetingEmail xmMeetingEmail=listOfXmMeetingEmail.get(i);
			sbCCAddress.append(xmMeetingEmail.getXmmeToAddress());
			if(i<listOfXmMeetingEmail.size()-1){
				sbCCAddress.append(",");
			}
		}//end of for
		

		MailSenderInfo mailSenderInfo=SendMailSupport.createMailSenderInfo(); 
		mailSenderInfo.setSubject("江苏省电力公司会议会议资料("+xmMeetingInfo.getXmmiName()+")"); 
		mailSenderInfo.setCcAddress(sbCCAddress.toString());
		List<XmMeetingDocument> listOfXmMeetingDocument = xmMeetingDocumentDao.findByXmmiGuid(xmmiGuid);
		String[] attachmentFullPath=new String[listOfXmMeetingDocument.size()];
		String[] attachmentFileName=new String[listOfXmMeetingDocument.size()];
		for(int i=0;i<listOfXmMeetingDocument.size();i++){
			XmMeetingDocument doc=listOfXmMeetingDocument.get(i);
			String filePath=mergeFilePath(doc.getXmmdFile());
			String fileName=doc.getXmmdName()+"."+getFileExtByPath(filePath);
			attachmentFullPath[i]=filePath;
			attachmentFileName[i]=fileName; 
		}//end of for
		mailSenderInfo.setAttachFileNames(attachmentFileName);
		mailSenderInfo.setAttachFullPath(attachmentFullPath);
		mailSenderInfo.setContent("<b>"+xmMeetingInfo.getXmmiName()+"</b>的会议资料,请查看附件.<br/><br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;智能会议系统小组");
		//发送邮件
		SendMailSupport.sendHtmlMail(mailSenderInfo);
		//
		XmMeetingEmailHistoryLog xmMeetingEmailHistoryLog=new XmMeetingEmailHistoryLog();
		xmMeetingEmailHistoryLog.setXmmiGuid(xmMeetingInfo.getXmmiName());
		xmMeetingEmailHistoryLog.setXmmehlFrom(mailSenderInfo.getFromAddress());
		xmMeetingEmailHistoryLog.setXmmehlTo(mailSenderInfo.getToAddress());
		xmMeetingEmailHistoryLog.setXmmehlCc(mailSenderInfo.getCcAddress());
		xmMeetingEmailHistoryLog.setXmmehlStatu("1-成功发送邮件."); 
		xmMeetingEmailHistoryLogDao.insert(xmMeetingEmailHistoryLog);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null));
	}//end of get 
	
	
	
	private String mergeFilePath(String filePath){
		String mergePath="";
		String basePath=Environment.getInstance().getString("com.broadsoft.platform.upload.base.directory");
		basePath=basePath.replace("upload/", "");
		mergePath=basePath+filePath;
		if(logger.isDebugEnabled()){
			logger.debug("the merge path is {}",mergePath);
		}
		
		return mergePath;
	}//end of mergeFilePath
	
	private String getFileExtByPath(String fileName){
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}

	// IoC
	
	private XmMeetingInfoDaoImpl xmMeetingInfoDao;
	
	public void setXmMeetingInfoDao(XmMeetingInfoDaoImpl xmMeetingInfoDao) {
		this.xmMeetingInfoDao = xmMeetingInfoDao;
	}
	private XmMeetingDocumentDaoImpl xmMeetingDocumentDao;

	public void setXmMeetingDocumentDao(
			XmMeetingDocumentDaoImpl xmMeetingDocumentDao) {
		this.xmMeetingDocumentDao = xmMeetingDocumentDao;
	}

	private XmMeetingEmailDaoImpl xmMeetingEmailDao;

	public void setXmMeetingEmailDao(XmMeetingEmailDaoImpl xmMeetingEmailDao) {
		this.xmMeetingEmailDao = xmMeetingEmailDao;
	}
	

	private XmMeetingEmailHistoryLogDaoImpl xmMeetingEmailHistoryLogDao;
	
	public void setXmMeetingEmailHistoryLogDao(XmMeetingEmailHistoryLogDaoImpl xmMeetingEmailHistoryLogDao) {
		this.xmMeetingEmailHistoryLogDao = xmMeetingEmailHistoryLogDao;
	}

}