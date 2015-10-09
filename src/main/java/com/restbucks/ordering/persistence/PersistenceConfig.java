package com.restbucks.ordering.persistence;

import lombok.Data;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

import static java.lang.String.format;

@EnableAutoConfiguration
@EntityScan(basePackages = {"com.restbucks.ordering.domain"})
@EnableJpaRepositories(basePackages = {"com.restbucks.ordering.persistence.jpa"})
@EnableTransactionManagement
@Configuration
public class PersistenceConfig {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceVar dataSourceVar() {
        return new DataSourceVar();
    }

    @Bean
    public DataSource dataSource(DataSourceVar var) {
        String username = getValueOrDefault(System.getenv("DB_ENV_MYSQL_USER"), var.getUsername());
        String password = getValueOrDefault(System.getenv("DB_ENV_MYSQL_PASSWORD"), var.getPassword());
        String url = getValueOrDefault(getUrlOrDefault(
                        System.getenv("DB_ENV_MYSQL_DATABASE"),
                        System.getenv("DB_PORT_3306_TCP_ADDR"),
                        System.getenv("DB_PORT_3306_TCP_PORT")),
                var.getUrl());

        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.username(username);
        builder.password(password);
        builder.url(url);
        return builder.build();
    }

    private String getUrlOrDefault(String db, String ip, String port) {
        if (StringUtils.isEmpty(db) || StringUtils.isEmpty(ip) || StringUtils.isEmpty(port)) {
            return null;
        } else {
            return format("jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8", ip, port, db);
        }
    }

    private String getValueOrDefault(String value, String defaultVal) {
        return StringUtils.isEmpty(value) ? defaultVal : value;
    }

    @Data
    public static class DataSourceVar {
        private String username;
        private String password;
        private String url;
    }
}
