package org.example;

public class Factorial {

    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Факториал для отрицательных чисел не определён");
        }

        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}