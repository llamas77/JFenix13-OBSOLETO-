package com.gamesharp.jfenix13.graphics;

import com.gamesharp.jfenix13.game_data.map.MapTile;
import com.gamesharp.jfenix13.general.Position;

public class DrawData {
    public static final int T_CAPA1 = 0;
    public static final int T_CAPA2 = 1;
    public static final int T_OBJETO = 2;
    public static final int T_PERSONAJE = 3;
    public static final int T_CAPA3 = 4;

    public MapTile tile;
    public Position pos;
    public int tipo;

    public DrawData() {

    }

    public DrawData(MapTile tile, Position pos) {
        this(tile, pos, -1);
    }

    public DrawData(MapTile tile, Position pos, int tipo) {
        this.tile = tile;
        this.pos = pos;
        this.tipo = tipo;
    }
}
