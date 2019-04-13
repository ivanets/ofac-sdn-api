package com.yellow.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.yellow.api")
@EnableAutoConfiguration
public class OFACApplication {
    public static void main(String[] args) {
        SpringApplication.run(OFACApplication.class, args);
    }
}
