package com.gamesharp.jfenix13.resources.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gamesharp.jfenix13.game_data.map.MapTile;
import com.gamesharp.jfenix13.general.Util;
import com.gamesharp.jfenix13.graphics.Grh;
import com.gamesharp.jfenix13.graphics.Luz;

import java.io.DataInputStream;
import java.io.IOException;

import static com.gamesharp.jfenix13.general.FileNames.*;

public class Map {

    public MapTile[][] tiles;
    private int numero;

    public Map(int numero) {
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

                Luz luz = new Luz();
                luz.setColor(rojo, verde, azul, 255);
                luz.setRango(Util.leReadByte(dis));

                tiles[x][y].setLuz(luz);
            }
        }

        if (cantParticulas > 0) {
            for (int i = 0; i < cantParticulas; i++) {
                int x = Util.leReadByte(dis) - 1;
                int y = Util.leReadByte(dis) - 1;
                tiles[x][y].setParticula(Util.leReadInt(dis));
            }
        }

        dis.close();
    }
}
