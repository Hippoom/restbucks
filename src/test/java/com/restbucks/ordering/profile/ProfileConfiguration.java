package com.restbucks.ordering.profile;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;

import static java.lang.String.format;

@Configuration
@PropertySource(value = "classpath:env.yml", ignoreResourceNotFound = true)
public class ProfileConfiguration {
    @Value("${application_host}")
    private String applicationHost;
    @Value("${application_port}")
    private int applicationPort;
    @Value("${application_version}")
    private String applicationVersion;

    public String orderingServiceUrl() {
        return format("http://%s:%d", applicationHost, applicationPort);
    }

    public String getApplicationBaseUri() {
        return format("http://%s", applicationHost);
    }

    public int getApplicationPort() {
        return applicationPort;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    @PostConstruct
    public void replaceConfigItemWithEnvironmentVariablesIfPresent() {
        final String appHost = System.getenv("APP_PORT_8080_TCP_ADDR");
        final String appPort = System.getenv("APP_PORT_8080_TCP_PORT");

        if (StringUtils.isNotBlank(appHost)) {
            this.applicationHost = appHost;
        }
        if (StringUtils.isNotBlank(appPort)) {
            this.applicationPort = Integer.valueOf(appPort);
        }
    }


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreResourceNotFound(true);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}
