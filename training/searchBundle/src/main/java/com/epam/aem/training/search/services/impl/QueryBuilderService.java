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
	private static final String TYPE = "type";
	private static final String BASE = "nt:base";
	private static final String GROUP_OR_PATH = "group.p.or";
	private static final String GROUP_1_PATH = "group.1_group.path";
	private static final String GROUP_2_PATH = "group.2_group.path";
	private static final String GROUP_2_NODENAME = "group.2_group.nodename";
	private static final String PDF_FILE = "*.pdf";
	private static final String TRUE = "true";

	@Override
	public List<String> getSearchResults(String text, String path1,
			String path2, ResourceResolver resolver) {
		List<String> searchResults = new ArrayList<>();
		
		try{
			Session session = resolver.adaptTo(Session.class);
			QueryBuilder queryBuilder = resolver.adaptTo(QueryBuilder.class);
		
			Map<String, String> propertyMap = new HashMap<>();
			propertyMap.put(FULLTEXT, text);
			propertyMap.put(TYPE, BASE);
			propertyMap.put(GROUP_OR_PATH, TRUE);
			propertyMap.put(GROUP_1_PATH, path1);
			propertyMap.put(GROUP_2_PATH, path2);
			propertyMap.put(GROUP_2_NODENAME, PDF_FILE);
		
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
