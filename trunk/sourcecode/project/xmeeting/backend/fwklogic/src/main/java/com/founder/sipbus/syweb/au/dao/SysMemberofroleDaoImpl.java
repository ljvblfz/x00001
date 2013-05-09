package com.founder.sipbus.syweb.au.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.AbstractBaseDaoImpl;
import com.founder.sipbus.syweb.au.po.SysMemberofrole;
@Component
public class SysMemberofroleDaoImpl extends AbstractBaseDaoImpl<SysMemberofrole, String> {
	 
	public ArrayList<Long> countMemberByAuRoleGuid(String[] guids){
		ArrayList<Long> la=new ArrayList<Long>();
	//	ArrayList<String> sa=new ArrayList<String>();
		try {
			 
 			String hql = " from SysMemberofrole sm where 1=1 and " +
 					" EXISTS( select sm.rolename from  AuRoles ar" +
 					" where sm.rolename=ar.name and ar.guid=?)";
			
				
		 
			 for(String id:guids)
			 {	String[] guid=new String[]{id};
				 
				Long l= this.getHQLReulstCount(hql,guid);
				if(l!=null&&l.longValue()==0){
					la.add(l);
					
					//sa.add(id);
				}else {}
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return la;
		
		
		
		
		
	}
	
	
	public List<SysMemberofrole> queryRolesByUserId(String member){
		String hql = "from SysMemberofrole t where t.member = '"+ member+ "'";
		return this.findByHql(hql);
	}
	 
}
