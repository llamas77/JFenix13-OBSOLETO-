package com.gamesharp.jfenix13.resources.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gamesharp.jfenix13.resources.objects.Grh;
import java.io.DataInputStream;
import java.io.IOException;
import static com.gamesharp.jfenix13.general.FileNames.*;
import static com.gamesharp.jfenix13.general.Util.*;

public class Grhs {
    private Grh[] grhs;

    public Grhs() {
        try {
            load();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Carga todos los GRHs
     * @throws IOException
     */
    private void load() throws IOException {
        int index;
        int cantFrames;
        short frame;
        Grh grh;

        FileHandle fh = Gdx.files.internal(getTexIndicesDir());
        DataInputStream dis = new DataInputStream(fh.read());

        dis.skipBytes(4);
        grhs = new Grh[leReadInt(dis)];

        while (dis.available() > 0) {
            index = leReadInt(dis);

            grh = new Grh();
            setGrh(index, grh);
            cantFrames = leReadShort(dis);

            if (cantFrames == 1) {
                if (index > 0 && index <= grhs.length)
                    grh.addFrame((short)index);
                grh.setFileNum(leReadInt(dis));
                grh.getRect().setLeft(leReadShort(dis));
                grh.getRect().setTop(leReadShort(dis));
                grh.getRect().setWidth(leReadShort(dis));
                grh.getRect().setHeight(leReadShort(dis));
            }
            else {
                for (int i = 0; i < cantFrames; i++) {
                    frame = leReadShort(dis);
                    if (frame > 0 && frame <= grhs.length)
                        grh.addFrame(frame);
                }
                grh.setSpeed(leReadFloat(dis));
                grh.getRect().setWidth(getGrh(grh.getFrame((short)0)).getRect().getWidth());
                grh.getRect().setHeight(getGrh(grh.getFrame((short)0)).getRect().getHeight());
            }
        }
        dis.close();
    }

    public Grh getGrh(int index) {
        return grhs[index - 1];
    }

    public void setGrh(int index, Grh grh) {
        grhs[index - 1] = grh;
    }
}
