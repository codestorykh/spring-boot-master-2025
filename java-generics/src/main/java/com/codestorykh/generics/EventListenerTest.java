package com.codestorykh.generics;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventListenerTest {

    @EventListener(ApplicationReadyEvent.class)
    public void eventListenerMethod() {
        System.out.println("Event received: ApplicationReadyEvent");
    }
}
