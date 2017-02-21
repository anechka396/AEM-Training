package com.epam.aem.training.core.models.footer;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables=Resource.class)
public class FooterColumn {

	@Inject @Default(values="")
	String header;
	
	@Inject @Default(booleanValues=false)
	Boolean hideInMobileView;
	
	@Inject @Optional
	List<Item> items;
	
	public String getHeader() {
		return header;
	}
	
	public Boolean getHideInMobileView() {
		return hideInMobileView;
	}
	
	public List<Item> getItems() {
		return items;
	}
}
