package com.restbucks.ordering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.restbucks.ordering", "com.restbucks.commandhandling"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}