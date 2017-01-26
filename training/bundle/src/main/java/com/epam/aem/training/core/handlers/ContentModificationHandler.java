package com.epam.aem.training.core.handlers;

import java.util.Iterator;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
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
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public void handleEvent(Event event) {
		PageEvent pgEvent = PageEvent.fromEvent(event);
		Iterator<PageModification> modifications = pgEvent.getModifications();
		
		while(modifications.hasNext()){
			PageModification modification = modifications.next();
			if(modification.getPath().matches(REGEXP)){
				LOG.debug(modification.toString());
			}
		}
	}

}
