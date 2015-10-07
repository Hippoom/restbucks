package com.restbucks.ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableEntityLinks;

@ComponentScan(basePackages = {"com.restbucks.ordering", "com.restbucks.commandhandling"})
@EnableAutoConfiguration
@EnableEntityLinks
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}