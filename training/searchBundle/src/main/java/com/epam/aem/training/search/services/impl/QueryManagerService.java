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

import com.epam.aem.training.search.services.SearchService;

public class QueryManagerService implements SearchService {

	@Override
	public String getServiceName() {
		return this.getClass().getName();
	}

	@Override
	public List<String> getSearchResults(String text, String path,
			ResourceResolver resolver) {
		
		List<String> searchResults = new ArrayList<>(); 
		Session session = resolver.adaptTo(Session.class);
		try {
			QueryManager queryManager = session.getWorkspace().getQueryManager();
			Query query = queryManager.createQuery("SELECT * FROM [nt:base] AS s WHERE CONTAINS(*, '"+ text + "') AND  " +
					"ISDESCENDANTNODE(s,'"+ path +"')", Query.JCR_SQL2);
			QueryResult result = query.execute();
			NodeIterator iterator = result.getNodes();
			while(iterator.hasNext()){
				Node node = iterator.nextNode();
				searchResults.add(node.getPath());
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		return searchResults;
	}

}
