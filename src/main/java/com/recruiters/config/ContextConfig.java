package com.recruiters.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource({"classpath:jdbc.properties", "classpath:hibernate.properties"})
@EnableJpaRepositories("com.recruiters")
public class ContextConfig {

    @Inject
    private org.springframework.core.env.Environment environment;

    @Bean
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(environment.getProperty("recruiter-jdbc.driverClassName"));
        ds.setUrl(environment.getProperty("recruiter-jdbc.url"));
        ds.setUsername(environment.getProperty("recruiter-jdbc.username"));
        ds.setPassword(environment.getProperty("recruiter-jdbc.password"));
        ds.setMaxActive(Integer.valueOf(environment.getProperty("recruiter-jdbc.max-active")));
        ds.setMaxWait(Integer.valueOf(environment.getProperty("recruiter-jdbc.max-wait")));
        ds.setPoolPreparedStatements(true);
        ds.setDefaultAutoCommit(true);
        ds.setValidationQuery("SELECT 1");
        ds.setTestOnBorrow(true);

        return ds;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.recruiter");
        factory.setDataSource(dataSource());
        factory.setJpaDialect(new HibernateJpaDialect());
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

}
