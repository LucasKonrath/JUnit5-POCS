package com.lucasdamaceno.junit5poc.nested;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NestedTest {

    @Nested
    @Tag("nested")
    class FirstNestedClass {
        @Test
        @DisplayName("First Nested Test")
        public void test(){
            assertTrue(true);
        }

        @Nested
        @Tag("nested")
        class SecondNestedClass {
            @Test
            @DisplayName("Second Nested Test")
            public void test(){
                assertTrue(true);
            }

            @Nested
            @Tag("nested")
            class ThirdNestedClass {
                @Test
                @DisplayName("Third Nested Test")
                public void test(){
                    assertTrue(true);
                }
            }
        }

    }

}
