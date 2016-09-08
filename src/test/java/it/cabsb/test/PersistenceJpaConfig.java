package it.cabsb.test;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.base.Preconditions;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:app.properties" })
public class PersistenceJpaConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
    	final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
    	emf.setPersistenceUnitName("jpa-persistence");
    	emf.setDataSource(dataSource());
    	emf.setPackagesToScan(new String[]{"it.cabsb.model"});
    	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    	emf.setJpaVendorAdapter(vendorAdapter);
    	emf.setJpaProperties(hibernateProperties());
    	return emf;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.username")));
        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.password")));
        return dataSource;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf){
    	final JpaTransactionManager transactionManager = new JpaTransactionManager();
    	transactionManager.setEntityManagerFactory(emf);
    	return transactionManager;
    }
    
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
    	final PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor = new PersistenceExceptionTranslationPostProcessor();
    	return persistenceExceptionTranslationPostProcessor;
    }

    final Properties hibernateProperties() {
        return new Properties() {
			private static final long serialVersionUID = -243029401562335321L;
			{
				setProperty("javax.persistence.validation.mode", "NONE");
                setProperty("hibernate.hbm2ddl.auto", Preconditions.checkNotNull(env.getProperty("hibernate.hbm2ddl.auto")));
                setProperty("hibernate.dialect", Preconditions.checkNotNull(env.getProperty("hibernate.dialect")));
                setProperty("hibernate.show_sql", Preconditions.checkNotNull(env.getProperty("hibernate.show_sql")));
                setProperty("hibernate.hbm2ddl.import_files", Preconditions.checkNotNull(env.getProperty("hibernate.hbm2ddl.import_files")));
                setProperty("hibernate.connection.useUnicode", Preconditions.checkNotNull(env.getProperty("hibernate.connection.useUnicode")));
                setProperty("hibernate.connection.characterEncoding", Preconditions.checkNotNull(env.getProperty("hibernate.connection.characterEncoding")));
                setProperty("hibernate.connection.autoReconnect", Preconditions.checkNotNull(env.getProperty("hibernate.connection.autoReconnect")));
                setProperty("hibernate.cache.use_second_level_cache", Preconditions.checkNotNull(env.getProperty("hibernate.cache.use_second_level_cache")));
                setProperty("hibernate.search.default.indexBase", Preconditions.checkNotNull(env.getProperty("hibernate.search.default.indexBase")));
            }
        };
    }
    
}
