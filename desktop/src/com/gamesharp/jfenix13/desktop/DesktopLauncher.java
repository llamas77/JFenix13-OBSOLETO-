package com.gamesharp.jfenix13.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamesharp.jfenix13.general.Main;

import static com.gamesharp.jfenix13.general.Main.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = SCR_WIDTH;
		config.height = SCR_HEIGHT;
		new LwjglApplication(new Main(), config);
	}
}
