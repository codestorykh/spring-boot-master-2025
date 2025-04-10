package com.codestorykh.subscribe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

@Slf4j
@Service
public class RedisSimpleSubscribe {

    private final JedisPool jedisPool;

    public RedisSimpleSubscribe(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void subscribeMessage(){
        try{
            JedisPubSub jedisPubSub = new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    log.info("Received message: {} from channel: {}", message, channel);
                }
            };
            jedisPool.getResource().subscribe(jedisPubSub, "my-channel");
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
