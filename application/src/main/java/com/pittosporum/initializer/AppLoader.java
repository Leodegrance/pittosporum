package com.pittosporum.initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import pittosporum.core.DataBaseProfile;
import pittosporum.core.ProfileMapper;
import pittosporum.utils.DataSourceManager;
import pittosporum.utils.JDBCTemplateHelper;

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
@Component
public class AppLoader implements InitializingBean {
    public void afterPropertiesSet() throws Exception {
        log.info("AppLoader start........");
        DriverManagerDataSource dataSource = DataSourceManager.getDriverManagerDataSourceByName("oRumDataSource");
        JdbcTemplate jdbcTemplate = JDBCTemplateHelper.getJdbcTemplateByDataSource(dataSource);

        String sql = "SELECT id, profile_name, create_by, create_dt, update_by, update_dt FROM orum_app.profile";
        List<DataBaseProfile> dataBaseProfiles = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DataBaseProfile.class));

        ProfileMapper.initProfileMap(dataBaseProfiles);

        log.info("AppLoader end........");
    }
}
