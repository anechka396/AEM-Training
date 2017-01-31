package com.epam.aem.training.search.services;

import java.util.List;

import org.apache.sling.api.resource.ResourceResolver;

public interface SearchService {
	List<String> getSearchResults(String text, String path1, String path2, ResourceResolver resolver);
}
