package com.pittosporum.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.beans.PropertyVetoException;

/**
 * @author yichen(graffitidef @ gmail.com)
 */

@Configuration
public class SpringJdbcConfig {

    @Bean(name = "appDataSource")
    @Qualifier("appDataSource")
    @Primary
    public ComboPooledDataSource appDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://192.168.7.145:3306/app_store?serverTimezone=Asia/Shanghai");
        dataSource.setUser("mock_user");
        dataSource.setPassword("P@ssw0rd");
        return dataSource;
    }

    @Bean(name = "appJdbcTemplate")
    @Qualifier("appJdbcTemplate")
    @Primary
    JdbcTemplate appJdbcTemplate (@Qualifier("appDataSource") ComboPooledDataSource appDataSource){
       return new JdbcTemplate(appDataSource);
    }

    @Bean
    @Qualifier("appTransactionManager")
    DataSourceTransactionManager oRumTransactionManager(@Qualifier("appDataSource") ComboPooledDataSource appDataSource){
        return new DataSourceTransactionManager(appDataSource);
    }
}
