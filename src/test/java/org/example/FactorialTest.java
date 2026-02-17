package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FactorialTest {

    @Test
    public void factorial_of5_is120() {
        Factorial f = new Factorial();
        Assert.assertEquals(f.factorial(5), 120L);
    }

    @Test
    public void factorial_of0_is1() {
        Factorial f = new Factorial();
        Assert.assertEquals(f.factorial(0), 1L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void factorial_negative_throwsException() {
        Factorial f = new Factorial();
        f.factorial(-1);
    }
}
