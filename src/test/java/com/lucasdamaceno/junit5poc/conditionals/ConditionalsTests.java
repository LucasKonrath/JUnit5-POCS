package com.lucasdamaceno.junit5poc.conditionals;


import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ConditionalsTests {

    @Test
    @EnabledOnOs({OS.MAC})
    public void test_mac_exclusive_features(){
        assertTrue(true);
    }

    @Test
    @EnabledOnOs({OS.WINDOWS})
    public void test_windows_exclusive_features(){
        assertTrue(true);
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "profile", matches = "demo")
    public void runs_only_on_demo(){
        assertTrue(true);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "profile", matches = "demo")
    public void doesnt_run_on_demo(){
        assertTrue(true);
    }

}
