package com.founder.sipbus.aop.license;


import java.sql.Timestamp;
import com.founder.sipbus.licesemgmt.ILicenseManagement;
import com.founder.sipbus.licesemgmt.LicenseManagementFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.founder.sipbus.common.exception.FounderException;


public  abstract aspect AbstractLicenseVerifying {

  private Logger logger = LoggerFactory.getLogger(com.founder.sipbus.aop.license.AbstractLicenseVerifying.class); 
  
  
  
 
  
  
   abstract pointcut licensedOperation() ;
    	 
 
 
   before() :  licensedOperation() {  
		 // System.out.println("##########license checked##########");  
		if(false){
		 	return;
		}
	    ILicenseManagement licenseManagement=LicenseManagementFactory.getLicenseManagement();
		String licenseValidDate=licenseManagement.getValidDate();
		Timestamp licenseValidTime=com.founder.sipbus.common.util.TimeUtils.string2Timestamp("yyyy-MM-dd",licenseValidDate); 
		String serialNo=licenseManagement.generateSerialNumber("");
		//
		String licenseSerialNo=licenseManagement.getFeatureValue("serialno");
		
		String machineSerialNo=licenseManagement.generateSerialNumber("");
		if(!licenseSerialNo.equals(machineSerialNo)){
			throw new FounderException(100999,"",serialNo); 
		}else  if(!licenseValidTime.after(new Timestamp(System.currentTimeMillis()))){
			throw new FounderException(100998);
		} 
		

	   
	}
	 
  
 
}