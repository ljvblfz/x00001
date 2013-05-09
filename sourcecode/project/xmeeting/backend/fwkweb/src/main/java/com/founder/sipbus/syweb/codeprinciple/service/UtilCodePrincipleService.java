package com.founder.sipbus.syweb.codeprinciple.service;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.founder.sipbus.syweb.codeprinciple.dao.UtilCodePrincipleDaoImpl;
import com.founder.sipbus.syweb.codeprinciple.dao.UtilCodePrincipleDetailDaoImpl;
import com.founder.sipbus.syweb.codeprinciple.po.UtilCodePrincipleDetail;
 

@Component
public class UtilCodePrincipleService {
private UtilCodePrincipleDaoImpl utilCodePrincipleDao;
	
	private UtilCodePrincipleDetailDaoImpl utilCodePrincipleDetailDao;
	 
	public UtilCodePrincipleDaoImpl getUtilCodePrincipleDao() {
		return utilCodePrincipleDao;
	}

	public void setUtilCodePrincipleDao(
			UtilCodePrincipleDaoImpl utilCodePrincipleDao) {
		this.utilCodePrincipleDao = utilCodePrincipleDao;
	}

	public UtilCodePrincipleDetailDaoImpl getUtilCodePrincipleDetailDao() {
		return utilCodePrincipleDetailDao;
	}

	public void setUtilCodePrincipleDetailDao(UtilCodePrincipleDetailDaoImpl utilCodePrincipleDetailDao) {
		this.utilCodePrincipleDetailDao = utilCodePrincipleDetailDao;
	}
	public void addAllUtilCodePrincipleDetail(ArrayList<UtilCodePrincipleDetail> details) {		 
			 utilCodePrincipleDetailDao.addAll(details);
	}

	public void updateDetailByucpGuid(String id,
			ArrayList<UtilCodePrincipleDetail> details) {
		utilCodePrincipleDetailDao.deleteByucpGuid(id);
		for (UtilCodePrincipleDetail utilCodePrincipleDetail : details) {
			utilCodePrincipleDetail.setUcpGuid(id);
		}
		utilCodePrincipleDetailDao.addAll(details);
	}

	public void deleteUcpAndUcpd(String[] ids) {
		 for (String string : ids) {
			 utilCodePrincipleDao.delete(string);
			 utilCodePrincipleDetailDao.deleteByucpGuid(string);
		}
		
	}

 
}
