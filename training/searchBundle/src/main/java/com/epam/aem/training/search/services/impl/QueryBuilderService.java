package com.epam.aem.training.search.services.impl;

import com.epam.aem.training.search.services.SearchService;

public class QueryBuilderService implements SearchService{

	@Override
	public String getServiceName() {
		return this.getClass().getName();
	}

}
