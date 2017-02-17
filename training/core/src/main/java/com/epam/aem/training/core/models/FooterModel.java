package com.epam.aem.training.core.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.aem.training.core.entity.Column;
import com.epam.aem.training.core.entity.SocialNetwork;
import com.fasterxml.jackson.databind.ObjectMapper;


@Model(adaptables=Resource.class)
public class FooterModel {

	private static final String EMPTY_TEXT = "";
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Inject @Default(values=EMPTY_TEXT)
	String copyrighttext;
	
	@Inject @Default(values="#EFB734")
	String colorCode;
	
	@Inject @Default(booleanValues=true)
	Boolean hideBuildInfo;
	
	@Inject @Optional
	List<String> socialNetworks;
	
	@Inject @Optional
	List<String> columns;
	
	@Inject 
	@Named("fileReference") 
	@Default(values=EMPTY_TEXT)
	private String backgroundImage;

	public String getCopyrighttext() {
		return copyrighttext;
	}

	public String getColorCode() {
		return colorCode;
	}

	public Boolean getHideBuildInfo() {
		return hideBuildInfo;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public List<SocialNetwork> getNetworks() {
		List<SocialNetwork> networks = new ArrayList<>();
		if(socialNetworks != null){
			ObjectMapper objectMapper = new ObjectMapper();
			for(String socialNetwork : socialNetworks){
				try {
					SocialNetwork network = objectMapper.readValue(socialNetwork, SocialNetwork.class);
					networks.add(network);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return networks;
	}
	
	public List<Column> getCols() {
		List<Column> cols = new ArrayList<>();
		if(columns != null){
			ObjectMapper objectMapper = new ObjectMapper();
			for(String column : columns){
				try {
					Column col = objectMapper.readValue(column, Column.class);
					cols.add(col);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return cols;
	}
}
