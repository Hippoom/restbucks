package com.restbucks.ordering.persistence;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableAutoConfiguration
@EntityScan(basePackages = {"com.restbucks.ordering.domain"})
@EnableJpaRepositories(basePackages = {"com.restbucks.ordering.persistence.jpa"})
@EnableTransactionManagement
@Configuration
@PropertySource(value = "classpath:datasource.properties")
public class PersistenceConfig {

    @Bean
    @ConfigurationProperties(prefix = "jdbc")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


}
