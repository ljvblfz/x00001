package com.founder.sipbus.aop.license;
 


aspect RestletLicenseVerifying extends AbstractLicenseVerifying {


	 
   pointcut licensedOperation() : call(* com.founder.sipbus.syweb.*.resource.*Resource.*(..) ) ;
      		 

    
    
}