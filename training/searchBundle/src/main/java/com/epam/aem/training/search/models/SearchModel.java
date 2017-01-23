package com.epam.aem.training.search.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;

@Model(adaptables=Resource.class)
public class SearchModel {
	
	@Inject @Default(values="Empty Text")
	protected String text;
	
	@Inject @Default(values="Empty Path")
	protected String path;
	
	@Inject @Default(values="API not selected")
	protected String searchAPI;

private String message;
	
	@PostConstruct
    protected void init() {
        message = "\tText: " + text + "\n" + 
        		"\tPath: " + path + "\n" + 
        		"\tSearch API: " + searchAPI + "\n";
    }
	
	public String getMessage() {
        return message;
    }
}
