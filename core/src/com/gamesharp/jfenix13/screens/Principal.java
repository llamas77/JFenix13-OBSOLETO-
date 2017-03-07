package com.gamesharp.jfenix13.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.gamesharp.jfenix13.game_data.general.UserStats;
import com.gamesharp.jfenix13.general.Main;
import com.gamesharp.jfenix13.general.Position;
import com.gamesharp.jfenix13.general.Rect;
import com.gamesharp.jfenix13.graphics.DrawParameter;
import com.gamesharp.jfenix13.graphics.Drawer;
import com.gamesharp.jfenix13.resources.objects.Map;

import static com.gamesharp.jfenix13.general.Direccion.*;

import static com.gamesharp.jfenix13.general.Main.*;

/**
 * Pantalla principal del juego
 *
 * mainRect: rectángulo que hace referencia a la zona de juego (donde se ve el mapa y los personajes, etc).
 * screenTile: indica los tiles del mapa correspondientes a los límites de la pantalla
 * screenBufTile: indica los límites de dibujado según los tiles de buffer
 */
public class Principal extends Screen {

    private Touchpad tp;
    private SelectBox<String> sb;
    private TextButton tb;

    private Rectangle mainRect;
    private Rect screenTile;
    private Rect screenBigTile;
    private UserStats userStats;
    private Map mapa;

    public Principal() {
        this.id = SCR_PRINCIPAL;
    }

