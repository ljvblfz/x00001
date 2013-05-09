package com.founder.sipbus.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 
 * @author Tracy
 *
 */
@Retention(RetentionPolicy.RUNTIME) 
public @interface RestletResource {
	 String urls();
}


