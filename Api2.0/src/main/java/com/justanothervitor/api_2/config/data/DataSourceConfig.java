package com.justanothervitor.api_2.config.data;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:2000/moneyflow_prod_database");
        dataSourceBuilder.username("moneyflow_prod_environment");
        dataSourceBuilder.password("239302531jkfdmfaksojvunxklxmcqccujwojqwrk12");
        return dataSourceBuilder.build();
    }
}
