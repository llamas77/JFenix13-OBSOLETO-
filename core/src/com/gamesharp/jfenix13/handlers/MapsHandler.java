package com.gamesharp.jfenix13.handlers;


import com.gamesharp.jfenix13.resources.objects.Map;

public class MapsHandler {
    private Map mapa;

    public MapsHandler() {
        load(1);
    }

    public void load(int index) {
        mapa = new Map(index);
    }

    public Map getMapa() {
        return mapa;
    }
}
