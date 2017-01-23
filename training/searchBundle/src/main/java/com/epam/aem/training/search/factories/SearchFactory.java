package com.epam.aem.training.search.factories;

import com.epam.aem.training.search.services.SearchService;
import com.epam.aem.training.search.services.impl.QueryBuilderService;
import com.epam.aem.training.search.services.impl.QueryManagerService;

public class SearchFactory {
private static final SearchFactory SEARCH_FACTORY = new SearchFactory();
	
	private static final String QUERY_MANAGER = "QueryManager";
	private static final String QUERY_BUILDER = "QueryBuilder";
	
	private SearchService queryManagerService = new QueryManagerService();
	private SearchService queryBuilderService = new QueryBuilderService();
	
	private SearchFactory() {
		super();
	}
	
	public static SearchFactory getInstance(){
		return SEARCH_FACTORY;
	}

	public SearchService getSearchService(String searchAPI){
		switch(searchAPI){
			case QUERY_BUILDER:
				return queryBuilderService;
			case QUERY_MANAGER:
				return queryManagerService;
			default: 
				return null;
		}
	}
}
