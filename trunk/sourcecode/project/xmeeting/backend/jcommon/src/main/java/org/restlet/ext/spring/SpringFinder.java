/*
 * 改写Restlet Spring find 源码 ，结合SpringRouter 让Spring Resource不再依赖配置文件，根据annotation自动注入
 * 如不在乎配置文件大小则恢复SpringFinder和SpringRouter
 * 
 * By  Tracy chen 2012-3-1
 */

package org.restlet.ext.spring;

import java.util.logging.Level;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.resource.Finder;
import org.restlet.resource.Resource;
import org.restlet.resource.ServerResource;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringFinder extends Finder
{

    public SpringFinder()
    {
    }

    public SpringFinder(Context context)
    {
        super(context);
    }

    public SpringFinder(Context context, Class targetClass)
    {
        super(context, targetClass);
    }

    public SpringFinder(Restlet restlet)
    {
        super(restlet.getContext());
    }
    
    private String beanName;
    
    public SpringFinder(String beanName)
    {
        this.beanName=beanName;
    }

    
    
    public ServerResource create()
    {
    	if(null!=beanName){
    		return (ServerResource)ac.getBean(beanName);
    	}
    	
        ServerResource result = null;
        if(getTargetClass() != null)
            try
            {
                result = (ServerResource)getTargetClass().newInstance();
            }
            catch(Exception e)
            {
                getLogger().log(Level.WARNING, "Exception while instantiating the target server resource.", e);
            }
        return result;
    }

    public ServerResource create(Class targetClass, Request request, Response response)
    {
        return create(request, response);
    }

    private ApplicationContext ac;
    public ServerResource create(Request request, Response response)
    {
    	if(null==ac)
    		ac=WebApplicationContextUtils.getWebApplicationContext(ServletUtils.getRequest(request).getSession().getServletContext()); 
    	
        return create();
    }

    public Resource createResource()
    {
        Resource result = null;
        if(getTargetClass() != null)
            try
            {
                result = (Resource)getTargetClass().newInstance();
            }
            catch(Exception e)
            {
                getLogger().log(Level.WARNING, "Exception while instantiating the target resource.", e);
            }
        return result;
    }

    public Resource createTarget(Request request, Response response)
    {
        Resource result = createResource();
        if(result != null)
            result.init(getContext(), request, response);
        return result;
    }

//    public volatile Handler createTarget(Request x0, Response x1)
//    {
//        return createTarget(x0, x1);
//    }
}