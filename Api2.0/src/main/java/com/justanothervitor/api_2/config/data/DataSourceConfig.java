package com.justanothervitor.api_2.config.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
 @Value("${spring.datasource.url}")
 private String url;
 @Value("${spring.flyway.user}")
 private String username;
 @Value("${spring.flyway.password}")
 private String password;

 @Bean
 public DataSource getDataSource() {
     return DataSourceBuilder.create().driverClassName("org.postgresql.Driver").url(url).username(username).password(password).build();
 }

}
