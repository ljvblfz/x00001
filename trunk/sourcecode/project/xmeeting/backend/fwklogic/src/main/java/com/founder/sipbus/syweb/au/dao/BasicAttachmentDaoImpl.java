/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus.syweb.au.dao;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.au.po.BasicAttachment;

@Component
public class BasicAttachmentDaoImpl extends DefaultBaseDaoImpl<BasicAttachment, String> {

//	public List<SyCode> getSyCode(String typeId) {
//		return findByHql("from SyCode where delFlag=0 and " + SyCode.TYPE_ID
//				+ "=? order by codeSeq asc", typeId);
//	}
//
//	public List<SyCode> getSyCodeByTypeName(String typeName) {
//		String hql = "select c from SyCode c,SyCodeType t where c.delFlag=0 and c.typeId = t.typeId and t.typeName = ? order by c.codeSeq asc";
//		return findByHql(hql, typeName);
//	}
//
//	public String getSyCodeName(String typeName, String codeValue) {
//		String hql = "select c.valueName from SyCode c,SyCodeType t where c.typeId = t.typeId and t.typeName = ? and c.valueCode = ?";
//		return (String) findUniqueByHqlNoEntityType(hql, typeName, codeValue);
//	}
//
//	public List<SyCode> getSyCodeByTypeNameAndUserId(String typeName,
//			String userId) {
//		String hql = "select c from SyCode c,SyCodeType t where c.typeId = t.typeId and t.typeName = ? and c.createBy = ? order by c.codeSeq asc";
//		return findByHql(hql, typeName, userId);
//	}
//
//	public Map<String, String> getAllCodeName(String typename) {
//		String hql = "select c from SyCode c,SyCodeType t where c.delFlag = 0 and c.typeId = t.typeId and t.typeName = ? ";
//		List list = findByHql(hql);
//		Map<String, String> map = new HashMap<String, String>();
//		for (int i = 0; i < list.size(); i++) {
//			SyCode syCode = (SyCode) list.get(i);
//			map.put(syCode.getValueCode(), syCode.getValueName());
//		}
//		return map;
//	}
//	
//	public String findSequence() {
//		String sequenceName = "seq_mt_orderno";
//		Long nextval = getSequence(sequenceName);
//		return nextval.toString();
//	}
}
