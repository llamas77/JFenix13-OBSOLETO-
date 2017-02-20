package com.gamesharp.jfenix13.general;

public class FileNames {
    public static final String DIR_TEXTURAS = "texturas";
    public static final String DIR_BIGTEXTURAS = "bigtexturas";
    public static final String DIR_INITS = "inits";
    public static final String DIR_SKINS = "skins";
    public static final String DIR_ICON = "icon";

    public static String getTexIndicesDir() {
        return DIR_INITS + "/" + "graficos.ind";
    }

    public static String getAtlasTexDir() {
        return DIR_TEXTURAS + "/" + "texturas.atlas";
    }

    public static String getSkinVisDir() {
        return DIR_SKINS + "/visui/" + "uiskin.json";
    }

    public static final String getSkinLML() {
        //return DIR_SKINS + "/lml/" + "skin.json";
        return DIR_SKINS + "/flat/" + "skin.json";
    }

    public static String getIconDir() {
        return DIR_ICON + "/" + "ic_launcher.png";
    }
}
