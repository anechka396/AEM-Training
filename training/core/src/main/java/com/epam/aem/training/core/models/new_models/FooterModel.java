package com.epam.aem.training.core.models.new_models;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables=Resource.class)
public class FooterModel {
	
	private static final String EMPTY_TEXT = "";

	@Inject @Default(values=EMPTY_TEXT)
	String copyrighttext;
	
	@Inject @Default(booleanValues=true)
	Boolean hideBuildInfo;
	
	@Inject @Optional
	List<SocialNetwork> socialNetworks;
	
	@Inject @Optional
	List<Column> columns;
	
	public String getCopyrighttext() {
		return copyrighttext;
	}
	
	public Boolean getHideBuildInfo() {
		return hideBuildInfo;
	}

	public List<SocialNetwork> getSocialNetworks() {
		return socialNetworks;
	}

	public List<Column> getColumns() {
		return columns;
	}
	
	
}
