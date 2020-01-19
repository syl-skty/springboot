package com.example.skty.springboot.configurations.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据源配置，可以在这里配置多个数据源，以及配置一些跟数据源相关的配置bean（如启动时自动执行sql）
 *
 * @author skty
 */
@Configuration
public class DataSourceConfigurer {

    /**
     * 使用@ConfigurationProperties注解，声明前缀，这样就可以在使用的时候获取配置文件中的不同连接信息
     * 该注解不但能使用在类上（将配置文件中的数据绑定到类中的属性上），还能使用在方法上（将配置文件中的数据传递到方法执行中）
     *
     * @return
     */

    @Primary//设置为基础的bean,当有多个数据源bean时，没有指定名称，默认使用这个
    @Bean("primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("secondDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }
}
