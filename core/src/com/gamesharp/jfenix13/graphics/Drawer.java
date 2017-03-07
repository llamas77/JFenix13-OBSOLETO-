package com.gamesharp.jfenix13.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gamesharp.jfenix13.general.Main;
import com.gamesharp.jfenix13.general.Rect;
import com.gamesharp.jfenix13.resources.objects.Char;
import com.gamesharp.jfenix13.resources.objects.Font;
import com.gamesharp.jfenix13.resources.objects.GrhData;

import java.util.Stack;

import static com.gamesharp.jfenix13.graphics.Grh.*;
import static com.gamesharp.jfenix13.general.FileNames.*;
import static com.gamesharp.jfenix13.general.Main.*;

import static com.badlogic.gdx.graphics.GL20.*;

/**
 * Contiene lo referido a la manipulacion de texturas
 *
 * dp: es un objeto que contiene los parametros por defecto para dibujar.
 * containerRect: pila con datos del rectángulo del contenedor actual, para dibujar pasando una posición relativa.
 */
public final class Drawer {
    public static final int PRINCIPAL = 0;
    public static final int FUENTE = 1;


    public static Stack<Rectangle> containerRect;
    private static DrawParameter dp;

    static {
        dp = new DrawParameter();
        containerRect = new Stack();
        containerRect.push(new Rectangle(0, 0, SCR_WIDTH, SCR_HEIGHT));
    }

    public static void pushScissors(Stage stage, float x, float y, float width, float height) {
        pushScissors(stage, new Rectangle(x, y, width, height));
    }

    /**
     * Hace que todo lo que se dibuje luego, se limite a un rectángulo determinado por rect.
     */
    public static void pushScissors(Stage stage, Rectangle rect) {
        Rectangle rect2 = new Rectangle();
        rect2.setX(rect.getX());
        rect2.setY(containerRect.peek().getHeight() - rect.getY() - rect.getHeight());
        rect2.setWidth(rect.getWidth());
        rect2.setHeight(rect.getHeight());

        Rectangle scissors = new Rectangle();
        Viewport vp = stage.getViewport();
        ScissorStack.calculateScissors(stage.getCamera(), vp.getScreenX(), vp.getScreenY(),
                vp.getScreenWidth(), vp.getScreenHeight(), stage.getBatch().getTransformMatrix(), rect2, scissors);
        stage.getBatch().flush(); // para que lo que se dibuja antes se renderize antes de verse afectado por esto
        ScissorStack.pushScissors(scissors);
        containerRect.push(rect2);
    }

    /**
     * Libera la limitación del rectángulo.
     */
    public static void popScissors(Stage stage) {
        stage.getBatch().flush(); // para mandar a dibujar lo que está entre scissors
        ScissorStack.popScissors();
        containerRect.pop();
    }

    /**
     * Dibuja un Grh sin especificarle más nada
     */
    public static void drawGrh(Batch batch, Grh grh, float x, float y) {
        drawGrh(batch, grh, x, y, dp);
    }

    /**
     * Dibuja un Grh especificandole una serie de parámetros
     */
    public static void drawGrh(Batch batch, Grh grh, float x, float y, DrawParameter dp) {
        GrhData grhData = Main.game.assets.getGrhs().getGrhData(grh.getIndex());
        if (grhData == null) return;

        if (dp.isAnimated()) {
            if (grh.getStarted() == 1) {
                grh.setFrame(grh.getFrame() + (getDelta() * grhData.getCantFrames() / grh.getSpeed()));
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
    public static void drawGrh(Batch batch, int index, float x, float y) {
        drawGrh(batch, index, x, y, dp);
    }

    /**
     * Dibuja un Grh especificándole su Index y una serie de parámetros.
     */
    public static void drawGrh(Batch batch, int index, float x, float y, DrawParameter dp) {
        GrhData grhDataCurrent = Main.game.assets.getGrhs().getGrhData(index);
        if (grhDataCurrent == null) return;

        draw(batch, grhDataCurrent.getTR(), x, y, dp);
    }

    public static void draw(Batch batch, TextureRegion reg, float x, float y) {
        draw(batch, reg, x, y, dp);
    }

    /**
     * Dibuja una región de textura en donde
     */
    public static void draw(Batch batch, TextureRegion reg, float x, float y, DrawParameter dp) {
        if (reg == null) return;

        if (dp.isCenter()) {
            //sp.setCenter(sp.getX(), sp.getY() + reg.getRegionHeight());

            float tileWidth = (float)reg.getRegionWidth() / TILE_PIXEL_WIDTH;
            float tileHeight = (float)reg.getRegionHeight() / TILE_PIXEL_HEIGHT;

            x = (int)x;
            y = (int)y;

            if (tileWidth != 1f)
                x = (int)(x -(tileWidth * WINDOWS_TILE_WIDTH / 2) - WINDOWS_TILE_WIDTH / 2);

            if (tileHeight != 1f)
                y = (int)(y -(tileHeight * WINDOWS_TILE_HEIGHT) - WINDOWS_TILE_HEIGHT);

        }

        y = (int)containerRect.peek().getHeight() - y - reg.getRegionHeight();

        x += containerRect.peek().getX();
        y += containerRect.peek().getY();

        Sprite sp = new Sprite(reg);

        sp.setColors(dp.getColors());
        sp.setAlphas(dp.getAlphas());

        sp.setScale(dp.getScaleX(), dp.getScaleY());
        sp.setPosition(x, y);
        sp.rotate(dp.getRotation());
        sp.flip(dp.isFlipX(), dp.isFlipY());


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
            reg = new TextureRegion(reg, (int) r.getX1(), (int) r.getY1(), (int) r.getWidth(), (int) r.getHeight());

        return reg;
    }

    public static void drawText(Batch batch, int numFont, String text, float x, float y) {
        drawText(batch, numFont, text, x, y, dp);
    }

    public static void drawText(Batch batch, int numFont, String text, float x, float y, DrawParameter dp) {
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

    /**
     * Obtiene el tiempo transcurrido entre dos frames y se multiplica por una constante
     * Se usa para calcular velocidades sin depender de los FPS.
     */
    public static float getDelta() {
        return Gdx.graphics.getDeltaTime() * BASE_SPEED;
    }
}
