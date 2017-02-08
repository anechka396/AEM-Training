package com.epam.aem.training.workflow.processes;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.core.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.PayloadMap;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component
@Service(WorkflowProcess.class)
@Property(name="process.label", value="EPAM Training WF Process")
public class MovePageWorkflow implements WorkflowProcess{

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private static final String PATH_TO_MOVE = "pathToMove";
	private static final String DASH = "-";
	
	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {
		WorkflowData workflowData = workItem.getWorkflowData();
		if(workflowData.getPayloadType().equals(PayloadMap.TYPE_JCR_PATH)){
			String currentPath = workflowData.getPayload().toString();
			Session jcrSession = workflowSession.adaptTo(Session.class);
			try {
				Node pageNode = jcrSession.getNode(currentPath);
				if(pageNode != null){
					Node contentNode = pageNode.getNode(JcrConstants.JCR_CONTENT);
					if(contentNode != null && contentNode.hasProperty(PATH_TO_MOVE)){
						String pathToMove = contentNode.getProperty(PATH_TO_MOVE).getString();
						String newPath = pathToMove + FileSystem.SEPARATOR + pageNode.getName();
						if(jcrSession.nodeExists(pathToMove) && !newPath.equals(currentPath)){
							if(jcrSession.nodeExists(newPath)){
								newPath = renameNode(newPath, jcrSession);
							}
							jcrSession.move(currentPath, newPath);
							jcrSession.save();
						}
					}
				}
			} catch (RepositoryException e) {
				logger.error(e.getMessage(), e);
			}
			
		}
	}
	
	private String renameNode(String nodeName, Session session) throws RepositoryException{
		int count = 1;
		nodeName += DASH;
		
		while(session.nodeExists(nodeName + count)){
			count++;
		}
		
		return nodeName + count;
	}

}
