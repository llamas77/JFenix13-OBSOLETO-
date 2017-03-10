package com.gamesharp.jfenix13.general;

import static com.gamesharp.jfenix13.general.Direccion.*;

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

    /**
     * Obtener la suma de la posición actual y una pasada por parámetro
     */
    public Position getSuma(Position pos) {
        Position p = new Position(this.getX(), this.getY());
        p.addX(pos.getX());
        p.addY(pos.getY());

        return p;
    }

    /**
     * A la posición actual se le agrega una pasada por parámetro
     */
    public void sumar(Position pos) {
        addX(pos.getX());
        addY(pos.getY());
    }

    /**
     * Obtiene una posición relativa según una dirección
     */
    public static Position dirToPos(int dir) {
        Position pos = new Position();

        switch (dir) {
            case NORTE:
                pos.setY(-1);
                break;
            case ESTE:
                pos.setX(1);
                break;
            case SUR:
                pos.setY(1);
                break;
            case OESTE:
                pos.setX(-1);
                break;
        }
        return pos;
    }
}
