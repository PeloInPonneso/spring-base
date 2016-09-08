package it.cabsb.config;

import it.cabsb.batch.tasklet.PurgeUnconfirmedUsers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(9)
@Configuration
public class BatchJobConfig {
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private JobBuilderFactory jobBuilders;
 
	@Autowired
	private StepBuilderFactory stepBuilders;
	
	@Autowired
	private PurgeUnconfirmedUsers purgeUnconfirmedUsers;
	
	@Bean(name="jobPurgeUnconfirmedUsers")
	public Job createJobPurgeUnconfirmedUsers() throws Exception {
		return jobBuilders.get("jobPurgeUnconfirmedUsers").repository(jobRepository).start(createJobPurgeUnconfirmedUsersUniqueStep()).build();
	}
	
	@Bean 
	public Step createJobPurgeUnconfirmedUsersUniqueStep() {
		return stepBuilders.get("jobPurgeUnconfirmedUsersUniqueStep").repository(jobRepository).tasklet(purgeUnconfirmedUsers).build();
	}
}
