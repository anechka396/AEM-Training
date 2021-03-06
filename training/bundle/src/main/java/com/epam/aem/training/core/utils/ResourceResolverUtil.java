package com.epam.aem.training.core.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

public class ResourceResolverUtil {
	
	private ResourceResolverUtil(){
		super();
	}
	
	public static ResourceResolver getServerResourceResolver(ResourceResolverFactory 
			resolverFactory, String subservice) throws LoginException{
		Map<String, Object> authenticationInfo = new HashMap<>();
		authenticationInfo.put(ResourceResolverFactory.SUBSERVICE, subservice);
		return resolverFactory.getServiceResourceResolver(authenticationInfo);
	}
	
	public static void closeResourceResolver(ResourceResolver resourceResolver){
		if(resourceResolver != null && resourceResolver.isLive()){
			resourceResolver.close();
		}
	}

}