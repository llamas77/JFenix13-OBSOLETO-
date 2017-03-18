package com.gamesharp.jfenix13.general;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.gamesharp.jfenix13.handlers.AssetsHandler;
import com.gamesharp.jfenix13.handlers.MapsHandler;
import com.gamesharp.jfenix13.screens.Carga;

public class Main extends Game {
    public static Main game;
	public static final int SCR_WIDTH = 1024, SCR_HEIGHT = 768;
	public static final int WINDOWS_TILE_WIDTH = 23, WINDOWS_TILE_HEIGHT = 17;
	public static final int TILE_PIXEL_WIDTH = 32, TILE_PIXEL_HEIGHT = 32;
	public static final int TILE_BUFFER_SIZE_X = 9, TILE_BUFFER_SIZE_Y = 9;
	public static final int SCROLL_PIXELS_PER_FRAME = 8;
	public static final int BASE_SPEED = 17;

	public AssetsHandler assets;
	public MapsHandler maps;

	@Override
	public void create () {
		game = this;
		Gdx.graphics.setTitle("JFenix13");

		assets = new AssetsHandler();

		setScreen(new Carga());
	}

	@Override
	public void dispose () {
		assets.getAM().dispose();
	}
}
