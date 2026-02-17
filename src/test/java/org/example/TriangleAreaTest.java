package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriangleAreaTest {

    @Test
    void area_base5_height2_is5() {
        TriangleArea t = new TriangleArea();
        assertEquals(5, t.area(5, 2), 0.000001);
    }

    @Test
    void area_invalid_throwsException() {
        TriangleArea t = new TriangleArea();
        assertThrows(IllegalArgumentException.class, () -> t.area(0, 5));
    }
}
