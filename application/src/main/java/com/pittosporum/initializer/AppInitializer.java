package com.pittosporum.initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import pittosporum.core.DataBaseProfile;
import pittosporum.core.ProfileMapper;
import pittosporum.utils.JDBCTemplateMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
@Component
public class AppInitializer implements InitializingBean {
    @Autowired
    @Qualifier("oRumJdbcTemplate")
    private JdbcTemplate oRumJdbcTemplate;

    public void afterPropertiesSet() throws Exception {
        log.info("AppLoader start........");

        String sql = "SELECT id, profile_name, create_by, create_dt, update_by, update_dt FROM profile";
        List<DataBaseProfile> dataBaseProfiles = oRumJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DataBaseProfile.class));

        ProfileMapper.initProfileMap(dataBaseProfiles);

        JDBCTemplateMapper.initJDBCTemplateMapper(dataBaseProfiles.stream().map(DataBaseProfile::getProfileName).collect(Collectors.toList()));

        log.info("AppLoader end........");
    }
}
