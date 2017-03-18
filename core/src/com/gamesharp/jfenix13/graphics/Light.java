package com.gamesharp.jfenix13.graphics;

import com.badlogic.gdx.graphics.Color;

/**
 * Contiene las características de una luz
 */
public class Light {
    private Color color;
    private int rango;
    private float intensidad;

    public Light() {
        color = new Color(1, 1, 1, 1);
    }

    public Light(Color color, int rango, float intensidad) {
        this.color = color;
        this.rango = rango;
        this.intensidad = intensidad;
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

    public float getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(float intensidad) {
        this.intensidad = intensidad;
    }
}
