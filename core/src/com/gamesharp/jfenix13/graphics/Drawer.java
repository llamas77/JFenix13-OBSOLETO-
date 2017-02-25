package com.gamesharp.jfenix13.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.gamesharp.jfenix13.general.Main;
import com.gamesharp.jfenix13.general.Rect;
import com.gamesharp.jfenix13.resources.objects.GrhData;
import static com.gamesharp.jfenix13.graphics.Grh.*;
import static com.gamesharp.jfenix13.general.FileNames.*;

import static com.badlogic.gdx.graphics.GL20.*;

/**
 * Contiene lo referido a la manipulacion de texturas
 *
 * DEF_SEGS_PER_FRAME: segundos que dura cada frame si no se lo especifica
 * dp: es un objeto que contiene los parametros por defecto para dibujar.
 */
public final class Drawer {

    private static DrawParameter dp;

    static {
        dp = new DrawParameter();
    }

    /**
     * Dibuja un Grh sin especificarle más nada
     */
    public static void drawGrh(Batch batch, Grh grh, int x, int y) {
        drawGrh(batch, grh, x, y, dp);
    }

    /**
     * Dibuja un Grh especificandole una serie de parámetros
     */
    public static void drawGrh(Batch batch, Grh grh, int x, int y, DrawParameter dp) {
        GrhData grhData = Main.game.assets.getGrhs().getGrhData(grh.getIndex());

        if (dp.isAnimated()) {
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

        drawGrh(batch, grhData.getFrame((short)(grh.getFrame() - 1)), x, y, dp);
    }

    /**
     * Dibuja un Grh especificandole su Index, sin ningún otro parámetro.
     */
    public static void drawGrh(Batch batch, int index, int x, int y) {
        drawGrh(batch, index, x, y, dp);
    }

    /**
     * Dibuja un Grh especificándole su Index y una serie de parámetros.
     */
    public static void drawGrh(Batch batch, int index, int x, int y, DrawParameter dp) {
        GrhData grhDataCurrent = Main.game.assets.getGrhs().getGrhData(index);
        Rect r = new Rect();
        r.setLeft(grhDataCurrent.getRect().getLeft());
        r.setTop(grhDataCurrent.getRect().getTop());
        r.setWidth(grhDataCurrent.getRect().getWidth());
        r.setHeight(grhDataCurrent.getRect().getHeight());

        draw(batch, grhDataCurrent.getFileNum(), x, y, r, dp);
    }


    public static void draw(Batch batch, int numGrafico, int x, int y) {
        draw(batch, numGrafico, x, y, dp);
    }

    /**
     * Dibuja un gráfico contenido en el atlas de texturas.
     */
    public static void draw(Batch batch, int numGrafico, int x, int y, DrawParameter dp) {
        TextureAtlas atlas = Main.game.assets.getAM().get(getAtlasTexDir(), TextureAtlas.class);
        TextureRegion reg = atlas.findRegion(Integer.toString(numGrafico));

        draw(batch, reg, x, y, dp);
    }

    public static void draw(Batch batch, int numGrafico, int x, int y, Rect r) {
        draw(batch, numGrafico, x, y, r, dp);
    }

    /**
     * Dibuja un subgráfico de un gráfico contenido en el atlas de texturas
     */
    public static void draw(Batch batch, int numGrafico, int x, int y, Rect r, DrawParameter dp) {
        TextureAtlas atlas = Main.game.assets.getAM().get(getAtlasTexDir(), TextureAtlas.class);
        TextureRegion reg = atlas.findRegion(Integer.toString(numGrafico));

        TextureRegion reg2 = new TextureRegion(reg, (int)r.getLeft(), (int)r.getTop(),
                                    (int)r.getWidth(), (int)r.getWidth());

        draw(batch, reg2, x, y, dp);
    }

    /**
     * Dibuja una región de textura en donde
     */
    public static void draw(Batch batch, TextureRegion reg, int x, int y, DrawParameter dp) {
        Sprite sp = new Sprite(reg);

        sp.setColors(dp.getColors()); // Cambiar: Controlar el color por vértices
        sp.setAlphas(dp.getAlphas());

        sp.setScale(dp.getScaleX(), dp.getScaleY());
        sp.setPosition(x, y);
        sp.rotate(dp.getRotation());
        sp.flip(dp.isFlipX(), dp.isFlipY());

        if (dp.isCenter())
            sp.setCenter(sp.getX(), sp.getY());


        if (dp.isBlend())
            batch.setBlendFunction(dp.getBlendSrcFunc(), dp.getBlendDstFunc());

        sp.draw(batch);

        if (dp.isBlend())
            batch.setBlendFunction(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

}