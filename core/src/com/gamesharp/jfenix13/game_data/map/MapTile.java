package com.gamesharp.jfenix13.game_data.map;

import com.gamesharp.jfenix13.graphics.Grh;
import com.gamesharp.jfenix13.graphics.Light;

/**
 * Características de 1 tile del mapa.
 *
 *
 */
public class MapTile {
    private Grh[] capas;
    private int charIndex;
    private int npcIndex;
    private Objeto objeto;

    private boolean blocked;
    private int trigger;
    private Light light;
    private int particula;

    public MapTile() {
        capas = new Grh[4];
        objeto = null;
        light = null;
    }

    public Grh[] getCapas() {
        return capas;
    }

    public void setCapas(Grh[] capa) {
        this.capas = capa;
    }

    public Grh getCapa(int index) {
        return capas[index];
    }

    public void setCapa(int index, Grh grh) {
        this.capas[index] = grh;
    }

    public int getCharIndex() {
        return charIndex;
    }

    public void setCharIndex(int charIndex) {
        this.charIndex = charIndex;
    }

    public int getNpcIndex() {
        return npcIndex;
    }

    public void setNpcIndex(int npcIndex) {
        this.npcIndex = npcIndex;
    }

    public Objeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Objeto objeto) {
        this.objeto = objeto;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getTrigger() {
        return trigger;
    }

    public void setTrigger(int trigger) {
        this.trigger = trigger;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public int getParticula() {
        return particula;
    }

    public void setParticula(int particula) {
        this.particula = particula;
    }
}
