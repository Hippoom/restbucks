package com.restbucks.ordering.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

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

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreResourceNotFound(true);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}
