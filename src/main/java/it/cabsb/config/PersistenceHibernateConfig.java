package it.cabsb.config;

import java.util.Properties;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import com.google.common.base.Preconditions;

//@Order(2)
//@Configuration
//@EnableTransactionManagement
//@ComponentScan({ "it.cabsb.persistence.dao", "it.cabsb.persistence.service" })
public class PersistenceHibernateConfig {

    @Autowired
    private Environment env;

    public PersistenceHibernateConfig() {
        super();
    }

    @Bean
    public AnnotationSessionFactoryBean sessionFactory() {
        final AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] {"it.cabsb.model"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public BasicDataSource dataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.username")));
        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.password")));
        return dataSource;
    }

    @Bean
    public HibernateTransactionManager transactionManager(final SessionFactory sessionFactory) {
        final HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
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
