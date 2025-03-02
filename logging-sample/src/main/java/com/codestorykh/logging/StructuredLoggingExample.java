package com.codestorykh.logging;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class StructuredLoggingExample {

    public static String runExample() {
        log.info("This is an INFO message - Application started successfully");
        log.warn("This is a WARN message - Something might need attention");
        try {
            throw new RuntimeException("Something went wrong!");
        } catch (Exception e) {
            log.error("This is an ERROR message - An error occurred", e);
        }
        return "Structured logging example executed successfully";
    }
}