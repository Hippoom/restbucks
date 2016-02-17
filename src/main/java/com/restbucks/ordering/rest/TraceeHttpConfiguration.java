package com.restbucks.ordering.rest;

import io.tracee.binding.servlet.TraceeFilter;
import io.tracee.binding.servlet.TraceeServletRequestListener;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
public class TraceeHttpConfiguration {

    @Bean
    protected FilterRegistrationBean traceeFilter() {
        FilterRegistrationBean frb = new FilterRegistrationBean();
        frb.setFilter(new TraceeFilter());
        frb.setOrder(HIGHEST_PRECEDENCE);
        frb.addUrlPatterns("/*");
        return frb;
    }

    @Bean
    protected ServletListenerRegistrationBean traceeServletListener() {
        return new ServletListenerRegistrationBean<>(new TraceeServletRequestListener());
    }
}
