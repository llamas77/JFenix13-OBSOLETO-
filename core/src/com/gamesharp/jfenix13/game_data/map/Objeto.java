package com.gamesharp.jfenix13.game_data.map;


import com.gamesharp.jfenix13.graphics.Grh;

public class Objeto {
    private int index;
    private int cantidad;
    private Grh grh;

    public Objeto() {
        grh = null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Grh getGrh() {
        return grh;
    }

    public void setGrh(Grh grh) {
        this.grh = grh;
    }
}
