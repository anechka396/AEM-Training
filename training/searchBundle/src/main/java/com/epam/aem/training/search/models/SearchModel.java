package com.epam.aem.training.search.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Source;

import com.epam.aem.training.search.factories.SearchFactory;
import com.epam.aem.training.search.services.SearchService;

@Model(adaptables=Resource.class)
public class SearchModel {
	
	@Inject @Default(values="Empty Text")
	protected String text;
	
	@Inject @Default(values="Empty Path")
	protected String path1;
	
	@Inject @Default(values="Empty Path")
	protected String path2;
	
	@Inject @Default(values="API not selected")
	protected String searchAPI;
	
	@Inject @Source("sling-object")
	ResourceResolver resourceResolver;

	private List<String> searchResults = new ArrayList<>();

	public List<String> getSearchResults() {
		SearchService searchService = SearchFactory.getInstance().getSearchService(searchAPI);
        if(searchService != null){
        	searchResults = searchService.getSearchResults(text, path1, path2, resourceResolver);
        }
		return searchResults;
	}
}
