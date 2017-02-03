package com.epam.aem.training.workflow.processes;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

@Component
@Service(WorkflowProcess.class)
@Property(name="process.label", value="EPAM Training WF Process")
public class MovePageWorkflow implements WorkflowProcess{

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Override
	public void execute(WorkItem workItem, WorkflowSession wfSession, MetaDataMap metaDataMap)
			throws WorkflowException {
		logger.debug("Training process executed: called from Java code");
		logger.debug("Payload: " + workItem.getWorkflowData().getPayload());
	}

}
