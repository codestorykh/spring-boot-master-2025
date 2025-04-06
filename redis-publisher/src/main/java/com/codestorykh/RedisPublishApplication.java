package com.codestorykh;

import com.codestorykh.service.RedisSimpleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class RedisPublishApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisPublishApplication.class, args);
    }

    @Autowired
    private RedisSimpleService redisSimpleService;

    @GetMapping("/publish")
    public void sendMessage() {
        redisSimpleService.sendMessage("Hello, Redis!");
    }

}