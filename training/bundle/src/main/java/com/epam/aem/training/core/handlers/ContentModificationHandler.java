package com.epam.aem.training.core.handlers;

import java.util.Iterator;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageModification;
import com.day.cq.wcm.api.WCMException;
import com.epam.aem.training.core.utils.ResourceResolverUtil;

@Component
@Service(EventHandler.class)
@Properties({
	@Property(name=EventConstants.EVENT_TOPIC, value=PageEvent.EVENT_TOPIC)
})
public class ContentModificationHandler implements EventHandler {
	
	private static final String REGEXP = "^/content/training-content/[^/]+";
	private static final String EVENT_SERVICE = "eventService";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Reference
	ResourceResolverFactory resolverFactory;

	@Override
	public void handleEvent(Event event) {
		PageEvent pgEvent = PageEvent.fromEvent(event);
		Iterator<PageModification> modifications = pgEvent.getModifications();
		
		while(modifications.hasNext()){
			PageModification modification = modifications.next();
			
			if(modification.getPath().matches(REGEXP) && 
					modification.getType().equals(PageModification.ModificationType.MODIFIED)){
				ResourceResolver resourceResolver = null;
				try {
					 resourceResolver = ResourceResolverUtil.
							getServerResourceResolver(resolverFactory, EVENT_SERVICE);
					PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
					Page page = pageManager.getContainingPage(modification.getPath());
					if(page.getDescription() != null){
						pageManager.createRevision(page);
					}
				} catch (LoginException | WCMException e) {
					logger.error(e.getMessage(), e);
				} finally{
					ResourceResolverUtil.closeResourceResolver(resourceResolver);
				}
			}
		}
	}
}
