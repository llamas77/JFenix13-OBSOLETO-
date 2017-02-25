package com.gamesharp.jfenix13.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamesharp.jfenix13.general.Main;
import static com.gamesharp.jfenix13.general.FileNames.*;
import com.badlogic.gdx.Files.FileType;

import static com.gamesharp.jfenix13.general.Main.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = false;
		config.width = SCR_WIDTH;
		config.height = SCR_HEIGHT;
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;
		config.addIcon(getIconDir(), FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}
