package com.pittosporum.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pittosporum.entity.Properties;
import com.pittosporum.exception.BaseRunException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import java.beans.PropertyVetoException;
import java.util.ArrayList;
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

        List<Properties> properties = appJdbcTemplate.query("select application as applicationname, profile, label, `key`, " +
                "value from properties where label = 'datasource'", new BeanPropertyRowMapper<>(Properties.class));

        if (CommonUtil.isEmpty(properties)){
            return;
        }

        Map<String, List<Properties>> listMap =  new HashMap<>();
        for (Properties ele : properties){
            String key = ele.getApplicationName();
            List<Properties> list = listMap.get(key);
            if (list == null || list.isEmpty()){
                list = new ArrayList();
                list.add(ele);
                listMap.put(key, list);
            }else {
                list.add(ele);
            }
        }

        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        for (Map.Entry<String, List<Properties>> entry : listMap.entrySet()) {
            String sourceName = entry.getKey();
            List<Properties> list = entry.getValue();
            ComboPooledDataSource dataSource = new ComboPooledDataSource();
            for (Properties ele : list) {
                String key = ele.getKey();
                String val = ele.getValue();
                if ("driverClass".equals(key)) {
                    dataSource.setDriverClass(val);
                } else if ("jdbcUrl".equals(key)) {
                    dataSource.setJdbcUrl(val);
                } else if ("password".equals(key)) {
                    dataSource.setPassword(val);
                } else if ("user".equals(key)) {
                    dataSource.setUser(val);
                }
            }

            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.setDataSource(dataSource);
            String datasourceName = sourceName + DATASOURCE_BEAN_NAME_SUFFIX;
            String jdbcBeanName = sourceName + JDBC_BEAN_NAME_SUFFIX;
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
