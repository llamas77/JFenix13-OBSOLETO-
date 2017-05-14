package com.gamesharp.jfenix13.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamesharp.jfenix13.general.Main;
import static com.gamesharp.jfenix13.general.FileNames.*;
import com.badlogic.gdx.Files.FileType;

import static com.gamesharp.jfenix13.general.Main.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = false;
		config.width = SCR_WIDTH;
		config.resizable = false;
		config.height = SCR_HEIGHT;
		config.addIcon(getIconDir(), FileType.Internal);

		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;



		new LwjglApplication(new Main(), config);
	}
}
