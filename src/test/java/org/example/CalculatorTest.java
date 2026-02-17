package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    void add_works() {
        Calculator c = new Calculator();
        assertEquals(4, c.add(2, 2));
    }

    @Test
    void sub_works() {
        Calculator c = new Calculator();
        assertEquals(0, c.sub(2, 2));
    }

    @Test
    void mul_works() {
        Calculator c = new Calculator();
        assertEquals(64, c.mul(8, 8));
    }

    @Test
    void div_works_integerDivision() {
        Calculator c = new Calculator();
        assertEquals(3.5, c.div(7.0, 2.0));
    }

    @Test
    void div_byZero_throwsException() {
        Calculator c = new Calculator();
        assertThrows(ArithmeticException.class, () -> c.div(10, 0));
    }
}
