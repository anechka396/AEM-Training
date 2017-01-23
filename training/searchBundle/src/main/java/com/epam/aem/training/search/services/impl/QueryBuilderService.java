package com.epam.aem.training.search.services.impl;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

import com.epam.aem.training.search.services.SearchService;

public class QueryBuilderService implements SearchService{

	@Override
	public String getServiceName() {
		return this.getClass().getName();
	}

	@Override
	public List<String> getSearchResults(String text, String path,
			ResourceResolver resolver) {
		// TODO Auto-generated method stub
		return null;
	}

}
