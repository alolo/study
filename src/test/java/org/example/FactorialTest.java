package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactorialTest {

    @Test
    void factorial_of5_is120() {
        Factorial f = new Factorial();
        assertEquals(120, f.factorial(5));
    }

    @Test
    void factorial_of0_is1() {
        Factorial f = new Factorial();
        assertEquals(1, f.factorial(0));
    }

    @Test
    void factorial_negative_throwsException() {
        Factorial f = new Factorial();
        assertThrows(IllegalArgumentException.class, () -> f.factorial(-1));
    }
}