package com.example.observabilityspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // Import

@SpringBootApplication
@EnableScheduling // Enable scheduling
public class ObservabilitySpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObservabilitySpringApplication.class, args);
    }

}
