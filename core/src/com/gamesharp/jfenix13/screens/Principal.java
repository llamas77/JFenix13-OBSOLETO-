package com.gamesharp.jfenix13.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.gamesharp.jfenix13.game_data.general.World;
import com.gamesharp.jfenix13.graphics.Drawer;

import static com.gamesharp.jfenix13.general.Direccion.*;
import static com.badlogic.gdx.Input.Keys.*;

import static com.gamesharp.jfenix13.general.Main.*;

/**
 * Pantalla principal del juego
 *
 * world: el rectángulo principal donde se muestra el mundo con los personajes, npcs, etc.
 */
public class Principal extends Screen {

    private TextButton tbCerrar;
    private SelectBox<String> sbMapas;
    private TextButton tbCargar;

    private World world;

    public Principal() {
        super(SCR_PRINCIPAL, "principal");
    }

    @Override
    public void show() {
        super.show();

        // Boton para cerrar el juego
        tbCerrar = new TextButton("X", skin);
        tbCerrar.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        tbCerrar.setPosition(983, 730);
        tbCerrar.setSize(25, 22);
        stage.addActor(tbCerrar);


        // SelectBox para seleccionar un mapa
        sbMapas = new SelectBox(skin);
        Array<String> items = new Array();
        for (int i = 1; i <= 204; i++) {
            items.add(Integer.toString(i));
        }
        sbMapas.setPosition(660, 721);
        sbMapas.setSize(70, 20);
        sbMapas.setItems(items);
        stage.addActor(sbMapas);


        // Boton para cargar un mapa
        tbCargar = new TextButton("Cargar", skin);
        tbCargar.addListener(new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                world.getMapa().load(sbMapas.getSelectedIndex() + 1);

                return super.touchDown(event, x, y, pointer, button);
            }
        });
        tbCargar.setPosition(631, 676);
        tbCargar.setSize(100, 30);
        stage.addActor(tbCargar);


        world = new World();
        world.setPosition(15, 74);
        stage.addActor(world);
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        stage.getBatch().begin();
            Drawer.pushScissors(stage, world.getRect());

                checkKeys();
                world.move();
                world.render(stage);

            Drawer.popScissors(stage);
        stage.getBatch().end();

        stage.draw();

        stage.getBatch().begin();
            Drawer.drawText(stage.getBatch(), 1, "" + Gdx.graphics.getFramesPerSecond(), 662, 114);
            Drawer.drawText(stage.getBatch(), 2, "X: " + (int) world.getPos().getX() +
                            "  -  Y: " + (int) world.getPos().getY(), 560, 725);
        stage.getBatch().end();

    }


    /**
     * Mueve la pantalla según la entrada del teclado
     */
    public void checkKeys() {
        if (!world.isMoving()) {
            if (Gdx.input.isKeyPressed(UP)) {
                world.setMove(NORTE);
            } else if (Gdx.input.isKeyPressed(RIGHT)) {
                world.setMove(ESTE);
            } else if (Gdx.input.isKeyPressed(DOWN)) {
                world.setMove(SUR);
            } else if (Gdx.input.isKeyPressed(LEFT)) {
                world.setMove(OESTE);
            }
        }
    }

}
