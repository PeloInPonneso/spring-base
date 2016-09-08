package it.cabsb.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;

@Order(8)
@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = { "it.cabsb.batch.launcher", "it.cabsb.batch.tasklet" })
public class BatchConfig {

	@Autowired
    private Environment env;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	public JobRepository jobRepository() throws Exception {
		Boolean batchJobRepositoryEnabled = env.getProperty("batch.job.repository.enabled", Boolean.class);
		if(batchJobRepositoryEnabled) {
			final JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
			factory.setDataSource(dataSource);
			factory.setTransactionManager(transactionManager);
			factory.setTablePrefix("BATCH_");
			factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
			factory.afterPropertiesSet();
			return  (JobRepository) factory.getObject();
		} else {
			final MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
			factory.setTransactionManager(transactionManager);
			factory.setIsolationLevelForCreate("ISOLATION_DEFAULT");
			factory.afterPropertiesSet();
			return  (JobRepository) factory.getObject();
		}
	}

	@Bean
	public JobLauncher jobLauncher() throws Exception {
		final SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
	
}
