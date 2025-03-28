package com.codestorykh.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

@Configuration
public class RedisConfiguration {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    private JedisPoolConfig buildPoolConfig() {
        final  JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(128);
        jedisPoolConfig.setMaxIdle(128);
        jedisPoolConfig.setMinIdle(16);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setMinEvictableIdleDuration(Duration.ofSeconds(60));
        jedisPoolConfig.setTimeBetweenEvictionRuns(Duration.ofSeconds(60));
        jedisPoolConfig.setNumTestsPerEvictionRun(3);
        jedisPoolConfig.setBlockWhenExhausted(true);

        jedisPoolConfig.setJmxEnabled(false);

        return jedisPoolConfig;
    }

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool(buildPoolConfig(), host, port, 5000);
    }
}
