package com.gamesharp.jfenix13.general;

import static com.gamesharp.jfenix13.general.Main.*;

public class Rect {
    private float left, top;
    private float width, height;

    /**
     * Verifica si un punto está dentro del rectángulo principal
     * @param x: coordenada en X
     * @param y: coordenada en Y
     * @return
     */
    public boolean isPointIn(float x, float y) {
        return x >= left && x <= getRight() && y >= top && y <= getBottom();
    }


    /**
     * Verifica si al menos una parte de un rectángulo está en el rectángulo principal.
     * @param rect: rectángulo a comparar
     * @return
     */
    public boolean isRectIn(Rect rect) {
        return isRectIn(rect.getLeft(), rect.getTop(), rect.getWidth(), rect.getHeight());
    }

    /**
     * Verifica si al menos una parte de un rectángulo está en el rectángulo principal.
     * @param x1: coordenada en X
     * @param y1: coordenada en Y
     * @param width: ancho
     * @param height: alto
     * @return
     */
    public boolean isRectIn(float x1, float y1, float width, float height) {
        float x2 = x1 + width;
        float y2 = y1 + height;
        return isPointIn(x1, y1) || isPointIn(x2, y2) || ((x1 < left || y1 < top) && (x2 > getRight() || y2 > getBottom()));
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getBottom() {
        return getTop() + getHeight();
    }

    public void setBottom(float bottom) {
        setHeight(bottom - getTop());
    }

    public float getRight() {
        return getLeft() + getWidth();
    }

    public void setRight(float right) {
        setWidth(right - getLeft());
    }

    public float getTileWidth() {
        return getWidth() / TILE_PIXEL_WIDTH;
    }

    public float getTileHeight() {
        return getHeight() / TILE_PIXEL_HEIGHT;
    }
}