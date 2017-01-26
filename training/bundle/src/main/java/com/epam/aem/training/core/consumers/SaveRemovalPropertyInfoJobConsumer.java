package com.epam.aem.training.core.consumers;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Service(JobConsumer.class)
@Properties({
	@Property(name=JobConsumer.PROPERTY_TOPICS, value="save/removal/property/info")
})
public class SaveRemovalPropertyInfoJobConsumer implements JobConsumer {
	
	private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public JobResult process(final Job job) {
		LOG.debug(job.getTopic() + " " + job.getProperty("path"));
		return JobResult.OK;
	}

}
