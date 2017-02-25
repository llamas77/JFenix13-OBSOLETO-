package com.gamesharp.jfenix13.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.gamesharp.jfenix13.graphics.DrawParameter;
import com.gamesharp.jfenix13.graphics.Drawer;
import com.gamesharp.jfenix13.graphics.Grh;


public class Principal extends Screen {
    Label lbFPS;
    Grh g;
    TextButton tbCerrar;


    @Override
    public void show() {
        super.show();

        tbCerrar = new TextButton("X", skin);
        tbCerrar.setPosition(stage.getWidth() - 30, stage.getHeight() - 30);
        tbCerrar.setWidth(20);
        tbCerrar.setHeight(20);
        tbCerrar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        lbFPS = new Label("FPS: 0", skin);
        lbFPS.setPosition(30, stage.getHeight() - 30);
        //Prueba p = new Prueba();
        lbFPS.setFontScale(1.2f);

        TextArea tf = new TextArea("", skin);
        tf.setPosition(50, 200);
        tf.setWidth(200);
        tf.setHeight(500);
        //stage.addActor(tf);
        stage.addActor(tbCerrar);
        stage.addActor(lbFPS);
        //stage.addActor(p);

        Window w = new Window("Comerciar", skin);
        w.setPosition(300, 200);
        w.setResizable(true);
        w.setMovable(true);
        w.setWidth(400);
        w.setHeight(200);

        CheckBox cb = new CheckBox("Activar pantalla completa", skin);
        //w.addActor(cb);

        SelectBox<String> sb = new SelectBox(skin);
        sb.setItems("Mago", "Arquero", "Clérigo", "Asesino", "Ñoqui", "Pe?{E¿'0", "ASD", "Cat}an");
        sb.setWidth(300);



        w.addActor(sb);



        /*Touchpad tp = new Touchpad(10, skin);
        tp.setBounds(15, 15, 200, 200);
        tp.setPosition(50, 50);
        stage.addActor(tp);
        stage.addActor(w);
*/

        g = new Grh((short)13021);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        lbFPS.setText("FPS: " + Gdx.graphics.getFramesPerSecond());

        stage.getBatch().begin();


        DrawParameter dp = new DrawParameter();
        dp.setAnimated(true);

        dp.setColor(255, 255, 255);

        dp.setVertColor(0, 255, 0, 0);
        dp.setVertColor(1, 255, 255, 0);
        dp.setVertColor(2, 0, 255, 0);
        dp.setVertColor(3, 0, 0, 255);

        dp.setVertAlpha(0, 50);
        dp.setVertAlpha(3, 50);


        dp.setScale(4);

        /*dp.setVertAlpha(0, 10);
        dp.setVertAlpha(2, 10);*/

        dp.setCenter(false);
        dp.setFlipX(true);
        //dp.setFlip(true);
        dp.setRotation(60);
        Drawer.drawGrh(stage.getBatch(), g, 300, 300, dp);


        stage.getBatch().end();

    }
}
