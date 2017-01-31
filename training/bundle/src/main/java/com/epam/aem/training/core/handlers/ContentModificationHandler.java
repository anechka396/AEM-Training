package com.epam.aem.training.core.handlers;

import java.util.Iterator;

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
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageModification;
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
				try {
					Session session = ResourceResolverUtil.getServerResourceResolver(resolverFactory, 
							EVENT_SERVICE).adaptTo(Session.class);
					Node pageNode = session.getNode(modification.getPath());
					Node contentNode = pageNode.getNode(JcrConstants.JCR_CONTENT);
					if(contentNode.hasProperty(JcrConstants.JCR_DESCRIPTION)){
						createVersion(pageNode, session);
					}
				} catch (LoginException | RepositoryException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}
	
	private void createVersion(Node page, Session session) throws RepositoryException{
		VersionManager versionManager = session.getWorkspace().getVersionManager();
		Version version = versionManager.checkpoint(page.getPath());
		logger.debug(version.toString());
	}

}
