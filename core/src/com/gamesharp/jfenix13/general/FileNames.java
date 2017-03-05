package com.gamesharp.jfenix13.general;

public class FileNames {
    public static final String DIR_TEXTURAS = "textures";
    public static final String DIR_INITS = "inits";
    public static final String DIR_SKINS = "skins";
    public static final String DIR_ICON = "icon";
    public static final String DIR_MAPS = "maps";

    public static String getIconDir() {
        return DIR_ICON + "/" + "ic_launcher.png";
    }

    public static String getTexIndicesDir() {
        return DIR_INITS + "/" + "graficos.ind";
    }

    public static String getFontsIndicesDir() {
        return DIR_INITS + "/" + "fonts.ind";
    }


    public static String getMainTexDir() {
        return DIR_TEXTURAS + "/main";
    }

    public static String getAtlasTexDir() {
        return getMainTexDir() + "/normal/" + "texturas.atlas";
    }

    public static String getBigTexDir() {
        return getMainTexDir() + "/big";
    }

    public static String getAtlasFontTexDir() {
        return DIR_TEXTURAS + "/fonts/" + "fuentes.atlas";
    }

    public static final String getSkinFlat() {
        return DIR_SKINS + "/flat/" + "skin.json";
    }


}
