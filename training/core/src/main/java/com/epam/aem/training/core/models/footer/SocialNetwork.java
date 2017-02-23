package com.epam.aem.training.core.models.footer;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;

@Model(adaptables=Resource.class)
public class SocialNetwork {

	private static final String EMPTY_TEXT = "";
	
	@Inject @Default(values=EMPTY_TEXT)
	String title;
	
	@Inject @Default(values=EMPTY_TEXT)
	String url;
	
	@Inject @Default(values=EMPTY_TEXT)
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
