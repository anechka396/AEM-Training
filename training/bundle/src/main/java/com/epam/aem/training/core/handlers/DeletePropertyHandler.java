package com.epam.aem.training.core.handlers;

import java.util.HashMap;
import java.util.Map;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

@Component
@Service(EventHandler.class)
@Properties({
	@Property(name=EventConstants.EVENT_TOPIC, value=SlingConstants.TOPIC_RESOURCE_CHANGED),
	@Property(name=EventConstants.EVENT_FILTER, value="(&(path=/content/training-content/*)(resourceRemovedAttributes=*))")
})
public class DeletePropertyHandler implements EventHandler{

	private static final String JOB_TOPIC = "save/removal/property/info";
	
	@Reference
	JobManager jobManager;
	
	@Override
	public void handleEvent(Event event) {		
		final Map<String, Object> jobProperties = new HashMap<>();
		jobProperties.put(SlingConstants.PROPERTY_PATH, event.getProperty(SlingConstants.PROPERTY_PATH));
		jobProperties.put(SlingConstants.PROPERTY_REMOVED_ATTRIBUTES, event.getProperty(SlingConstants.PROPERTY_REMOVED_ATTRIBUTES));
		jobManager.addJob(JOB_TOPIC, jobProperties);
	}

}
