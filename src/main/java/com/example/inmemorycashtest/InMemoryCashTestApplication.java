package com.example.inmemorycashtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // springbootのdefault値をCache
public class InMemoryCashTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(InMemoryCashTestApplication.class, args);
    }

}
