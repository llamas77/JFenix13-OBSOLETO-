package com.gamesharp.jfenix13.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.gamesharp.jfenix13.general.Main;
import com.gamesharp.jfenix13.resources.objects.GrhData;
import static com.gamesharp.jfenix13.graphics.Grh.*;
import static com.gamesharp.jfenix13.general.Main.*;
import static com.gamesharp.jfenix13.general.FileNames.*;

/**
 * Contiene lo referido a la manipulacion de texturas
 *
 * DEF_SEGS_PER_FRAME: segundos que dura cada frame si no se lo especifica
 */
public final class Drawer {
    private static final float DEF_SEGS_PER_FRAME = 0.1f;

    /**
     * Actualiza al Grh y devuelve el TextureRegion correspondiente
     * @return
     */
    public static void drawGrh(Grh grh, int x, int y, byte center, byte animate, Batch batch) {
        GrhData grhData = Main.game.assets.getGrhs().getGrhData(grh.getIndex());

        if (animate > 0) {
            if (grh.getStarted() == 1) {
                grh.setFrame(grh.getFrame() + (Gdx.graphics.getDeltaTime() * grhData.getCantFrames() / grh.getSpeed()));
                if (grh.getFrame() > grhData.getCantFrames() + 1) {
                    grh.setFrame(grh.getFrame() % grhData.getCantFrames());

                    if (grh.getLoops() != INF_LOOPS) {
                        if (grh.getLoops() > 0)
                            grh.setLoops((short)(grh.getLoops() - 1));
                        else
                            grh.getStarted();
                    }
                }
            }
        }

        short currentIndex = grhData.getFrame((short)(grh.getFrame() - 1));
        GrhData grhDataCurrent = Main.game.assets.getGrhs().getGrhData(currentIndex);

        if (center > 0) {
            if (grhDataCurrent.getRect().getTileWidth() != 1)
                x = x - (int)(grhDataCurrent.getRect().getTileWidth() * TILE_PIXEL_WIDTH / 2) + TILE_PIXEL_WIDTH / 2;

            if (grhDataCurrent.getRect().getTileHeight() != 1)
                y = y - (int)(grhDataCurrent.getRect().getTileHeight() * TILE_PIXEL_HEIGHT) + TILE_PIXEL_HEIGHT;
        }

        TextureAtlas atlas = Main.game.assets.getAM().get(getAtlasTexDir(), TextureAtlas.class);
        TextureRegion reg = atlas.findRegion(Integer.toString(grhDataCurrent.getFileNum()));
        TextureRegion reg2 = new TextureRegion(reg, (int)grhDataCurrent.getRect().getLeft(),
                (int)(grhDataCurrent.getRect().getTop()),
                (int)grhDataCurrent.getRect().getWidth(), (int)grhDataCurrent.getRect().getHeight());

        batch.draw(reg2, x, y);
    }



    public static Animation getAnimation(String atlasName, String nombre) {
        return getAnimation(atlasName, nombre, 1, 1, DEF_SEGS_PER_FRAME);
    }

    public static Animation getAnimation(String atlasName, String nombre, int X) {
        return getAnimation(atlasName, nombre, X, 1, DEF_SEGS_PER_FRAME);
    }

    public static Animation getAnimation(String atlasName, String nombre, float duracion) {
        return getAnimation(atlasName, nombre, 1, 1, duracion);
    }

    public static Animation getAnimation(String atlasName, String nombre, int X, float duracion) {
        return getAnimation(atlasName, nombre, X, 1, duracion);
    }

    public static Animation getAnimation(String atlasName, String nombre, int X, int Y) {
        return getAnimation(atlasName, nombre, X, Y, DEF_SEGS_PER_FRAME);
    }

    /**
     * Obtener una animación desde un atlas.
     *
     * @param atlasName TextureAtlas en donde se encuentra la animacion.
     * @param nombre nombre indexado en el atlas
     * @param X cantidad de frames contados horizontalmente
     * @param Y cantidad de frames contados verticalmente
     * @param duracion duración de cada frame en segundos
     * */
    public static Animation getAnimation(String atlasName, String nombre, int X, int Y, float duracion) {
        TextureAtlas atlas = Main.game.assets.getAM().get(atlasName, TextureAtlas.class);
        Animation anim = null;
        TextureRegion texR = atlas.findRegion(nombre);
        TextureRegion[][] temp = texR.split(texR.getRegionWidth() / X, texR.getRegionHeight() / Y);
        TextureRegion[] frames = new TextureRegion[temp.length * temp[0].length];
        int indice = 0;
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                frames[indice++] = temp[i][j];
            }
        }
        anim = new Animation(duracion, frames);
        return anim;
    }

    /**
     * Obtener un TextureRegion desde un atlas
     *
     * @param atlasName: nombre del atlas
     * @param nombreTex: nombre de la textura
     * @return
     */
    /*public static TextureRegion getTextureRegion(String atlasName, String nombreTex) {
        TextureAtlas atlas = Main.game.assets.getAM().get(atlasName, TextureAtlas.class);
        return atlas.findRegion(nombreTex);
    }*/

}