package com.codestorykh.generics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("Box Generics container test")
class BoxTest {

    private Box<String> stringBox;

    @BeforeEach
    void setup() {
        stringBox = new Box<>();
        Box<Integer> integerBox = new Box<>();
    }

    @DisplayName("Test create box with empty content")
    @Test
    void testCreateBoxWithEmptyContent() {
        assertAll(
                () -> assertNull(stringBox.getContent()),
                () -> assertFalse(stringBox.hasContent()),
                () -> assertEquals("Box{content=null}", stringBox.toString())
        );
    }

    @DisplayName("Test create box with content")
    @Test
    void testCreateBoxWithContent() {
        Box<String> stringBox = new Box<>("Hello");
        assertEquals("Hello", stringBox.getContent());
    }

    @DisplayName("Test transferm content")
    @Test
    void testTransformContent() {
        Box<String> stringBox = new Box<>("Hello");

        Box<Integer> integerBox = stringBox.transformContent(String::length);
        assertEquals(5, integerBox.getContent());
    }
}