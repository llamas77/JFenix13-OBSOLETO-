package com.gamesharp.jfenix13.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gamesharp.jfenix13.general.Main;

import static com.gamesharp.jfenix13.general.FileNames.*;
import static com.gamesharp.jfenix13.general.Main.*;

public class Screen implements com.badlogic.gdx.Screen {
    public static final byte SCREENS = 2;

    public static final byte SCR_CARGA = 0;
    public static final byte SCR_PRINCIPAL = 1;

    public static Screen[] screens;

    static {
        screens = new Screen[SCREENS];
    }

    protected byte id;
    protected static Skin skin;

    protected Stage stage;
    protected float width;
    protected float height;


    public Screen() {

    }

    @Override
    public void show() {
        skin = Main.game.assets.getAM().get(getSkinLML(), Skin.class);
        stage = new Stage(new FitViewport(SCR_WIDTH, SCR_HEIGHT));
        width = stage.getWidth();
        height = stage.getHeight();
        Gdx.input.setInputProcessor(stage);
        screens[id] = this;
        //stage.setDebugAll(true);

    }

    @Override
    public void render(float delta) {
        stage.act();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }
}
