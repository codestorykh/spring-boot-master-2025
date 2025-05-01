package com.codestorykh.streams.controller;

import com.codestorykh.streams.producer.StreamProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling Redis Stream operations.
 * Provides endpoints for sending messages to Redis Streams.
 */
@Slf4j
@RestController
@RequestMapping("/api/stream")
public class StreamController {

    private final StreamProducer streamProducer;
    private static final int DEFAULT_MESSAGE_COUNT = 100;

    /**
     * Constructor for dependency injection.
     *
     * @param streamProducer The service responsible for producing messages to Redis Stream
     */
    public StreamController(StreamProducer streamProducer) {
        this.streamProducer = streamProducer;
    }

    /**
     * Endpoint for sending multiple messages to Redis Stream.
     * Creates multiple messages by appending an index to the base message.
     *
     * @param message The base message to be sent
     * @param count Optional parameter to specify how many messages to send (defaults to 100)
     * @return A confirmation message indicating successful operation
     */
    @PostMapping("/send")
    public String sendMessages(
            @RequestParam(required = true) String message,
            @RequestParam(required = false, defaultValue = "100") int count) {

        if (message == null || message.trim().isEmpty()) {
            log.warn("Attempted to send empty message");
            return "Error: Message cannot be empty";
        }

        int messageCount = Math.min(Math.max(count, 1), 1000); // Ensure count is between 1 and 1000
        log.info("Sending {} messages with base text: {}", messageCount, message);

        for (int i = 0; i < messageCount; i++) {
            String formattedMessage = String.format("%s (message %d of %d)", message, i + 1, messageCount);
            streamProducer.produceMessage(formattedMessage);
        }

        log.info("Successfully sent {} messages to Redis Stream", messageCount);
        return String.format("Successfully sent %d messages with base text: %s", messageCount, message);
    }
}
