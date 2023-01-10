package com.lucasdamaceno.junit5poc.assertions;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AssertionsTests {
    @Test
    public void assert_exception(){
        assertThrows(NullPointerException.class,
                () -> {
                    throw new NullPointerException();
                });
    }

    @Test
    public void assert_timeout(){
        Supplier<String> supplier = () -> {
            try {
                Thread.sleep(2000l);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return "Done";
        };

        //This will pass. It waits 2500 milliseconds for response from the supplier.
        Assertions.assertTimeout(Duration.ofMillis(2500), supplier::get);

        //This will fail, as it only waits 20ms.
        Assertions.assertTimeout(Duration.ofMillis(20), supplier::get);
    }

    @Test
    public void assert_group_all(){
        assertAll("Numbers are positive",
                () -> assertTrue(2 > 0),
                () -> assertTrue(5 > 0),
                () -> assertTrue(-2 > 0));
    }
}
