package com.pittosporum.initializer;

import com.pittosporum.utils.CommonLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
@Component
public class AppInitializer implements InitializingBean {
    @Autowired
    @Qualifier("appJdbcTemplate")
    private JdbcTemplate appJdbcTemplate;

    public void afterPropertiesSet() throws Exception {
        log.info("AppLoader start........");

        CommonLoader.copyFolderToDir();

        /*CommonLoader.loadXmlTemplateToMap();

        String sql = XmlSQLMapper.receiveSql("storeCatalog", "searchProfile");

        List<DataBaseProfile> dataBaseProfiles = appJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DataBaseProfile.class));

        ProfileMapper.initProfileMap(dataBaseProfiles);

        JDBCTemplateMapper.initJDBCTemplateMapper(dataBaseProfiles.stream().map(DataBaseProfile::getProfileName).collect(Collectors.toList()));

        JobHandlerMapper.scanClass("application/src/main/java/com/pittosporum/batchjob");
        log.info("AppLoader end........");*/
    }
}
