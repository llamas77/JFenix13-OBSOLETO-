package com.gamesharp.jfenix13.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.gamesharp.jfenix13.graphics.Drawer;
import com.gamesharp.jfenix13.graphics.Grh;


public class Test extends Screen {
    Label lbFPS;
    Grh g, g2;
    TextButton tbCerrar;

    public Test() {
        this.id = SCR_TEST;
    }

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
                //Gdx.app.exit();
                Gdx.graphics.setTitle("JFenix13 - FPS: " + Gdx.graphics.getFramesPerSecond());
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

        g = new Grh(13021);
        g2 = new Grh(6825);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        lbFPS.setText("FPS: " + Gdx.graphics.getFramesPerSecond());


        stage.getBatch().begin();

        Drawer.drawGrh(stage.getBatch(), g2, 90, 50);

        Drawer.pushScissors(stage, 170, 140, 80, 110);

        Drawer.drawGrh(stage.getBatch(), g2, 150, 150);

        Drawer.popScissors(stage);

        stage.getBatch().end();

    }
}
