package com.example.skty.springboot.configurations.datasource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * 针对primary数据库的jpa实体管理和事务配置
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.example.skty.springboot.repository.db2"
        , entityManagerFactoryRef = "secondEntityManagerFactoryBean",
        transactionManagerRef = "secondTransactionManager")
@EnableTransactionManagement
public class JpaSecondDataSourceConfig {
    @Resource(name = "secondDataSource")
    private DataSource dataSource;

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa.second")
    public JpaProperties secondJpaProperties() {
        return new JpaProperties();
    }

    @Qualifier("secondEntityManagerFactoryBean")
    @Bean
    public LocalContainerEntityManagerFactoryBean secondEntityManagerFactoryBean(@Qualifier("secondJpaProperties") JpaProperties jpaProperties) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        BeanUtils.copyProperties(jpaProperties, vendorAdapter);
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.example.skty.springboot.entity.db2");
        factory.setDataSource(dataSource);
        factory.setPersistenceUnitName("secondPersistenceUnitName");
        return factory;
    }

    @Qualifier("secondTransactionManager")
    @Bean
    public PlatformTransactionManager secondTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }

}
