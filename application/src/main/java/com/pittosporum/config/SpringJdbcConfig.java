package com.pittosporum.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Configuration
public class SpringJdbcConfig {

    @Bean(name = "oRumAppDataSource")
    @Qualifier("oRumAppDataSource")
    public DataSource oRumAppDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/orum_app?serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("params$11");
        return dataSource;
    }

    @Bean(name = "oRumJdbcTemplate")
    @Qualifier("oRumJdbcTemplate")
    JdbcTemplate oRumJdbcTemplate (@Qualifier("oRumAppDataSource") DataSource oRumAppDataSource){
       return new JdbcTemplate(oRumAppDataSource);
    }

    @Bean
    @Qualifier("oRumTransactionManager")
    DataSourceTransactionManager oRumTransactionManager(@Qualifier("oRumAppDataSource") DataSource oRumAppDataSource){
        return new DataSourceTransactionManager(oRumAppDataSource);
    }
}
