package com.gamesharp.jfenix13.general;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.gamesharp.jfenix13.handlers.AssetsHandler;
import com.gamesharp.jfenix13.screens.Carga;

public class Main extends Game {
    public static Main game;
	public static final int SCR_WIDTH = 800, SCR_HEIGHT = 600;
	public static final int TILE_PIXEL_WIDTH = 32, TILE_PIXEL_HEIGHT = 32;

	public AssetsHandler assets;

	@Override
	public void create () {
		game = this;

		Gdx.graphics.setTitle("JFenix13");
		assets = new AssetsHandler();
		setScreen(new Carga());
	}

	@Override
	public void dispose () {
	}
}
