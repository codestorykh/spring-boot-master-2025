package com.codestorykh.streams.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStreamCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class StreamProducer {

    private static final String STREAM_KEY = "my-stream";
    private final RedisTemplate<String, String> redisTemplate;

    public StreamProducer(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void produceMessage(String message) {
        // Create a record (message) to add to the stream
        Map<String, String> record = new HashMap<>();
        record.put("message", message);
        record.put("timestamp", String.valueOf(System.currentTimeMillis()));

        // Add the record to the stream
        redisTemplate.opsForStream().add(STREAM_KEY, record);
        log.info("Produce message: {}", message);


        // Auto-trim: Keep only the latest 50 messages
        redisTemplate.opsForStream().trim(STREAM_KEY, 50); // Approximate trim
    }

}