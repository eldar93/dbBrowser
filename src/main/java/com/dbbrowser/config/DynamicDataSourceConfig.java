package com.dbbrowser.config;

import com.dbbrowser.routing.DynamicMySQLDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DynamicDataSourceConfig {

    @Bean
    public DataSource dynamicDataSource() {
        return new DynamicMySQLDataSource();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dynamicDataSource());
    }

    @Bean(name = "dynamicDSTransactionManager")
    public PlatformTransactionManager dynamicDSTransactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
