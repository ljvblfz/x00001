package com.broadsoft.xmeeting.websocket.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingCallDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingMessageDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingVoteDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingVoteDetailDaoImpl;

public class SpringDaoHolder {
	private Logger logger = LoggerFactory.getLogger(SpringDaoHolder.class);

	private SpringDaoHolder() {
	}

	private static SpringDaoHolder springDaoHolder = new SpringDaoHolder();

	public static SpringDaoHolder getInstance() { 
		return springDaoHolder;
	}

	// =======================IOC================>

	// 投票服务
	private XmMeetingVoteDaoImpl xmMeetingVoteDao;
	private XmMeetingVoteDetailDaoImpl xmMeetingVoteDetailDao;

	public XmMeetingVoteDaoImpl getXmMeetingVoteDao() {
		return xmMeetingVoteDao;
	}

	public void setXmMeetingVoteDao(XmMeetingVoteDaoImpl xmMeetingVoteDao) { 
		this.xmMeetingVoteDao = xmMeetingVoteDao;
	}

	public XmMeetingVoteDetailDaoImpl getXmMeetingVoteDetailDao() {
		return xmMeetingVoteDetailDao;
	}

	public void setXmMeetingVoteDetailDao(
			XmMeetingVoteDetailDaoImpl xmMeetingVoteDetailDao) {
		this.xmMeetingVoteDetailDao = xmMeetingVoteDetailDao;
	}

	// 消息服务
	private XmMeetingMessageDaoImpl xmMeetingMessageDao;

	public XmMeetingMessageDaoImpl getXmMeetingMessageDao() {
		return xmMeetingMessageDao;
	}

	public void setXmMeetingMessageDao(
			XmMeetingMessageDaoImpl xmMeetingMessageDao) {
		this.xmMeetingMessageDao = xmMeetingMessageDao;
	}

	// 呼叫服务
	private XmMeetingCallDaoImpl xmMeetingCallDao;

	public XmMeetingCallDaoImpl getXmMeetingCallDao() {
		return xmMeetingCallDao;
	}

	public void setXmMeetingCallDao(XmMeetingCallDaoImpl xmMeetingCallDao) {
		this.xmMeetingCallDao = xmMeetingCallDao;
	}

}
