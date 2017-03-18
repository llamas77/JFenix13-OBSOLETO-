package com.gamesharp.jfenix13.game_data.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gamesharp.jfenix13.game_data.map.MapTile;
import com.gamesharp.jfenix13.general.Main;
import com.gamesharp.jfenix13.general.Position;
import com.gamesharp.jfenix13.general.Rect;
import com.gamesharp.jfenix13.graphics.DrawData;
import com.gamesharp.jfenix13.graphics.DrawParameter;
import com.gamesharp.jfenix13.graphics.Drawer;
import com.gamesharp.jfenix13.graphics.Light;
import com.gamesharp.jfenix13.handlers.WorldHandler;
import com.gamesharp.jfenix13.listeners.WorldListener;
import com.gamesharp.jfenix13.resources.objects.Map;

import static com.gamesharp.jfenix13.general.Main.*;
import static com.gamesharp.jfenix13.general.FileNames.*;
import static com.gamesharp.jfenix13.graphics.DrawData.*;
import static com.badlogic.gdx.graphics.GL20.*;

/**
 * Mundo donde se muestra y se interactúa con una parte del mapa, personajes, npcs, etc.
 *
 * moving: indica si la pantalla se esta moviendo
 * pos: posición actual del mapa
 * addToPos: vector que indica para que dirección y sentido se esta moviendo
 * offset: cantidad de pixeles desde que arrancó el movimiento hasta un momento en donde se sigue moviendo.
 * mouseTile: indica en que tile del mapa se encuentra el mouse.
 * techo: indica si en la posición actual se está bajo techo.
 * screenTile: rectángulo con las posiciones del mundo que se visualizan (para capas 1 y 2)
 * screenBigTile: lo mismo que screenTile pero más grande (para dibujar incluso donde no se ve: para capa 3, etc).
 * mapa: hace referencia al mapa actual.
 */
public class World extends Actor {
    private boolean moving;
    private Position pos;
    private Position addToPos;
    private Position offset;
    private Position mouseTile;
    private boolean techo;

    private Rect screenTile;
    private Rect screenBigTile;
    private Map mapa;

    private WorldHandler h;

    private ShaderProgram shadowMapSH, shadowRenderSH, shadowCombineSH;
    private FrameBuffer shadowMapFBO, occludersFBO, tercerFBO;
    private Texture shadowMap1D;
    private Texture occluders;
    private TextureRegion tercer;

    // ONLY DEBUG
    private int lightSize;


    public World() {
        setSize(WINDOWS_TILE_WIDTH * TILE_PIXEL_WIDTH, WINDOWS_TILE_HEIGHT * TILE_PIXEL_HEIGHT);

        h = new WorldHandler();
        addListener(new WorldListener(h));

        pos = new Position(50, 50);
        addToPos = new Position();
        offset = new Position();
        mouseTile = new Position();
        screenTile = new Rect();
        screenBigTile = new Rect();
        mapa = Main.game.maps.getMapa();

        ShaderProgram.pedantic = false;
        FileHandle vSH = Gdx.files.internal(getPassVSH());
        shadowMapSH = new ShaderProgram(vSH, Gdx.files.internal(getShadowMapFSH()));
        shadowRenderSH = new ShaderProgram(vSH, Gdx.files.internal(getShadowRenderFSH()));
        shadowCombineSH = new ShaderProgram(vSH, Gdx.files.internal(getShadowCombineFSH()));


        // ONLY DEBUG
        lightSize = 256;

        //build frame buffers
        occludersFBO = new FrameBuffer(Pixmap.Format.RGBA8888, lightSize, lightSize, false);
        occluders = occludersFBO.getColorBufferTexture();

        shadowMapFBO = new FrameBuffer(Pixmap.Format.RGBA8888, lightSize, 1, false);
        shadowMap1D = shadowMapFBO.getColorBufferTexture();

        tercerFBO = new FrameBuffer(Pixmap.Format.RGBA8888, SCR_WIDTH, SCR_HEIGHT, false);
        tercer = new TextureRegion(tercerFBO.getColorBufferTexture());


    }

