package com.gamesharp.jfenix13.game_data.general;

import com.gamesharp.jfenix13.general.Position;

/**
 * Se refiere a las características del personaje actual (con el que interactuamos)
 *
 *
 */
public class UserStats {
    private int mapaActual;
    private boolean moving;
    private Position pos;
    private Position addToPos;
    private Position offsetCounter;

    public UserStats() {
        pos = new Position(50, 50);
        addToPos = new Position();
        offsetCounter = new Position();
    }

    public int getMapaActual() {
        return mapaActual;
    }

    public void setMapaActual(int mapaActual) {
        this.mapaActual = mapaActual;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Position getAddToPos() {
        return addToPos;
    }

    public void setAddToPos(Position addToPos) {
        this.addToPos = addToPos;
    }

    public Position getOC() {
        return offsetCounter;
    }

    public void setOC(Position offsetCounter) {
        this.offsetCounter = offsetCounter;
    }
}
