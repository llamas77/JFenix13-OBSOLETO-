package com.gamesharp.jfenix13.screens;

import com.gamesharp.jfenix13.graphics.Grh;

public class Principal extends Screen {

    private Grh grh;

    public Principal() {
        this.id = SCR_PRINCIPAL;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.getBatch().begin();

        stage.getBatch().end();
    }
}