    public boolean isMoving() {
        return moving;
    }

    public Position getPos() {
        return pos;
    }

    public Map getMapa() {
        return mapa;
    }

    /**
     * Devuelve el rectángulo donde se contiene
     */
    public Rectangle getRect() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Determina si la pantalla se tiene que mover hacia alguna dirección.
     */
    public void setMove(int dir) {
        Position relPos = Position.dirToPos(dir);
        Position absPos = pos.getSuma(relPos);

        if (!mapa.isLegalPos(absPos)) return;

        pos = absPos;
        addToPos = relPos;
        moving = true;

        setTecho();
    }


    /**
     * Si la pantalla se tiene que mover, esto se encarga de ir moviendola.
     * (se ejecuta constantemente)
     */
    public void move() {
        if (moving) {
            if (addToPos.getX() != 0) {
                offset.addX(-SCROLL_PIXELS_PER_FRAME * addToPos.getX() * Drawer.getDelta());
                if (Math.abs(offset.getX()) >= Math.abs(TILE_PIXEL_WIDTH * addToPos.getX())) {
                    offset.setX(0);
                    addToPos.setX(0);
                    moving = false;
                }
            }

            if (addToPos.getY() != 0) {
                offset.addY(-SCROLL_PIXELS_PER_FRAME * addToPos.getY() * Drawer.getDelta());
                if (Math.abs(offset.getY()) >= Math.abs(TILE_PIXEL_HEIGHT * addToPos.getY())) {
                    offset.setY(0);
                    addToPos.setY(0);
                    moving = false;
                }
            }

            setMouseTile(h.pos);
        }
    }

