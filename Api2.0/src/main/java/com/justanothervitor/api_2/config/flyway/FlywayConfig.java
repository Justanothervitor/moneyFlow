package com.justanothervitor.api_2.config.flyway;

import com.justanothervitor.api_2.Application;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class FlywayConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public Flyway flyway(){
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineVersion("0")
                .validateOnMigrate(true)
                .callbacks(new FlywayCallback())
                .placeholders(Map.of("schema","public","app_name","MoneyFlow"))
                .load();
        return flyway;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void migrateFlyway(){
        Flyway flyway = flyway();
        flyway.migrate();
    }

}
