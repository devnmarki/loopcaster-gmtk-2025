package com.devnmarki.engine.math;

public class Vector2 {

    public float x;
    public float y;

    public Vector2() {
        this.x = 0f;
        this.y = 0f;
    }

    public Vector2(float value) {
        this.x = value;
        this.y = value;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 v) {
        set(v);
    }

    public static Vector2 zero() {
        return new Vector2(0, 0);
    }

    public static Vector2 one() {
        return new Vector2(1, 1);
    }

    public static Vector2 left() {
        return new Vector2(-1, 0);
    }

    public static Vector2 right() {
        return new Vector2(1, 0);
    }

    public static Vector2 up() {
        return new Vector2(0, 1);
    }

    public static Vector2 down() {
        return new Vector2(0, -1);
    }

    public Vector2 set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;

        return this;
    }

    public Vector2 cpy() {
        return new Vector2(this);
    }

    public String convertToString() {
        return "X: " + this.x + ", Y: " + this.y;
    }

    public static Vector2 moveTowards(Vector2 current, Vector2 target, float maxDistanceDelta) {
        float toX = target.x - current.x;
        float toY = target.y - current.y;
        float dist = (float) Math.sqrt(toX * toX + toY * toY);

        if (dist <= maxDistanceDelta || dist == 0f) {
            return new Vector2(target);
        }

        return new Vector2(
                current.x + toX / dist * maxDistanceDelta,
                current.y + toY / dist * maxDistanceDelta
        );
    }

    public Vector2 normalized() {
        float len = length();
        return len != 0 ? new Vector2(x / len, y / len) : new Vector2(0, 0);
    }

    public boolean epsilonEquals(Vector2 other, float epsilon) {
        return Math.abs(x - other.x) < epsilon && Math.abs(y - other.y) < epsilon;
    }

    public boolean epsilonXEquals(Vector2 other, float epsilon) {
        return Math.abs(x - other.x) < epsilon;
    }

    public boolean epsilonYEquals(Vector2 other, float epsilon) {
        return Math.abs(y - other.y) < epsilon;
    }

    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Vector2 add(Vector2 v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2 add(float v) {
        this.x += v;
        this.y += v;
        return this;
    }

    public Vector2 sub(Vector2 v) {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2 sub(float v) {
        this.x -= v;
        this.y -= v;
        return this;
    }

    public Vector2 mul(Vector2 v) {
        this.x *= v.x;
        this.y *= v.y;
        return this;
    }

    public Vector2 mul(float v) {
        this.x *= v;
        this.y *= v;
        return this;
    }

    public Vector2 div(Vector2 v) {
        this.x /= v.x;
        this.y /= v.y;
        return this;
    }

    public Vector2 div(float v) {
        this.x /= v;
        this.y /= v;
        return this;
    }

    public Vector2 nor () {
        float len = length();
        if (len != 0) {
            x /= len;
            y /= len;
        }
        return this;
    }
}
