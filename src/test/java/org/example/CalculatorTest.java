package org.example;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculatorTest {

    @Test
    public void add_works() {
        Calculator c = new Calculator();
        Assert.assertEquals(c.add(3, 4), 7);
    }

    @Test
    public void sub_works() {
        Calculator c = new Calculator();
        Assert.assertEquals(c.sub(3, 4), -1);
    }

    @Test
    public void mul_works() {
        Calculator c = new Calculator();
        Assert.assertEquals(c.mul(3, 4), 12);
    }

    @Test
    public void div_works_integerDivision() {
        Calculator c = new Calculator();
        Assert.assertEquals(c.div(7, 2), 3.5);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void div_byZero_throwsException() {
        Calculator c = new Calculator();
        c.div(10, 0);
    }
}