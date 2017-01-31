package com.epam.aem.training.search.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;

import com.epam.aem.training.search.factories.SearchFactoryEnum;
import com.epam.aem.training.search.services.SearchService;

@Model(adaptables=Resource.class)
public class SearchModel {
	
	@Inject @Default(values="industry leadership")
	private String text;
	
	@Inject @Default(values="/content/geometrixx")
	private String path1;
	
	@Inject @Default(values="/content/dam")
	private String path2;
	
	@Inject @Default(values="QUERY_MANAGER")
	private String searchAPI;
	
	@Inject @Source("sling-object")
	ResourceResolver resourceResolver;
	
	public String getText() {
		return text;
	}

	public String getPath1() {
		return path1;
	}

	public String getPath2() {
		return path2;
	}

	public String getSearchAPI() {
		return searchAPI;
	}

	public List<String> getSearchResults() {
		List<String> searchResults = new ArrayList<>();
		SearchService searchService = SearchFactoryEnum.valueOf(searchAPI).getSearchService();
        if(searchService != null){
        	searchResults = searchService.getSearchResults(text, path1, path2, resourceResolver);
        }
		return searchResults;
	}
}
