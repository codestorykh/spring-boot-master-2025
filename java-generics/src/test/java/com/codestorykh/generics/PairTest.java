package com.codestorykh.generics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    @DisplayName("Test pair creation")
    void testPairCreation() {
        Pair<String, String> pair = new Pair<>("key", "value");

        assertEquals("key", pair.getKey());
        assertEquals("value", pair.getValue());
    }

    @Test
    @DisplayName("Test pair creation with static factory method")
    void testPairCreationWithStaticFactoryMethod() {
        Pair<String, Double> pair =  Pair.of("pi", 3.14);
        assertEquals("pi", pair.getKey());
        assertEquals(3.14, pair.getValue(), 0.0001);
    }

}