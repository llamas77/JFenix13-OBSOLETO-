package com.gamesharp.jfenix13.general;

import com.badlogic.gdx.Game;
import com.gamesharp.jfenix13.handlers.AssetsHandler;

public class Main extends Game {
    public static Main game;
	public static int SCR_WIDTH = 1024, SCR_HEIGHT = 768;
	public static int TILE_PIXEL_WIDTH = 32, TILE_PIXEL_HEIGHT = 32;

	public AssetsHandler assets;

	@Override
	public void create () {
        game = this;
		assets = new AssetsHandler();
	}

	@Override
	public void dispose () {
	}
}
