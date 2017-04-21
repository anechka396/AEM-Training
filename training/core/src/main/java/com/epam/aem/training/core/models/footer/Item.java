package com.epam.aem.training.core.models.footer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables=Resource.class)
public class Item {
	
	@Inject
	String  path;
	
	@Inject @Source("sling-object")
	ResourceResolver resourceResolver;
	
	Page page;
	
	@PostConstruct
    void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        page = pageManager.getContainingPage(path);
    }

	public String getPath() {
		return path;
	}
	
	public String getPageTitle() {
		return page != null ? page.getPageTitle() : null;
	}
	
	public String getNavigationTitle() {
		return page != null ? page.getNavigationTitle() : null;
	}
}
