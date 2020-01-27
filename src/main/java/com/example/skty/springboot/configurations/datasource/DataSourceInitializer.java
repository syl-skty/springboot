package com.example.skty.springboot.configurations.datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 用于初始化当前的数据源，同时执行配置好的数据库脚本
 *
 * @author skty
 */
class DataSourceInitializer {

    private static final Log logger = LogFactory.getLog(DataSourceInitializer.class);


    /**
     * 将jpaProperties对象中的配置填充到创建jpa实体配置中
     *
     * @return
     */
    public static void obtainsJpaVendorAdapter(JpaProperties jpaProperties,
                                               AbstractJpaVendorAdapter adapter) {
        if (adapter != null && jpaProperties != null) {
            adapter.setDatabase(jpaProperties.getDatabase());
            adapter.setDatabasePlatform(jpaProperties.getDatabasePlatform());
            adapter.setGenerateDdl(jpaProperties.isGenerateDdl());
            adapter.setShowSql(jpaProperties.isShowSql());
            BeanUtils.copyProperties(jpaProperties, adapter);
        }
    }

    /**
     * 使用指定的配置文件，初始化当前的数据源，并执行配置中的sql脚本
     *
     * @param properties
     * @return
     */
    static DataSource initializerDataSource(DataSourceProperties properties) {
        DataSource dataSource = properties.initializeDataSourceBuilder().build();
        //执行配置的sql脚本
        if (dataSource != null) {
            //先执行DDL语句，再执行DML语句
            createSchema(dataSource, properties);
            initSchema(dataSource, properties);
        }
        return dataSource;
    }

    /**
     * 执行DDL语句，数据定义语句，建表
     *
     * @param dataSource
     * @param properties
     * @return
     */
    private static boolean createSchema(DataSource dataSource, DataSourceProperties properties) {
        List<Resource> scripts = getScripts("要执行的ddl,sql脚本未配置", properties.getSchema(), "schema",
                properties.getPlatform());
        if (!scripts.isEmpty()) {
            if (!isEnabled(dataSource, properties)) {
                logger.debug("Initialization disabled (not running DDL scripts)");
                return false;
            }
            String username = properties.getSchemaUsername();
            String password = properties.getSchemaPassword();
            runScripts(scripts, username, password, dataSource, properties);
        }
        return !scripts.isEmpty();
    }


    /**
     * 执行DML语句（增删改查）
     *
     * @see DataSourceProperties#getData()
     */
    private static void initSchema(DataSource dataSource, DataSourceProperties properties) {
        List<Resource> scripts = getScripts("要执行的dml,sql脚本未配置", properties.getData(), "data", properties.getPlatform());
        if (!scripts.isEmpty()) {
            if (!isEnabled(dataSource, properties)) {
                logger.debug("Initialization disabled (not running data scripts)");
                return;
            }
            String username = properties.getDataUsername();
            String password = properties.getDataPassword();
            runScripts(scripts, username, password, dataSource, properties);
        }
    }

    private static List<Resource> getScripts(String propertyName, List<String> resources, String fallback, String platform) {
        if (resources != null) {
            return getResources(propertyName, resources, true);
        }
        List<String> fallbackResources = new ArrayList<>();
        fallbackResources.add("classpath*:" + fallback + "-" + platform + ".sql");
        fallbackResources.add("classpath*:" + fallback + ".sql");
        return getResources(propertyName, fallbackResources, false);
    }

    private static List<Resource> getResources(String propertyName, List<String> locations, boolean validate) {
        List<Resource> resources = new ArrayList<>();
        for (String location : locations) {
            for (Resource resource : doGetResources(location)) {
                if (resource.exists()) {
                    resources.add(resource);
                } else if (validate) {
                    throw new InvalidConfigurationPropertyValueException(propertyName, resource,
                            "The specified resource does not exist.");
                }
            }
        }
        return resources;
    }

    private static Resource[] doGetResources(String location) {
        try {
            SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(new DefaultResourceLoader(),
                    Collections.singletonList(location));
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (Exception ex) {
            throw new IllegalStateException("Unable to load resources from " + location, ex);
        }
    }

    private static boolean isEnabled(DataSource dataSource, DataSourceProperties properties) {
        DataSourceInitializationMode mode = properties.getInitializationMode();
        if (mode == DataSourceInitializationMode.NEVER) {
            return false;
        }
        if (mode == DataSourceInitializationMode.EMBEDDED && !isEmbedded(dataSource)) {
            return false;
        }
        return true;
    }

    /**
     * 是否是嵌入式数据源
     *
     * @param dataSource
     * @return
     */
    private static boolean isEmbedded(DataSource dataSource) {
        try {
            return EmbeddedDatabaseConnection.isEmbedded(dataSource);
        } catch (Exception ex) {
            logger.debug("Could not determine if datasource is embedded", ex);
            return false;
        }
    }


    private static void runScripts(List<Resource> resources, String userName, String passWord, DataSource dataSource,
                                   DataSourceProperties properties) {
        if (resources.isEmpty()) {
            return;
        }
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(properties.isContinueOnError());
        populator.setSeparator(properties.getSeparator());
        if (properties.getSqlScriptEncoding() != null) {
            populator.setSqlScriptEncoding(properties.getSqlScriptEncoding().name());
        }
        for (Resource resource : resources) {
            populator.addScript(resource);
        }
        if (StringUtils.hasText(userName) && StringUtils.hasText(passWord)) {
            dataSource = DataSourceBuilder.create(properties.getClassLoader())
                    .driverClassName(properties.determineDriverClassName()).url(properties.determineUrl())
                    .username(userName).password(passWord).build();
        }
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

}
