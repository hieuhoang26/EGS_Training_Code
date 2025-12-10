package org.example.search_advance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SearchAdvanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SearchAdvanceApplication.class, args);
    }

}