    public void render(Stage stage) {
        int x, y;
        MapTile tile;
        Position tempPos = new Position();
        Position minOffset = new Position();
        Position screen = new Position();

        Array<DrawData> qLights = new Array();
        Array<DrawData> qCapa1y2 = new Array();
        Array<DrawData> qCapa3 = new Array();
        Array<DrawData> qCapa4 = new Array();


        int halfWindowsTileWidth = WINDOWS_TILE_WIDTH / 2;
        int halfWindowsTileHeight = WINDOWS_TILE_HEIGHT / 2;

        screenTile.setX1(pos.getX() - addToPos.getX() - halfWindowsTileWidth);
        screenTile.setY1(pos.getY() - addToPos.getY() - halfWindowsTileHeight);
        screenTile.setX2(pos.getX() - addToPos.getX() + halfWindowsTileWidth);
        screenTile.setY2(pos.getY() - addToPos.getY() + halfWindowsTileHeight);

        screenBigTile.setX1(screenTile.getX1() - TILE_BUFFER_SIZE_X);
        screenBigTile.setY1(screenTile.getY1() - TILE_BUFFER_SIZE_Y);
        screenBigTile.setX2(screenTile.getX2() + TILE_BUFFER_SIZE_X);
        screenBigTile.setY2(screenTile.getY2() + TILE_BUFFER_SIZE_Y);


        // Asegurarse de que screenBigTile está siempre dentro del mapa
        if (screenBigTile.getY1() < mapa.getSize().getY1()) {
            minOffset.setY((int)(mapa.getSize().getY1() - screenBigTile.getY1()));
            screenBigTile.setY1(mapa.getSize().getY1());
        }
        if (screenBigTile.getY2() > mapa.getSize().getY2()) {
            screenBigTile.setY2(mapa.getSize().getY2());
        }
        if (screenBigTile.getX1() < mapa.getSize().getX1()) {
            minOffset.setX((int)(mapa.getSize().getX1() - screenBigTile.getX1()));
            screenBigTile.setX1(mapa.getSize().getX1());
        }
        if (screenBigTile.getX2() > mapa.getSize().getX2()) {
            screenBigTile.setX2(mapa.getSize().getX2());
        }


        // En lo posible agrandar los bordes de renderizado a un tile para cada lado, para que se vea bien al moverse.
        if (screenTile.getY1() > mapa.getSize().getY1()) {
            screenTile.addY1(-1);
            screenTile.addHeight(1);
        }
        else {
            screenTile.setY1(1);
            screen.setY(1);
        }
        if (screenTile.getY2() < mapa.getSize().getY2())
            screenTile.addY2(1);

        if (screenTile.getX1() > mapa.getSize().getX1()) {
            screenTile.addX1(-1);
            screenTile.addWidth(1);
        }
        else {
            screenTile.setX1(1);
            screen.setX(1);
        }
        if (screenTile.getX2() < mapa.getSize().getX2())
            screenTile.addX2(1);


        DrawParameter dpA = new DrawParameter();
        dpA.setAnimated(true);

        DrawParameter dpAC = new DrawParameter();
        dpAC.setAnimated(true);
        dpAC.setCenter(true);


        screen.setX(minOffset.getX() - TILE_BUFFER_SIZE_X);
        screen.setY(minOffset.getY() - TILE_BUFFER_SIZE_Y);

        // Analizar región del mapa
        for (x = (int)screenBigTile.getX1(); x <= (int)screenBigTile.getX2(); x++) {
            tempPos.setX(getX() + screen.getX() * TILE_PIXEL_WIDTH + offset.getX());
            for (y = (int)screenBigTile.getY1(); y <= (int)screenBigTile.getY2(); y++) {
                tempPos.setY(SCR_HEIGHT - getY() - getHeight() + screen.getY() * TILE_PIXEL_HEIGHT + offset.getY());
                tile = mapa.getTile(x - 1, y - 1);

                Position thisPos = new Position(tempPos.getX(), tempPos.getY());

                if (screenTile.isPointIn(x, y)) {
                    qCapa1y2.add(new DrawData(tile, thisPos, T_CAPA1));
                    if (tile.getCapa(1) != null)
                        qCapa1y2.add(new DrawData(tile ,thisPos, T_CAPA2));
                }

                if (tile.getLight() != null)
                    qLights.add(new DrawData(tile, thisPos));

                if (tile.getCapa(2) != null)
                    qCapa3.add(new DrawData(tile, thisPos, T_CAPA3));

                if (!techo) {
                    if (tile.getCapa(3) != null)
                        qCapa4.add(new DrawData(tile, thisPos));
                }

                screen.addY(1);
            }
            screen.addY(-y + screenBigTile.getY1());
            screen.addX(1);
        }


        stage.getBatch().end();

        stage.getBatch().setBlendFunction(GL_SRC_ALPHA, GL_ONE);

        tercerFBO.begin();
        Gdx.gl.glClearColor(0f,0f,0f,0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tercerFBO.end();

        int mx, my;
        OrthographicCamera cam = (OrthographicCamera)stage.getCamera();

        for (DrawData dd : qLights) {

            Light luz = dd.tile.getLight();

            mx = Gdx.input.getX();
            my = SCR_HEIGHT - Gdx.input.getY();

            //PASO 1 (FBO de lo que hace sombra justo sobre UNA parte (rectangulo en donde hay una luz)).
            occludersFBO.begin();
            Gdx.gl.glClearColor(0f,0f,0f,0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            cam.setToOrtho(false, occludersFBO.getWidth(), occludersFBO.getHeight());
            cam.translate(dd.pos.getX() - lightSize/2f, SCR_HEIGHT - dd.pos.getY() - lightSize/2f);
            cam.update();
            stage.getBatch().setProjectionMatrix(cam.combined);

            stage.getBatch().setShader(null);
            stage.getBatch().begin();
                // Dibujar lo que hace sombra
                for (DrawData dd2 : qCapa3) {
                    switch (dd2.tipo) {
                        case T_CAPA3:
                            Drawer.drawGrh(stage.getBatch(), dd2.tile.getCapa(2), dd2.pos.getX(), dd2.pos.getY(), dpAC);
                            break;
                    }
                }
            stage.getBatch().end();
            occludersFBO.end();


            // PASO 2 (FBO con algunos calculos iniciales de UNA luz)
            shadowMapFBO.begin();
            Gdx.gl.glClearColor(0f,0f,0f,0f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            cam.setToOrtho(false, shadowMapFBO.getWidth(), shadowMapFBO.getHeight());
            stage.getBatch().setProjectionMatrix(cam.combined);

            stage.getBatch().setShader(shadowMapSH);
            stage.getBatch().begin();
            shadowMapSH.setUniformf("resolution", lightSize, lightSize);
            stage.getBatch().draw(occluders, 0, 0, lightSize, shadowMapFBO.getHeight());
            stage.getBatch().end();
            shadowMapFBO.end();


            // PASO 3 (FBO que va acumulando todas las luces)
            tercerFBO.begin();

            cam.setToOrtho(false);
            stage.getBatch().setProjectionMatrix(cam.combined);

            stage.getBatch().setShader(shadowRenderSH);
            stage.getBatch().begin();
            shadowRenderSH.setUniformf("resolution", lightSize, lightSize);
            shadowRenderSH.setUniformf("softShadows", true ? 1f : 0f);
            shadowRenderSH.setUniformf("defColor", Drawer.getDefColor());
            shadowRenderSH.setUniformf("intensity", luz.getIntensidad());
            Color batchColor = stage.getBatch().getColor();
            stage.getBatch().setColor(dd.tile.getLight().getColor());
            stage.getBatch().draw(shadowMap1D, dd.pos.getX() -lightSize/2f, SCR_HEIGHT - dd.pos.getY() -lightSize/2f, lightSize, lightSize);
            stage.getBatch().setColor(batchColor);
            stage.getBatch().end();
            tercerFBO.end();
        }

        stage.getBatch().setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        cam.setToOrtho(false);
        stage.getBatch().setProjectionMatrix(cam.combined);

        stage.getBatch().setShader(shadowCombineSH);
        stage.getBatch().begin();
        tercer.getTexture().bind(1);
        shadowCombineSH.setUniformi("u_light", 1);
        shadowCombineSH.setUniformf("resolution", SCR_WIDTH, SCR_HEIGHT);


        Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0);


        for (DrawData dd: qCapa1y2) {
            Drawer.drawGrh(stage.getBatch(), dd.tile.getCapa(dd.tipo), dd.pos.getX(), dd.pos.getY(), dpAC);
        }

        stage.getBatch().end();
        stage.getBatch().setShader(null);
        stage.getBatch().begin();

        /*TextureRegion asd = new TextureRegion(tercer);
        asd.flip(false, true);
        stage.getBatch().draw(asd, 0, 0);
*/

        for (DrawData dd : qCapa3) {
            switch (dd.tipo) {
                case T_CAPA3:
                    Drawer.drawGrh(stage.getBatch(), dd.tile.getCapa(2), dd.pos.getX(), dd.pos.getY(), dpAC);
                    break;
            }
        }


        if (!techo) {
            for (DrawData dd : qCapa4) {
                Drawer.drawGrh(stage.getBatch(), dd.tile.getCapa(3), dd.pos.getX(), dd.pos.getY(), dpAC);
            }
        }
    }


    /**
     * Acciones ejecutadas según lo que pasa
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        if (h.moved) {
            setMouseTile(h.pos);
            h.moved = false;
        }
    }

    /**
     * Activa el flag para ver los techos, según el trigger del tile donde se está.
     */
    public void setTecho() {
        MapTile tile = mapa.getTile((int)pos.getX() - 1, (int)pos.getY() - 1);
        this.techo = tile.getTrigger() == 1 || tile.getTrigger() == 2 || tile.getTrigger() == 4;
    }


    public void setMouseTile(Position pos) {
        mouseTile.setX((int)(this.pos.getX() + pos.getX() / TILE_PIXEL_WIDTH - WINDOWS_TILE_WIDTH / 2));
        mouseTile.setY((int)(this.pos.getY() + (getHeight() - pos.getY()) / TILE_PIXEL_HEIGHT - WINDOWS_TILE_HEIGHT / 2));
    }
}
