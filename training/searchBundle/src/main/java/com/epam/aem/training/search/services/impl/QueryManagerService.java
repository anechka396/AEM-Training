package com.epam.aem.training.search.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.aem.training.search.services.SearchService;

public class QueryManagerService implements SearchService {

	private static final QueryManagerService QUERY_MANAGER_SERVICE = new QueryManagerService();
	
	private static final String queryString = "SELECT * FROM [nt:base] AS s WHERE CONTAINS(*, '%s') AND (ISDESCENDANTNODE(s,'%s') or (ISDESCENDANTNODE(s,'%s') AND NAME() LIKE '%%.pdf'))";
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private QueryManagerService() {
		super();
	}
	
	public static SearchService getInstance(){
		return QUERY_MANAGER_SERVICE;
	}
	
	@Override
	public List<String> getSearchResults(String text, String path1,
			String path2, ResourceResolver resolver) {
		
		List<String> searchResults = new ArrayList<>(); 
		Session session = resolver.adaptTo(Session.class);
		try {
			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery(String.format(queryString, text, path1, path2), Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator iterator = result.getNodes();
			while(iterator.hasNext()){
				Node node = iterator.nextNode();
				searchResults.add(node.getPath());
			}
		} catch (RepositoryException e) {
			logger.error(e.getMessage());
		}
		return searchResults;
	}

}
