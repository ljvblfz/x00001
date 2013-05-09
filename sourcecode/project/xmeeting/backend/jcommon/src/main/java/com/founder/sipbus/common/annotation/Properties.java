package com.founder.sipbus.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**  
 * @author ahuaxuan(aaron zhang)  
 * @since 2008-4-7  
 * @version $Id: Properties.java 261 2008-04-07 07:03:41Z aaron $  
 */  
@Target(ElementType.FIELD)   
@Retention(RetentionPolicy.RUNTIME)    
public @interface Properties {   
  
//  String bundle();   
       
    String name();   
}  
