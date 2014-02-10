package com.recruiters.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;


/**
 * MyBatis Configuration
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
@EnableJpaRepositories("com.recruiters")
@ComponentScan("com.recruiters")
@MapperScan("com.recruiters.repository.mapper")
@EnableTransactionManagement
public class MyBatisConfig {

    @Inject
    private Environment environment;

    /**
     * DataSource Bean setup
     * @return DataSource
     */
    @Bean
    public javax.sql.DataSource dataSource() {
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

    /**
     * SQL Session Factory setup
     * @return SQL Session Factory
     * @throws Exception if fails
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource());

        return sqlSessionFactory.getObject();
    }

    /**
     * Transaction Manager Setup
     * @return transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {

        return new DataSourceTransactionManager(dataSource());
    }
}
