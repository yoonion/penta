package com.task.penta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PentaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PentaApplication.class, args);
    }

}
