package com.codestorykh.redis.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

@Slf4j
@Service
public class RedisString {

    private final JedisPool jedisPool;

    public RedisString(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public void setStringValue(String key, String value) {
        try(var jedis = jedisPool.getResource()){
            jedis.set(key, value);
        } catch (Exception e) {
            log.error("Error setting value in Redis", e);
        }
    }

    public String getStringValue(String key) {
        try(var jedis = jedisPool.getResource()){
            return jedis.get(key);
        } catch (Exception e) {
            log.error("Error getting value from Redis", e);
        }
        return null;
    }

    public void deleteStringValue(String key) {
        try(var jedis = jedisPool.getResource()){
            jedis.del(key);
        } catch (Exception e) {
            log.error("Error deleting value from Redis", e);
        }
    }

    public void expireStringValue(String key, int seconds) {
        try(var jedis = jedisPool.getResource()){
            jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("Error setting expiration for value in Redis", e);
        }
    }

    public void persistStringValue(String key) {
        try(var jedis = jedisPool.getResource()){
            jedis.persist(key);
        } catch (Exception e) {
            log.error("Error persisting value in Redis", e);
        }
    }
}
