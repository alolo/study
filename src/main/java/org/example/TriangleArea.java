package org.example;

public class TriangleArea {
    public double area(double base, double height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Основание и высота не могут быть меньше или равны 0");
        }
        return (base * height) / 2.0;
    }
}
