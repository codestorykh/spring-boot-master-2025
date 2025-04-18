package com.codestorykh.streams.consumer;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class StreamConsumer {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String STREAM_KEY = "my-stream";
    private static final String GROUP_NAME = "my-group";
    private static final String CONSUMER_NAME = "consumer-1";

    public StreamConsumer(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        // Create the consumer group if it doesn't exist
        try {
            redisTemplate.opsForStream().createGroup(STREAM_KEY, ReadOffset.from("0"), GROUP_NAME);
        } catch (Exception e) {
            log.info("Consumer group already exists or stream not initialized: {}", e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    @Scheduled(fixedRate = 2000) // run every 2 seconds
    public void consumeMessages() {
        StreamOffset<String> streamOffset = StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed());

        @SuppressWarnings("unchecked")
        List<ObjectRecord<String, String>> records = redisTemplate.opsForStream()
                .read(String.class,
                        Consumer.from(GROUP_NAME, CONSUMER_NAME),
                        StreamReadOptions.empty().count(1),
                        streamOffset);

        if (records != null && !records.isEmpty()) {
            for (ObjectRecord<String, String> record : records) {
                log.info("Consumed message: {}", record.getValue());
                redisTemplate.opsForStream().acknowledge(STREAM_KEY, GROUP_NAME, record.getId());
            }
        } else {
            log.info("No new messages to consume.");
        }
    }


}