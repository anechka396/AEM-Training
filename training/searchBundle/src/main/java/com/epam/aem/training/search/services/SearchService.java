package com.epam.aem.training.search.services;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

public interface SearchService {
	String getServiceName();
	List<String> getSearchResults(String text, String path, ResourceResolver resolver);
}
