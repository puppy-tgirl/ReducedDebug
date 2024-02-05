package me.nyaaa.reduced.utils;

public class MathUtils {
    public static double round(double value, int places) {
        return (double) (int) (value * (10 ^ places)) / (10 ^ places);
    }
}
