package com.codestorykh.cache;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class AppStatusController {

    @GetMapping("/api/v1/status")
    public ResponseEntity<Object> checkAppStatus() {
        System.out.println("Request received to check app status");
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
                .eTag("TEST_ETAG")
                .body("App is running");
    }
}
