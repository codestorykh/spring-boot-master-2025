package com.codestorykh.streams.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OrderEventProducer {

    private static final String STREAM_KEY = "orders";

    private final StringRedisTemplate redisTemplate;

    public OrderEventProducer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void publishOrderEvent(String orderId) {
        Map<String, String> message = new HashMap<>();
        message.put("orderId", orderId);
        message.put("event", "ORDER_CREATED");
        message.put("timestamp", String.valueOf(System.currentTimeMillis()));

        // Publish the event to Redis Stream
        RecordId recordId = redisTemplate.opsForStream().add(STREAM_KEY, message);
        if(recordId != null) {
            log.info("Published event with ID: {}", recordId.getValue());
        } else {
            log.error("Failed to publish event");
        }

        // Auto-trim: Keep only the latest 1000 messages
        redisTemplate.opsForStream().trim(STREAM_KEY, 1000); // Approximate trim
    }
}
