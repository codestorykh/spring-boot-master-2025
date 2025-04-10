package com.codestorykh.subscribe;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisSubscribeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedisSubscribeApplication.class, args);
    }

    @Autowired
    private RedisSimpleSubscribe redisSimpleSubscribe;

    @PostConstruct
    public void init() {
        redisSimpleSubscribe.subscribeMessage();
    }
}