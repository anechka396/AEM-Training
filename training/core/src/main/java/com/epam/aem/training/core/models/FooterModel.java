package com.epam.aem.training.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Source;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.epam.aem.training.core.entity.Column;
import com.epam.aem.training.core.entity.Item;
import com.epam.aem.training.core.entity.SocialNetwork;


@Model(adaptables=Resource.class)
public class FooterModel {

	private static final String EMPTY_TEXT = "";
	private static final String PATH = "path";
	private static final String URL ="url";
	private static final String ICONCLASS = "iconclass";
	private static final String TITLE = "title";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Inject @Default(values=EMPTY_TEXT)
	String copyrighttext;
	
	@Inject @Default(values="#EFB734")
	String colorCode;
	
	@Inject @Default(booleanValues=true)
	Boolean hideBuildInfo;
	
	@Inject @Optional
	List<Resource> socialNetworks;
	
	@Inject @Source("sling-object")
	ResourceResolver resourceResolver;
	
	List<List<Resource>>  columns;
	
	@Inject 
	@Named("fileReference") 
	@Default(values=EMPTY_TEXT)
	private String backgroundImage;
	
	@Self
	Resource self;
	
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
			for(Resource resource : socialNetworks){
				 ValueMap props = resource.adaptTo(ValueMap.class);
				 SocialNetwork socialNetwork = new SocialNetwork();
				 socialNetwork.setTitle(props.get(TITLE, EMPTY_TEXT));
				 socialNetwork.setUrl(props.get(URL, EMPTY_TEXT));
				 socialNetwork.setIconclass(props.get(ICONCLASS, EMPTY_TEXT));
				 networks.add(socialNetwork);
			}
		}
		return networks;
	}
	
	public List<Column> getCols() {
		List<Column> cols = new ArrayList<>();
		ValueMap selfProps = self.adaptTo(ValueMap.class);
		for(int i =1; i <= 5; i++){
			Resource column = self.getChild("column"+i);
			Column col = new Column();
			if(column != null){
				List<Item> items = new ArrayList<>();
				for(Resource resource : column.getChildren()){
					ValueMap props = resource.adaptTo(ValueMap.class);
					PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
					Page page = pageManager.getContainingPage(props.get(PATH, EMPTY_TEXT));
					items.add(new Item(page));
				}
				col.setItems(items);
			}
			col.setHeader(selfProps.get("column"+i+"Header", EMPTY_TEXT));
			col.setHideInMobileView(Boolean.valueOf(selfProps.get("column"+i+"HideInMobile", "false")));
			cols.add(col);
		}
		return cols;
	}
}
