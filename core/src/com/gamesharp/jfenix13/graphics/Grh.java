package com.gamesharp.jfenix13.graphics;

import com.gamesharp.jfenix13.general.Main;
import com.gamesharp.jfenix13.resources.objects.GrhData;

public class Grh {
    public static final int INF_LOOPS = -1;

    private short index;
    private float frame;
    private float speed;
    private byte started;
    private short loops;

    public Grh(short index) {
        this(index, 2);
    }

    public Grh(short index, int started) {
        GrhData grhData = Main.game.assets.getGrhs().getGrhData(index);
        if (index == 0) return;
        this.index = index;

        if (started == 2) {
            if (grhData.getCantFrames() > 1)
                this.started = 1;
            else
                this.started = 0;
        }
        else {
            if (grhData.getCantFrames() == 1)
                this.started = 0;
        }

        if (this.started == 1)
            this.loops = INF_LOOPS;
        else
            this.loops = 0;

        this.frame = 1;
        this.speed = grhData.getSpeed();
    }

    public short getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }

    public float getFrame() {
        return frame;
    }

    public void setFrame(float frame) {
        this.frame = frame;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public byte getStarted() {
        return started;
    }

    public void setStarted(byte started) {
        this.started = started;
    }

    public short getLoops() {
        return loops;
    }

    public void setLoops(short loops) {
        this.loops = loops;
    }
}