package com.epam.aem.training.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.foundation.Image;

@Model(adaptables=Resource.class)
public class NewsListModel {
	
	@Inject
	String path;
	
	@Inject
	String color;
	
	@Inject @Source("sling-object")
	ResourceResolver resourceResolver;
	
	private String title;
	
	private String description;
	
	private String date;
	
	private String image;
	
	@PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page page = pageManager.getContainingPage(path);
        title = page.getTitle();
        description = page.getDescription();
        date = page.getProperties().get("jcr:created", "");
        Resource resource = page.getContentResource("image");
        if(resource != null){
        	Image img = new Image(resource);
        	image = img.getFileReference();
        }
    }

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getDate() {
		return date;
	}
	
	public String getImage(){
		return image;
	}
	
	public String getColor(){
		return color;
	}

}
