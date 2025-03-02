package com.codestorykh.logging;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class HomeRestController {

    @GetMapping("logs")
    public ResponseEntity<Object> getLogs() {
        log.info("This is an INFO message - Application started successfully");
        log.info("MDC contents: {}", org.slf4j.MDC.getCopyOfContextMap());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "text/plain");
        httpHeaders.add("Content-Disposition", "attachment; filename=\"logs.txt\"");
        return new ResponseEntity<>(StructuredLoggingExample.runExample(),  httpHeaders, HttpStatus.OK);
    }
}
