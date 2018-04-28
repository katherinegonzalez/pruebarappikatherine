package com.katherine.pruebarappi.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class NetValidationTest {

    @Test
    public void isOnlineNet() {
        Boolean isSuccessful = false;
        try {
            Process process = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            Integer val = process.waitFor();
            isSuccessful = true;
        } catch (Exception e) {
            isSuccessful = false;
        }

        assertTrue(isSuccessful);
    }
}