package io.bmeurant.bookordermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BookOrderManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookOrderManagerApplication.class, args);
    }

}
