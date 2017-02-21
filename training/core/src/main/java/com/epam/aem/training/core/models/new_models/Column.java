package com.epam.aem.training.core.models.new_models;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables=Resource.class)
public class Column {
	
	@Inject @Default(values="")
	String header;
	
	@Inject @Default(booleanValues=false)
	Boolean hideInMobile;
	
	@Inject @Named(".") @Optional
	List<Item> items;
	
	public String getHeader() {
		return header;
	}

	public Boolean getHideInMobile() {
		return hideInMobile;
	}

	public List<Item> getItems() {
		return items;
	}
}
