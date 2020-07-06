package com.pittosporum.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.beans.PropertyVetoException;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Configuration
public class SpringJdbcConfig {

    @Bean(name = "oRumAppDataSource")
    @Qualifier("oRumAppDataSource")
    public ComboPooledDataSource oRumAppDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/orum_app?serverTimezone=Asia/Shanghai");
        dataSource.setUser("root");
        dataSource.setPassword("params$11");
        return dataSource;
    }

    @Bean(name = "oRumJdbcTemplate")
    @Qualifier("oRumJdbcTemplate")
    JdbcTemplate oRumJdbcTemplate (@Qualifier("oRumAppDataSource") ComboPooledDataSource oRumAppDataSource){
       return new JdbcTemplate(oRumAppDataSource);
    }

    @Bean
    @Qualifier("oRumTransactionManager")
    DataSourceTransactionManager oRumTransactionManager(@Qualifier("oRumAppDataSource") ComboPooledDataSource oRumAppDataSource){
        return new DataSourceTransactionManager(oRumAppDataSource);
    }
}
