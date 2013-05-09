/*
 * 改写Restlet Spring find 源码 ，结合SpringRouter 让Spring Resource不再依赖配置文件，根据annotation自动注入
 * 如不在乎配置文件大小则恢复SpringFinder和SpringRouter
 * 
 * By  Tracy chen 2012-3-1
 */

package org.restlet.ext.spring;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import org.hibernate.MappingException;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.engine.Engine;
import org.restlet.resource.Resource;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.founder.sipbus.common.annotation.RestletResource;

public class SpringRouter extends Router {

	public static void setAttachment(Router router, String path, Object route) {
		if (route instanceof Restlet)
			router.attach(path, (Restlet) route);
		else if (route instanceof Class)
			router.attach(path, (Class) route);
		else if (route instanceof String)
			try {
				Class resourceClass = Engine.loadClass((String) route);
				if (Resource.class.isAssignableFrom(resourceClass))
					router.attach(path, resourceClass);
				else if (ServerResource.class.isAssignableFrom(resourceClass))
					router.attach(path, resourceClass);
				else
					router
							.getLogger()
							.warning(
									"Unknown class found in the mappings. Only subclasses of org.restlet.resource.Resource and ServerResource are allowed.");
			} catch (ClassNotFoundException e) {
				router.getLogger().log(Level.WARNING,
						"Unable to set the router mappings", e);
			}
		else
			router
					.getLogger()
					.warning(
							"Unknown object found in the mappings. Only instances of Restlet and subclasses of org.restlet.resource.Resource and ServerResource are allowed.");
	}

	public static void setAttachments(Router router, Map routes) {
		String key;
		for (Iterator i$ = routes.keySet().iterator(); i$.hasNext(); setAttachment(
				router, key, routes.get(key)))
			key = (String) i$.next();

	}
	
	

	public SpringRouter() {
	}

	public SpringRouter(Context context) {
		super(context);
	}

	public SpringRouter(Restlet parent) {
		super(parent.getContext());
	}

	public void setAttachments(Map routes) {
		setAttachments(((Router) (this)), routes);
	}

	public void setDefaultAttachment(Object route) {
		setAttachment(this, "", route);
	}
	
	public void setPackagesToScan(String packagesToScan[]){
        this.packagesToScan = packagesToScan;
        scanPackages();
        
    }
    private String packagesToScan[];
    
    private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    
	 protected void scanPackages(){
		 
		if (packagesToScan != null)
			try {
				String as[];
				int j = (as = packagesToScan).length;
				for (int i = 0; i < j; i++) {
					String pkg = as[i];
					String pattern = (new StringBuilder("classpath*:")).append(
							ClassUtils.convertClassNameToResourcePath(pkg))
							.append("/**/*.class").toString();
					org.springframework.core.io.Resource resources[] = resourcePatternResolver
							.getResources(pattern);
					MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(
							resourcePatternResolver);
					org.springframework.core.io.Resource aresource[];
					int l = (aresource = resources).length;
					for (int k = 0; k < l; k++) {
						org.springframework.core.io.Resource resource = aresource[k];
						if (resource.isReadable()) {
							MetadataReader reader = readerFactory
									.getMetadataReader(resource);
							String className,simpleName ;
							SpringFinder springFinder;
							if(reader.getAnnotationMetadata().hasAnnotation(RestletResource.class.getName())){
								className = reader.getClassMetadata().getClassName();
								simpleName = ClassUtils.getShortName(className);
								simpleName = StringUtils.uncapitalize(simpleName);
								springFinder=new SpringFinder(simpleName);
								setAttachment(this,(String)reader.getAnnotationMetadata().getAnnotationAttributes(RestletResource.class.getName()).get("urls"),springFinder);
							}
						}
					}

				}

			} catch (IOException ex) {
				throw new MappingException(
						"Failed to scan classpath for unlisted classes", ex);
			} catch (Exception ex) {
				throw new MappingException(
						"Failed to load annotated classes from classpath", ex);
			}
	}
}
