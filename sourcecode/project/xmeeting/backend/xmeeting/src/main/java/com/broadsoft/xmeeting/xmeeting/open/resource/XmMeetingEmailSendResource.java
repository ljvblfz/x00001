/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import net.sf.json.JSONSerializer;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingEmailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingEmail;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/open/sendmms/xmmiGuid/{xmmiGuid}/toAddress/{toAddress}/toName/{toName}")
public class XmMeetingEmailSendResource extends SyBaseResource{
	
	private String xmmiGuid;
	private String toAddress;
	private String toName;
	
	private XmMeetingEmailDaoImpl xmMeetingEmailDao;
	
	public void setXmMeetingEmailDao(XmMeetingEmailDaoImpl xmMeetingEmailDao) {
		this.xmMeetingEmailDao = xmMeetingEmailDao;
	}

	@Override
    protected void doInit() throws ResourceException {
		xmmiGuid=this.getAttribute("xmmiGuid");
		toAddress=this.getAttribute("toAddress");
		toName=this.getAttribute("toName");
	}
	
	@Get
	public Representation get(Representation entity) { 
		XmMeetingEmail xmMeetingEmail=new XmMeetingEmail(); 
		xmMeetingEmail.setXmmiGuid(xmmiGuid);
		xmMeetingEmail.setXmmeToAddress(toAddress);
		xmMeetingEmail.setXmmeToName(toName);
		xmMeetingEmail.setXmmeAttachment("all");
		xmMeetingEmail.setXmmeStatus("1");
		xmMeetingEmailDao.insert(xmMeetingEmail);
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingEmail,config));
	} 
}