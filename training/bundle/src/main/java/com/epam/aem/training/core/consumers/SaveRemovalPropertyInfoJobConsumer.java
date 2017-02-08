package com.epam.aem.training.core.consumers;

import java.util.Date;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.jcr.vault.util.Text;
import com.epam.aem.training.core.utils.ResourceResolverUtil;

@Component
@Service(JobConsumer.class)
@Properties({
	@Property(name=JobConsumer.PROPERTY_TOPICS, value="save/removal/property/info")
})
public class SaveRemovalPropertyInfoJobConsumer implements JobConsumer {

	private static final String BASE_PATH = "/var/log/removedProperties";
	private static final String PROPERTY_NAME = "propertyName";
	private static final String PROPERTY_PATH = "propertyPath";
	private static final String EVENT_SERVICE = "eventService";
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Reference
	ResourceResolverFactory resolverFactory;

	@Override
	public JobResult process(final Job job) {
		String path = job.getProperty(SlingConstants.PROPERTY_PATH, String.class);
		String [] attributes = job.getProperty(SlingConstants.PROPERTY_REMOVED_ATTRIBUTES, String[].class);
		ResourceResolver resourceResolver = null;
		
		try {
			resourceResolver = ResourceResolverUtil.getServerResourceResolver(resolverFactory, EVENT_SERVICE);
			Session session = resourceResolver.adaptTo(Session.class);
			
			Node baseNode = JcrUtil.createPath(BASE_PATH, JcrResourceConstants.NT_SLING_FOLDER, session);
			
			for(String attribute : attributes){
				processAttribute(baseNode, attribute, path, session);
			}
			
			session.save();
		} catch(LoginException e){
			logger.error(e.getStackTrace().toString());
			return JobResult.CANCEL;
		} catch (RepositoryException e) {
			logger.error(e.getStackTrace().toString());
			return JobResult.FAILED;
		} finally {
			ResourceResolverUtil.closeResourceResolver(resourceResolver);
		}
		
		return JobResult.OK;
	}
	
	private void processAttribute(Node baseNode, String name, String path, Session session) throws RepositoryException{
		Date currentDate = new Date();
		String nodeName = Text.escapeIllegalJcrChars(currentDate.toString());
		
		Node tmpNode = JcrUtil.createUniqueNode(baseNode, nodeName, JcrConstants.NT_UNSTRUCTURED, session);
		
		tmpNode.setProperty(PROPERTY_NAME, name);
		tmpNode.setProperty(PROPERTY_PATH, path);
	}
}
