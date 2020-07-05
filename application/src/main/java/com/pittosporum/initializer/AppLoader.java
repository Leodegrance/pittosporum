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

import java.util.List;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Slf4j
@Component
public class AppLoader implements InitializingBean {
    @Autowired
    @Qualifier("oRumJdbcTemplate")
    private JdbcTemplate oRumJdbcTemplate;

    public void afterPropertiesSet() throws Exception {
        log.info("AppLoader start........");

        String sql = "SELECT id, profile_name, create_by, create_dt, update_by, update_dt FROM profile";

        List<DataBaseProfile> dataBaseProfiles = oRumJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(DataBaseProfile.class));

        ProfileMapper.initProfileMap(dataBaseProfiles);

        log.info("AppLoader end........");
    }
}
