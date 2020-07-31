package com.pittosporum.initializer;

import com.pittosporum.batchjob.JobHandlerMapper;
import com.pittosporum.entity.DataBaseProfile;
import com.pittosporum.util.ProfileMapper;
import com.pittosporum.utils.CommonLoader;
import com.pittosporum.utils.JDBCTemplateMapper;
import com.pittosporum.xmlsql.XmlSQLMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
@Component
public class AppInitializer implements InitializingBean {
    @Autowired
    @Qualifier("appJdbcTemplate")
    private JdbcTemplate appJdbcTemplate;

    @Autowired(required = false)
    private Scheduler scheduler;

    public void afterPropertiesSet() throws Exception {
        log.info("AppLoader start........");

        CommonLoader.loadXmlTemplateToMap();

        String sql = XmlSQLMapper.receiveSql("storeCatalog", "searchProfile");

        List<DataBaseProfile> dataBaseProfiles = appJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DataBaseProfile.class));

        ProfileMapper.initProfileMap(dataBaseProfiles);

        JDBCTemplateMapper.initJDBCTemplateMapper(dataBaseProfiles.stream().map(DataBaseProfile::getProfileName).collect(Collectors.toList()));

        JobHandlerMapper.scanClass("application/src/main/java/com/pittosporum/batchjob");
        log.info("AppLoader end........");
    }
}
