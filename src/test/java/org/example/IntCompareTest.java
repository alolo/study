package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntCompareTest {

    @Test
    void compare_less() {
        IntCompare cmp = new IntCompare();
        assertEquals(-1, cmp.compare(1, 2));
    }

    @Test
    void compare_equal() {
        IntCompare cmp = new IntCompare();
        assertEquals(0, cmp.compare(3, 3));
    }

    @Test
    void compare_greater() {
        IntCompare cmp = new IntCompare();
        assertEquals(1, cmp.compare(500, 499));
    }
}