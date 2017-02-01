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
import org.apache.jackrabbit.core.fs.FileSystem;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.apache.sling.jcr.resource.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;
import com.epam.aem.training.core.utils.ResourceResolverUtil;

@Component
@Service(JobConsumer.class)
@Properties({
	@Property(name=JobConsumer.PROPERTY_TOPICS, value="save/removal/property/info")
})
public class SaveRemovalPropertyInfoJobConsumer implements JobConsumer {

	private static final String BASE_PATH = "var/log/removedProperties";
	private static final String PROPERTY_NAME = "propertyName";
	private static final String PROPERTY_PATH = "propertyPath";
	private static final String EVENT_SERVICE = "eventService";
	private static final String INCORRECT_SYMBOL_REGEXP = "[\\W]";
	private static final String DASH = "-";
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());
	
	@Reference
	ResourceResolverFactory resolverFactory;

	@Override
	public JobResult process(final Job job) {
		String path = job.getProperty(SlingConstants.PROPERTY_PATH, String.class);
		String [] attributes = job.getProperty(SlingConstants.PROPERTY_REMOVED_ATTRIBUTES, String[].class);
		
		try {
			Session session = ResourceResolverUtil.getServerResourceResolver(resolverFactory, 
					EVENT_SERVICE).adaptTo(Session.class);
			Node baseNode = getBaseNode(session);
			
			if(baseNode == null){
				return JobResult.FAILED;
			}
			
			for(String attribute : attributes){
				if(!processAttribute(baseNode, attribute, path)){
					return JobResult.FAILED;
				}
			}
			
			session.save();
		} catch (LoginException | RepositoryException e) {
			LOG.error(e.getMessage());
			return JobResult.FAILED;
		}
		
		return JobResult.OK;
	}
	
	private Node getBaseNode(Session session) throws RepositoryException{
		Node tmpNode = session.getRootNode();
		String []  pathParts = BASE_PATH.split(FileSystem.SEPARATOR);

		for(String pathPart : pathParts){
			if(tmpNode == null){
				return null;	
			}
			
			if(tmpNode.hasNode(pathPart)){
				tmpNode = tmpNode.getNode(pathPart);
			} else {
				tmpNode = tmpNode.addNode(pathPart, JcrResourceConstants.NT_SLING_FOLDER);
			}
		}
		return tmpNode;
	}
	
	private boolean processAttribute(Node baseNode, String name, String path) throws RepositoryException{
		Date currentDate = new Date();
		String nodeName = currentDate.toString().toLowerCase().replaceAll(INCORRECT_SYMBOL_REGEXP, DASH);
		
		Node tmpNode = baseNode.addNode(nodeName, JcrConstants.NT_UNSTRUCTURED);
		
		if(tmpNode == null)
			return false;
		
		tmpNode.setProperty(PROPERTY_NAME, name);
		tmpNode.setProperty(PROPERTY_PATH, path);
		return true;
	}
}
