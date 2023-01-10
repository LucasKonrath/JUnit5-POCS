package com.lucasdamaceno.junit5poc.assumptions;


import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AssumptionsTests {
    @Test
    void test_assumption(){
        //Will always execute this.
        assumingThat(1 > 0,
                () -> assertTrue(true));

        //Will never execute this code.
        assumingThat(1 < 0,
                () -> assertTrue(false));
    }
}
