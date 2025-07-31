package com.devnmarki.engine.math;

public class MathUtils {

    public static float lerp(float a, float b, float t) {
        return a + (b - a) * Math.min(Math.max(t, 0f), 1f);
    }

}
