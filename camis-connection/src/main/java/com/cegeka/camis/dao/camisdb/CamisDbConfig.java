package com.cegeka.camis.dao.camisdb;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CamisDbConfig {

    @Bean
    @ConfigurationProperties(prefix="camisdb.datasource")
    public DataSource camisDbDataSource() {
        return DataSourceBuilder.create()
                .type(SQLServerDataSource.class)
                .build();
    }

    @Primary
    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(camisDbDataSource());
    }
}
