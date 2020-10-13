package com.group7.banking.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.group7.banking.repository.sql",
        entityManagerFactoryRef = "sqlEntityManagerFactory",
        transactionManagerRef= "sqlTransactionManager")
@EnableTransactionManagement
public class SqlDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.sql")
    public DataSourceProperties sqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.sql.configuration")
    public DataSource sqlDataSource() {
        return sqlDataSourceProperties().initializeDataSourceBuilder()
                .type(BasicDataSource.class).build();
    }
    
    @Bean(name = "sqlEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean sqlEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(sqlDataSource());
        em.setPackagesToScan(new String[]{"com.group7.banking.model.sql"});
        em.setPersistenceUnitName("trueid");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    @Bean(name = "sqlTransactionManager")
    @Primary
    public PlatformTransactionManager sqlTransactionManager(
    		final @Qualifier("sqlEntityManagerFactory") LocalContainerEntityManagerFactoryBean sqlEntityManagerFactory) {
        
    	JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(sqlEntityManagerFactory.getObject());

        return transactionManager;
    }
    
    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
          "hibernate.hbm2ddl.auto", "create-drop");
        hibernateProperties.setProperty(
          "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
 
        return hibernateProperties;
    }
}