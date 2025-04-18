package com.codestorykh.streams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RedisStreamsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisStreamsApplication.class, args);
    }
}