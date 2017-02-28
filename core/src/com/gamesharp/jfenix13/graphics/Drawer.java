package com.gamesharp.jfenix13.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.gamesharp.jfenix13.general.Main;
import com.gamesharp.jfenix13.general.Rect;
import com.gamesharp.jfenix13.resources.objects.Char;
import com.gamesharp.jfenix13.resources.objects.Font;
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
    public static final int PRINCIPAL = 0;
    public static final int FUENTE = 1;


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
                if (grh.getFrame() >= grhData.getCantFrames() + 1) {
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

        draw(batch, grhDataCurrent.getTR(), x, y, dp);
    }

    public static void draw(Batch batch, TextureRegion reg, int x, int y) {
        draw(batch, reg, x, y, dp);
    }

    /**
     * Dibuja una región de textura en donde
     */
    public static void draw(Batch batch, TextureRegion reg, int x, int y, DrawParameter dp) {
        if (reg == null) return;

        Sprite sp = new Sprite(reg);

        sp.setColors(dp.getColors());
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

    /**
     * Devuelve una TextureRegion según el número de gráfico
     * Es conveniente llamar a este método una sola vez, porque es un proceso algo lento.
     */
    public static TextureRegion getTextureRegion(int tipoGrafico, int numGrafico, Rect r) {
        TextureAtlas atlas;
        TextureRegion reg = null;

        switch (tipoGrafico) {
            case PRINCIPAL:
                atlas = Main.game.assets.getAM().get(getAtlasTexDir(), TextureAtlas.class);
                // Lo busco en el atlas
                reg = atlas.findRegion(Integer.toString(numGrafico));

                // Si no existe, lo busco entre los gráficos grandes
                if (reg == null) {
                    String bigTexName = getBigTexDir() + "/" + numGrafico + ".png";
                    if (!Main.game.assets.getAM().isLoaded(bigTexName, Texture.class)) return null;
                    reg = new TextureRegion(Main.game.assets.getAM().get(bigTexName, Texture.class));
                }
                break;
            case FUENTE:
                atlas = Main.game.assets.getAM().get(getAtlasFontTexDir(), TextureAtlas.class);
                reg = atlas.findRegion(Integer.toString(numGrafico));
                break;
        }

        // Verificamos si hay que buscar una región específica de la textura.
        if (r != null)
            reg = new TextureRegion(reg, (int) r.getLeft(), (int) r.getTop(), (int) r.getWidth(), (int) r.getHeight());

        return reg;
    }

    public static void drawText(Batch batch, int numFont, String text, int x, int y) {
        drawText(batch, numFont, text, x, y, dp);
    }

    public static void drawText(Batch batch, int numFont, String text, int x, int y, DrawParameter dp) {
        Font[] fonts = Main.game.assets.getFonts().getFonts();
        if (text.length() == 0) {
            return;
        }

        if (numFont < 1 || numFont > fonts.length) {
            return;
        }

        Char c;
        int tempX = 0;
        Font font = fonts[numFont - 1];


        for (int i = 0; i < text.length(); i++) {
            try {
                c = font.getChars()[text.charAt(i)];
            } catch (ArrayIndexOutOfBoundsException ex){
                c = font.getChars()[63];
            }

            draw(batch, c.getTR(), tempX + x, y, dp);
            tempX += (c.getWidth() + font.getOffset()) * dp.getScaleX();
        }
    }

}
