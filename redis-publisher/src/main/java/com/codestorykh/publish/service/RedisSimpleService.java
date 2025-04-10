package com.codestorykh.publish.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Slf4j
public class RedisSimpleService {

  private final JedisPool jedisPool;

    public RedisSimpleService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void sendMessage(String message){
        try(Jedis jedis = jedisPool.getResource()) {
            for(int i = 0; i < 10; i ++) {
                log.info("Publishing message: {}", message);

                jedis.publish("my-channel", message);
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

}
