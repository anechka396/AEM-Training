package com.epam.aem.training.search.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.epam.aem.training.search.services.SearchService;

public class QueryBuilderService implements SearchService{
	
	private static final String FULLTEXT = "fulltext";
	private static final String PATH = "path";
	private static final String TYPE = "type";
	private static final String BASE = "nt:base";

	@Override
	public List<String> getSearchResults(String text, String path,
			ResourceResolver resolver) {
		List<String> searchResults = new ArrayList<>();
		
		try{
			Session session = resolver.adaptTo(Session.class);
			QueryBuilder queryBuilder = resolver.adaptTo(QueryBuilder.class);
		
			Map<String, String> propertyMap = new HashMap<>();
			propertyMap.put(FULLTEXT, text);
			propertyMap.put(PATH, path);
			propertyMap.put(TYPE, BASE);
		
			Query query = queryBuilder.createQuery(PredicateGroup.create(propertyMap), session);
			SearchResult result = query.getResult();
		
			for(Hit hit  : result.getHits()){
				searchResults.add(hit.getPath());
			}
		} catch(RepositoryException e){
			e.printStackTrace();
		}
		
		return searchResults;
	}

}
