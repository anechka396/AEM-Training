package com.epam.aem.training.core.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.version.Version;
import javax.jcr.version.VersionManager;

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

import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageModification;

@Component
@Service(EventHandler.class)
@Properties({
	@Property(name=EventConstants.EVENT_TOPIC, value=PageEvent.EVENT_TOPIC)
})
public class ContentModificationHandler implements EventHandler {
	
	private static final String REGEXP = "^/content/training-content/[^/]+";
	private static final String DEFAULT_USER = "admin";
	private static final String CONTENT = "jcr:content";
	private static final String DESCRIPTION = "jcr:description";
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
	
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
				try {
					Session session = getResourceResolver().adaptTo(Session.class);
					Node pageNode = session.getNode(modification.getPath());
					Node contentNode = pageNode.getNode(CONTENT);
					if(contentNode.hasProperty(DESCRIPTION)){
						createVersion(pageNode, session);
					}
				} catch (LoginException | RepositoryException e) {
					LOG.error(e.getMessage());
				}
			}
			
			LOG.debug(modification.toString());
		}
	}
	
	private ResourceResolver getResourceResolver() throws LoginException{
		Map<String, Object> authenticationInfo = new HashMap<>();
		authenticationInfo.put(ResourceResolverFactory.USER, DEFAULT_USER);
		authenticationInfo.put(ResourceResolverFactory.PASSWORD, DEFAULT_USER.toCharArray());
		ResourceResolver resourceResolver = resolverFactory.getResourceResolver(authenticationInfo);
		return resourceResolver;
	}
	
	private void createVersion(Node page, Session session) throws RepositoryException{
		VersionManager versionManager = session.getWorkspace().getVersionManager();
		Version version = versionManager.checkin(page.getPath());
		versionManager.checkout(page.getPath());
		LOG.debug(version.toString());
	}

}
