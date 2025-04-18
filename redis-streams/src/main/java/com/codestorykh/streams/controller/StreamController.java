package com.codestorykh.streams.controller;

import com.codestorykh.streams.producer.StreamProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamController {

    private final StreamProducer streamProducer;

    public StreamController(StreamProducer streamProducer) {
        this.streamProducer = streamProducer;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        for (int i = 0; i < 100; i++) {
            streamProducer.produceMessage(message + " " + i);
        }
        return "Message sent: " + message;
    }
}