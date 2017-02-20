package com.gamesharp.jfenix13.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gamesharp.jfenix13.resources.containers.*;

import static com.gamesharp.jfenix13.general.FileNames.*;

/**
 * Contiene todos los objetos cargados desde los assets
 * am: para assets que libGDX sabe cargar (atlas, texturas, musica, sonidos, etc)
 * los demas corresponden a archivos de datos propios del juego.
 */
public class AssetsHandler {
    private AssetManager am;

    private Grhs grhs;

    public AssetsHandler() {
        am = new AssetManager();
        preloadAM();
        am.finishLoadingAsset(getSkinLML());
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
        am.load(getSkinLML(), Skin.class);

        // Atlas de texturas
        am.load(getAtlasTexDir(), TextureAtlas.class);

        // Texturas grandes que no entraron en el atlas
        FileHandle[] files = Gdx.files.internal(DIR_BIGTEXTURAS).list();
        for (FileHandle f : files) {
            if (f.extension().toLowerCase().equals("png")) {
                am.load(DIR_BIGTEXTURAS + "/" + f.name(), Texture.class);
            }
        }
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
    }

    public Grhs getGrhs() {
        return grhs;
    }
}
