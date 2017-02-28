package com.gamesharp.jfenix13.resources.objects;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gamesharp.jfenix13.general.Rect;
import com.gamesharp.jfenix13.graphics.Drawer;

import static com.gamesharp.jfenix13.graphics.Drawer.*;

public class Char extends Rect {
    private TextureRegion tr;

    public Char() {
        tr = null;
    }

    public TextureRegion getTR() {
        return tr;
    }

    public void updateTR(int index) {
        tr = Drawer.getTextureRegion(FUENTE, index, this);
    }
}
