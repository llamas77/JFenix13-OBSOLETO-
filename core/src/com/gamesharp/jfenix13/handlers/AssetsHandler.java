package com.gamesharp.jfenix13.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gamesharp.jfenix13.general.Main;
import com.gamesharp.jfenix13.resources.containers.*;
import com.gamesharp.jfenix13.resources.objects.Map;

import static com.gamesharp.jfenix13.general.FileNames.*;

/**
 * Contiene todos los objetos cargados desde los assets
 * am: para assets que libGDX sabe cargar (atlas, texturas, musica, sonidos, etc)
 * los demas corresponden a archivos de datos propios del juego.
 */

public class AssetsHandler {
    private AssetManager am;

    private Grhs grhs;
    private Fonts fonts;

    public AssetsHandler() {
        am = new AssetManager();
        preloadAM();
        am.finishLoadingAsset(getAtlasGuiDir());
    }

    public AssetManager getAM() {
        return am;
    }

    /**
     * Precarga los assets
     */
    private void preloadAM()
    {

        // Skin de la GUI
        am.load(getSkinFlat(), Skin.class);

        // Atlas de la GUI del juego
        am.load(getAtlasGuiDir(), TextureAtlas.class);

        // Atlas de texturas
        am.load(getAtlasTexDir(), TextureAtlas.class);

        // Texturas grandes que no entraron en el atlas
        FileHandle[] files = Gdx.files.internal(getBigTexDir()).list();
        for (FileHandle f : files) {
            if (f.extension().toLowerCase().equals("png")) {
                am.load(getBigTexDir() + "/" + f.name(), Texture.class);
            }
        }

        am.load(getAtlasFontTexDir(), TextureAtlas.class);
    }


    /**
     * Carga al resto de los assets que sabe cargar libGDX, devuelve
     * @return: proporcion que representa el progreso
     */
    public float loadNextAsset() {
        am.update();
        return am.getProgress();
    }

    /**
     * Termina de cargar el resto de assets propios del juego
     */
    public void loadRemaining() {
        grhs = new Grhs();
        fonts = new Fonts();
        Main.game.maps = new MapsHandler();
    }

    public Grhs getGrhs() {
        return grhs;
    }

    public Fonts getFonts() {
        return fonts;
    }
}
