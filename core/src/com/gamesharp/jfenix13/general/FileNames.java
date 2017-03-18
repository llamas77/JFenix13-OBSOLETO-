package com.gamesharp.jfenix13.general;

public class FileNames {
    public static final String DIR_TEXTURAS = "textures";
    public static final String DIR_GUI = "gui";
    public static final String DIR_SHADERS = "shaders";
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

    public static String getAtlasNormTexDir() {
        return getMainTexDir() + "/" + "normal.atlas";
    }

    public static String getAtlasBigTexDir() {
        return getMainTexDir() + "/" + "big.atlas";
    }

    public static String getAtlasFontTexDir() {
        return DIR_TEXTURAS + "/fonts/" + "fuentes.atlas";
    }

    public static String getAtlasGuiDir() {
        return DIR_GUI + "/gui.atlas";
    }

    public static final String getSkinFlat() {
        return DIR_SKINS + "/flat/" + "skin.json";
    }

    public static final String getPassVSH() {
        return DIR_SHADERS + "/" + "pass.vsh";
    }

    public static final String getShadowMapFSH() {
        return DIR_SHADERS + "/" + "shadowMap.fsh";
    }

    public static final String getShadowRenderFSH() {
        return DIR_SHADERS + "/" + "shadowRender.fsh";
    }

    public static final String getShadowCombineFSH() {
        return DIR_SHADERS + "/" + "shadowCombine.fsh";
    }

}
