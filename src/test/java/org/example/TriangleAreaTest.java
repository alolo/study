package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TriangleAreaTest {

    @Test
    public void area_base3_height4_is6() {
        TriangleArea t = new TriangleArea();
        Assert.assertEquals(t.area(3, 4), 6, 0.000001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void area_invalid_throwsException() {
        TriangleArea t = new TriangleArea();
        t.area(0, 5);
    }
}
