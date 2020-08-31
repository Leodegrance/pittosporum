package com.pittosporum.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pittosporum.exception.BaseRunException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
public final class JDBCTemplateMapper {
    private JDBCTemplateMapper(){}

    private static final String JDBC_BEAN_NAME_SUFFIX = "JdbcTemplate";
    private static final String DATASOURCE_BEAN_NAME_SUFFIX = "DataSource";

    private static Map<String, JdbcTemplate> jdbcTemplateMap = new HashMap<>();

    public static void initJDBCTemplateMapper(ApplicationContext context, JdbcTemplate appJdbcTemplate) throws PropertyVetoException {
        if (context == null || appJdbcTemplate == null){
            return;
        }

        List<Map<String, Object>> queryForList = appJdbcTemplate.queryForList("SELECT application, " +
                " MAX( CASE `key` WHEN 'driverClass' THEN value END) driver_class, MAX( CASE `key` WHEN 'jdbcUrl' THEN value END ) jdbc_url, MAX( CASE `key` WHEN 'user' THEN value END ) `user`, MAX( CASE `key` WHEN 'password' THEN value END ) `password` " +
                " FROM properties where label = 'datasource' GROUP by application;");

        if (CommonUtil.isEmpty(queryForList)){
            return;
        }

        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();

        for (Map<String, Object> map : queryForList){
            String application = (String) map.get("application");
            String driverClass = (String) map.get("driver_class");
            String jdbcUrl = (String) map.get("jdbc_url");
            String user = (String) map.get("user");
            String password = (String) map.get("password");

            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass(driverClass);
            dataSource.setJdbcUrl(jdbcUrl);
            dataSource.setUser(user);
            dataSource.setPassword(password);

            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.setDataSource(dataSource);
            String datasourceName = application + DATASOURCE_BEAN_NAME_SUFFIX;
            String jdbcBeanName = application + JDBC_BEAN_NAME_SUFFIX;
            defaultListableBeanFactory.registerSingleton(datasourceName, dataSource);
            defaultListableBeanFactory.registerSingleton(jdbcBeanName, jdbcTemplate);
            jdbcTemplateMap.put(jdbcBeanName, jdbcTemplate);
        }
    }

    public static JdbcTemplate getJdbcTemplateByName (String name) throws BaseRunException{
        if (StringUtils.isEmpty(name)){
            throw new BaseRunException("can not find jdbc template by name", name);
        }

        return jdbcTemplateMap.get(name + JDBC_BEAN_NAME_SUFFIX);
    }
}
