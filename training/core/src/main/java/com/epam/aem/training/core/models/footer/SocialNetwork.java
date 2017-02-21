package com.epam.aem.training.core.models.footer;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;

@Model(adaptables=Resource.class)
public class SocialNetwork {

	@Inject
	String title;
	
	@Inject
	String url;
	
	@Inject
	String iconclass;

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getIconclass() {
		return iconclass;
	}
}
