package com.gamesharp.jfenix13.resources.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.gamesharp.jfenix13.game_data.map.MapTile;
import com.gamesharp.jfenix13.general.Position;
import com.gamesharp.jfenix13.general.Rect;
import com.gamesharp.jfenix13.general.Util;
import com.gamesharp.jfenix13.graphics.Grh;
import com.gamesharp.jfenix13.graphics.Light;
import java.io.DataInputStream;
import java.io.IOException;

import static com.gamesharp.jfenix13.general.FileNames.*;
import static com.gamesharp.jfenix13.general.Main.*;

public class Map {

    private MapTile[][] tiles;
    private int numero;
    private Rect size;

    public Map(int numero) {
        this.numero = numero;

        try {
            load();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void load(int numero) {
        this.numero = numero;
        try {
            load();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void load() throws IOException {
        FileHandle fh = Gdx.files.internal(DIR_MAPS + "/Mapa" + numero + ".mcl");
        DataInputStream dis = new DataInputStream(fh.read());

        int cantBloqueados = Util.leReadInt(dis);
        int[] cantTilesEnCapa = {Util.leReadInt(dis), Util.leReadInt(dis), Util.leReadInt(dis)};
        int cantTriggers = Util.leReadInt(dis);
        int cantLuces = Util.leReadInt(dis);
        int cantParticulas = Util.leReadInt(dis);

        int xMax = Util.leReadByte(dis);
        int xMin = Util.leReadByte(dis);
        int yMax = Util.leReadByte(dis);
        int yMin = Util.leReadByte(dis);

        size = new Rect();
        size.setX1(xMin);
        size.setY1(yMin);
        size.setX2(xMax);
        size.setY2(yMax);

        int cantXTiles = Math.abs(xMax - xMin) + 1;
        int cantYTiles = Math.abs(yMax - yMin) + 1;

        tiles = new MapTile[cantXTiles][cantYTiles];
        for (int j = 0; j < tiles.length; j++) {
            for (int k = 0; k < tiles[j].length; k++) {
                tiles[j][k] = new MapTile();
            }
        }

        for (int y = 0; y < cantYTiles; y++) {
            for (int x = 0; x < cantXTiles; x++) {
                tiles[x][y].setCapa(0, new Grh(Util.leReadInt(dis)));
            }
        }

        if (cantBloqueados > 0) {
            for (int i = 0; i < cantBloqueados; i++) {
                int x = Util.leReadByte(dis) - 1;
                int y = Util.leReadByte(dis) - 1;
                tiles[x][y].setBlocked(true);
            }
        }

        for (int c = 0; c < cantTilesEnCapa.length; c++) {
            if (cantTilesEnCapa[c] > 0) {
                for (int i = 0; i < cantTilesEnCapa[c]; i++) {
                    int x = Util.leReadByte(dis) - 1;
                    int y = Util.leReadByte(dis) - 1;
                    tiles[x][y].setCapa(c + 1, new Grh(Util.leReadInt(dis)));
                }
            }
        }

        if (cantTriggers > 0) {
            for (int i = 0; i < cantTriggers; i++) {
                int x = Util.leReadByte(dis) - 1;
                int y = Util.leReadByte(dis) - 1;
                int trigger = Util.leReadByte(dis);
                tiles[x][y].setTrigger(trigger);
            }
        }

        if (cantLuces > 0) {
            for (int i = 0; i < cantLuces; i++) {
                int x = Util.leReadByte(dis) - 1;
                int y = Util.leReadByte(dis) - 1;
                int rojo = Util.leReadByte(dis);
                int verde = Util.leReadByte(dis);
                int azul = Util.leReadByte(dis);

                Light light = new Light();
                light.setColor(rojo, verde, azul, 255);
                light.setRango(Util.leReadByte(dis));

                tiles[x][y].setLight(light);
            }
        }

        // DEBUG
        tiles[49][49].setLight(new Light(new Color(1, 0, 0, 1), 1, 0.4f));
        tiles[52][47].setLight(new Light(new Color(0, 0, 1, 1), 1, 0.4f));
        // DEBUG

        if (cantParticulas > 0) {
            for (int i = 0; i < cantParticulas; i++) {
                int x = Util.leReadByte(dis) - 1;
                int y = Util.leReadByte(dis) - 1;
                tiles[x][y].setParticula(Util.leReadInt(dis));
            }
        }

        dis.close();
    }

    public MapTile getTile(int x, int y) {
        return tiles[x][y];
    }

    public int getNumero() {
        return numero;
    }

    public Rect getSize() {
        return size;
    }

    /**
     * Obtiene un rectángulo interno al mapa, que corresponden a los límites de posición del usuario.
     * Esto es para que lo último que se vea del mapa sean los límites, y no bordes negros.
     */
    public Rect getBorderRect() {
        int halfWindowsTileWidth = WINDOWS_TILE_WIDTH / 2;
        int halfWindowsTileHeight = WINDOWS_TILE_HEIGHT / 2;

        Rect r = new Rect();
        r.setX1(size.getX1() + halfWindowsTileWidth);
        r.setY1(size.getY1() + halfWindowsTileHeight);
        r.setX2(size.getX2() - halfWindowsTileWidth);
        r.setY2(size.getY2() - halfWindowsTileHeight);

        return r;
    }


    /**
     * Indica si es posible moverse hacia una dirección estando en una posición determinada.
     */
    public boolean isLegalPos(Position pos, int dir) {
        return isLegalPos(pos.getSuma(Position.dirToPos(dir)));
    }

    public boolean isLegalPos(float x, float y) {
        return isLegalPos(new Position(x, y));
    }

    /**
     * Indica si es posible moverse a un tile final (pos)
     */
    public boolean isLegalPos(Position pos) {
        MapTile tile = tiles[(int)pos.getX() - 1][(int)pos.getY() - 1];

        // Verificamos que no se pase ciertos límites (porque sino intentaría renderizar tiles que no existen)
        if (!getBorderRect().isPointIn(pos)) return false;

        if (tile.isBlocked()) return false;

        return true;
    }
}
