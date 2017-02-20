package com.gamesharp.jfenix13.resources.objects;

public class Body {
    private short[] body;
    private short headOffsetX;
    private short headOffsetY;

    public Body() {
        body = new short[4];
    }

    public short getHeadOffsetX() {
        return headOffsetX;
    }

    public void setHeadOffsetX(short headOffsetX) {
        this.headOffsetX = headOffsetX;
    }

    public short getHeadOffsetY() {
        return headOffsetY;
    }

    public void setHeadOffsetY(short headOffsetY) {
        this.headOffsetY = headOffsetY;
    }

    public void setBody(int dir, int index) {
        body[dir] = (short)index;
    }

    public short getBody(int dir) {
        return body[dir];
    }
}
