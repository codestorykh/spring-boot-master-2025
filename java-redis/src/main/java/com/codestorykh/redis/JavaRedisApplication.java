package com.codestorykh.redis;


import com.codestorykh.redis.service.RedisString;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaRedisApplication.class, args);
    }

    @Autowired
    private RedisString redisString;

    @PostConstruct
    public void init() {
        redisString.setStringValue("user:10009", "CodeStory");
        redisString.setStringValue("user:10009", "CodeStoryKH");

        //redisString.deleteStringValue("user:10009");
        
        redisString.expireStringValue("user:10009", 10);
        var value = redisString.getStringValue("user:10009");
        System.out.println("Value from Redis: " + value);

        redisString.persistStringValue("user:10009");
    }
}
