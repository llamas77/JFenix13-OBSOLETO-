package com.gamesharp.jfenix13.graphics;

import com.badlogic.gdx.graphics.Color;

public class Luz {
    private Color color;
    private int rango;

    public Luz() {

    }

    public Luz(Color color, int rango) {
        this.color = color;
        this.rango = rango;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setColor(int r, int g, int b, int a) {
        setColor((float)r/255, (float)g/255, (float)b/255, (float)a/255);
    }

    public void setColor(float r, float g, float b, float a) {
        this.color = new Color(r, g, b, a);
    }

    public int getRango() {
        return rango;
    }

    public void setRango(int rango) {
        this.rango = rango;
    }
}
