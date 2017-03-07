package com.gamesharp.jfenix13.general;

public class Position {
    private float x;
    private float y;

    public Position() {

    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void addX(float x) {
        this.x += x;
    }

    public float getY() {
        return y;
    }

    public void addY(float y) {
        this.y += y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
