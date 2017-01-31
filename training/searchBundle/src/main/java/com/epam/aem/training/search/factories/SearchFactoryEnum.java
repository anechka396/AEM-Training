package com.epam.aem.training.search.factories;

import com.epam.aem.training.search.services.SearchService;
import com.epam.aem.training.search.services.impl.QueryBuilderService;
import com.epam.aem.training.search.services.impl.QueryManagerService;

public enum SearchFactoryEnum {
	QUERY_MANAGER(QueryManagerService.getInstance()), 
	QUERY_BUILDER(QueryBuilderService.getInstance());
	
	private SearchService searchService;

	private SearchFactoryEnum(SearchService searchService) {
		this.searchService = searchService;
	}
	
	public SearchService getSearchService(){
		return searchService;
	}
}
