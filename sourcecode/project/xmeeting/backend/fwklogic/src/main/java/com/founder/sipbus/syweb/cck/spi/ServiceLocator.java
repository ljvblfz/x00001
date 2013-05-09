package com.founder.sipbus.syweb.cck.spi;

import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * 
 * @author lu.zhen
 *
 */
public class ServiceLocator {
	public static <T> T findService(Class<T> type, Predicate<T> p) {
        for (T service : ServiceLoader.load(type)) {
                try {
                        if (p.matches(service)) {
                              return service;
                        }
                }
                catch (ServiceConfigurationError e){
                        e.printStackTrace();
                }
        }
        return null;
	}

}
