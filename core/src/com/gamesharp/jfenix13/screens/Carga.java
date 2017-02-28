package com.gamesharp.jfenix13.screens;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.gamesharp.jfenix13.general.Main;


public class Carga extends Screen {
    ProgressBar pb;
    Label lb1;
    Label lb2;

    public Carga() {
        id = SCR_CARGA;
    }

    @Override
    public void show() {
        super.show();
        pb = new ProgressBar(0, 1, 0.0625f, false, skin);
        pb.setPosition(0, 20);
        pb.setAnimateDuration(0.15f);
        pb.setSize(stage.getWidth(), 40);

        lb1 = new Label("Cargando...", skin);
        lb1.setPosition(pb.getX() + 10, pb.getY() + pb.getHeight() + 20);
        lb1.setFontScale(1.2f);

        lb2 = new Label("0 %", skin);
        lb2.setPosition(pb.getX() + pb.getWidth() - 50, pb.getY() + pb.getHeight() + 20);


        stage.addActor(pb);
        stage.addActor(lb1);
        stage.addActor(lb2);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        float value;

        //System.out.println(pb.getMaxValue());
        lb2.setText((int)(pb.getVisualValue() * 100) + " %");
        lb2.setFontScale(1.2f);

        if (pb.getVisualValue() == pb.getValue()) {
            if (pb.getValue() == pb.getMaxValue()) {
                /* Hacemos finishloading xq al trabajar con la progressbar redondea los valores
                haciendo que muestre que terminó la carga cuando en realizad falta muy poco */
                Main.game.assets.getAM().finishLoading();

                // Cargamos todos los demás assets
                Main.game.assets.loadRemaining();
                Main.game.setScreen(new Principal());
            }
            value = Main.game.assets.loadNextAsset();
            pb.setValue(value);
        }
    }
}
