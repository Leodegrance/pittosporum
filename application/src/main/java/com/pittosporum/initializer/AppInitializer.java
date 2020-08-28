package com.pittosporum.initializer;

import com.pittosporum.batchjob.JobHandlerMapper;
import com.pittosporum.utils.CommonLoader;
import com.pittosporum.utils.JDBCTemplateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.beans.PropertyVetoException;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
@Component
public class AppInitializer{
    @Autowired
    @Qualifier("appJdbcTemplate")
    private JdbcTemplate appJdbcTemplate;

    @Resource
    private ApplicationContext context;

    @PostConstruct
    public void initMethod() throws Exception {
        log.info("AppLoader start........");

        CommonLoader.copyFolderToDir();

        CommonLoader.loadXmlTemplateToMap();

        JobHandlerMapper.scanClass("com.pittosporum.batchjob");
        log.info("AppLoader end........");
    }

    @PostConstruct
    private void initCustomDataSource() throws PropertyVetoException {
        JDBCTemplateMapper.initJDBCTemplateMapper(context, appJdbcTemplate);
    }
}
