package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IntCompareTest {

    @Test
    public void compare_less() {
        IntCompare cmp = new IntCompare();
        Assert.assertEquals(cmp.compare(0, 1), -1);
    }

    @Test
    public void compare_equal() {
        IntCompare cmp = new IntCompare();
        Assert.assertEquals(cmp.compare(100, 100), 0);
    }

    @Test
    public void compare_greater() {
        IntCompare cmp = new IntCompare();
        Assert.assertEquals(cmp.compare(999, 1000), 1);
    }
}
