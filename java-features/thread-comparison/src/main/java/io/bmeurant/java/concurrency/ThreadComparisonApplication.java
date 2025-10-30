package io.bmeurant.java.concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// The annotation tells Spring Boot to enable virtual threads for the Web server (Tomcat)
// if the system property -Dspring.threads.virtual.enabled=true is passed.
@SpringBootApplication
public class ThreadComparisonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThreadComparisonApplication.class, args);
    }
}