    @Override
    public void show() {
        super.show();

        mainRect = new Rectangle(150, 3,
                                 WINDOWS_TILE_WIDTH * TILE_PIXEL_WIDTH, WINDOWS_TILE_HEIGHT * TILE_PIXEL_HEIGHT);
        screenTile = new Rect();
        screenBigTile = new Rect();
        userStats = Main.game.userStats;
        mapa = Main.game.maps.getMapa();

        tp = new Touchpad(15, skin);
        tp.setPosition(20, 50);
        tp.setSize(100, 100);
        stage.addActor(tp);

        sb = new SelectBox(skin);

        Array<String> asd = new Array();
        for (int i = 1; i <= 204; i++) {
            asd.add(Integer.toString(i));
        }

        sb.setPosition(20, 300);
        sb.setSize(70, 20);

        sb.setItems(asd);
        stage.addActor(sb);

        tb = new TextButton("Cargar", skin);
        tb.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mapa.load(sb.getSelectedIndex() + 1);

                return super.touchDown(event, x, y, pointer, button);
            }
        });

        tb.setPosition(20, 250);
        tb.setSize(100, 30);

        stage.addActor(tb);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.getBatch().begin();

        Drawer.drawText(stage.getBatch(), 3, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 10);
        Drawer.drawText(stage.getBatch(), 3, "(" + (int)userStats.getPos().getX() + ", " + (int)userStats.getPos().getY() + ")", 10, 30);

        Drawer.pushScissors(stage, mainRect);

        if (!userStats.isMoving()) {
            if (tp.getKnobPercentX() > 0f || tp.getKnobPercentY() > 0f) {
                if (tp.getKnobPercentX() > 0.7) {
                    setMoveWorld(ESTE);
                }

                else if (tp.getKnobPercentY() > 0.7) {
                    setMoveWorld(NORTE);
                }

                else if (tp.getKnobPercentX() < -0.7) {
                    setMoveWorld(OESTE);
                }

                else if (tp.getKnobPercentY() < -0.7) {
                    setMoveWorld(SUR);
                }
            }
        }

        moveWorld();


        renderWorld();

        Drawer.popScissors(stage);


        stage.getBatch().end();
    }


    /**
     * Determina si la pantalla se tiene que mover hacia alguna dirección.
     */
    public void setMoveWorld(int dir) {
        Position relPos = new Position();
        Position absPos = new Position();

        switch (dir) {
            case NORTE:
                relPos.setY(-1);
                break;
            case ESTE:
                relPos.setX(1);
                break;
            case SUR:
                relPos.setY(1);
                break;
            case OESTE:
                relPos.setX(-1);
                break;
        }

        absPos.setX(userStats.getPos().getX() + relPos.getX());
        absPos.setY(userStats.getPos().getY() + relPos.getY());

        if (!mapa.getBorderRect().isPointIn(absPos)) return;

        userStats.setPos(absPos);
        userStats.setAddToPos(relPos);
        userStats.setMoving(true);
    }


    /**
     * Si la pantalla se tiene que mover, esto se encarga de ir moviendola.
     * (se ejecuta constantemente)
     */
    public void moveWorld() {
        if (userStats.isMoving()) {

            if (userStats.getAddToPos().getX() != 0) {
                userStats.getOC().addX(-SCROLL_PIXELS_PER_FRAME * userStats.getAddToPos().getX() * Drawer.getDelta());
                if (Math.abs(userStats.getOC().getX()) >= Math.abs(TILE_PIXEL_WIDTH * userStats.getAddToPos().getX())) {
                    userStats.getOC().setX(0);
                    userStats.getAddToPos().setX(0);
                    userStats.setMoving(false);
                }
            }

            if (userStats.getAddToPos().getY() != 0) {
                userStats.getOC().addY(-SCROLL_PIXELS_PER_FRAME * userStats.getAddToPos().getY() * Drawer.getDelta());
                if (Math.abs(userStats.getOC().getY()) >= Math.abs(TILE_PIXEL_WIDTH * userStats.getAddToPos().getY())) {
                    userStats.getOC().setY(0);
                    userStats.getAddToPos().setY(0);
                    userStats.setMoving(false);
                }
            }
        }
    }

    public void renderWorld() {
        int x;
        int y;
        Position tempPos = new Position();
        Position minOffset = new Position();
        Position screen = new Position();

        int halfWindowsTileWidth = WINDOWS_TILE_WIDTH / 2;
        int halfWindowsTileHeight = WINDOWS_TILE_HEIGHT / 2;

        screenTile.setX1(userStats.getPos().getX() - userStats.getAddToPos().getX() - halfWindowsTileWidth);
        screenTile.setY1(userStats.getPos().getY() - userStats.getAddToPos().getY() - halfWindowsTileHeight);
        screenTile.setX2(userStats.getPos().getX() - userStats.getAddToPos().getX() + halfWindowsTileWidth);
        screenTile.setY2(userStats.getPos().getY() - userStats.getAddToPos().getY() + halfWindowsTileHeight);

        screenBigTile.setX1(screenTile.getX1() - TILE_BUFFER_SIZE);
        screenBigTile.setY1(screenTile.getY1() - TILE_BUFFER_SIZE);
        screenBigTile.setX2(screenTile.getX2() + TILE_BUFFER_SIZE);
        screenBigTile.setY2(screenTile.getY2() + TILE_BUFFER_SIZE);


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

        screen.addX(-1);
        screen.addY(-1);


        DrawParameter dpCapa1 = new DrawParameter();
        dpCapa1.setAnimated(true);

        DrawParameter dpCapa23 = new DrawParameter();
        dpCapa23.setAnimated(true);
        dpCapa23.setCenter(true);

        for (x = (int)screenTile.getX1(); x <= (int)screenTile.getX2(); x++) {
            tempPos.setX(screen.getX() * TILE_PIXEL_WIDTH + userStats.getOC().getX());
            for (y = (int)screenTile.getY1(); y <= (int)screenTile.getY2(); y++) {
                tempPos.setY(screen.getY() * TILE_PIXEL_HEIGHT + userStats.getOC().getY());

                Drawer.drawGrh(stage.getBatch(), mapa.getTile(x - 1, y - 1).getCapa(0), tempPos.getX(), tempPos.getY(), dpCapa1);
                if (mapa.getTile(x - 1, y - 1).getCapa(1) != null)
                    Drawer.drawGrh(stage.getBatch(), mapa.getTile(x - 1, y - 1).getCapa(1), tempPos.getX(), tempPos.getY(), dpCapa23);

                screen.addY(1);
            }

            screen.addY(-y + screenTile.getY1());
            screen.addX(1);
        }


        screen.setX(minOffset.getX() - TILE_BUFFER_SIZE);
        screen.setY(minOffset.getY() - TILE_BUFFER_SIZE);



        for (x = (int)screenBigTile.getX1(); x <= (int)screenBigTile.getX2(); x++) {
            tempPos.setX(screen.getX() * TILE_PIXEL_WIDTH + userStats.getOC().getX());
            for (y = (int)screenBigTile.getY1(); y <= (int)screenBigTile.getY2(); y++) {
                tempPos.setY(screen.getY() * TILE_PIXEL_HEIGHT + userStats.getOC().getY());

                if (mapa.getTile(x - 1, y - 1).getCapa(2) != null)
                    Drawer.drawGrh(stage.getBatch(), mapa.getTile(x - 1, y - 1).getCapa(2), tempPos.getX(), tempPos.getY(), dpCapa23);

                screen.addY(1);

            }
            screen.addY(-y + screenBigTile.getY1());
            screen.addX(1);
        }

    }

}
