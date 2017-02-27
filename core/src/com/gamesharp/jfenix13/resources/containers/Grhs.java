package com.gamesharp.jfenix13.resources.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gamesharp.jfenix13.resources.objects.GrhData;

import java.io.DataInputStream;
import java.io.IOException;
import static com.gamesharp.jfenix13.general.FileNames.*;
import static com.gamesharp.jfenix13.general.Util.*;

public class Grhs {
    private GrhData[] grhsData;

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
        GrhData grhData;

        FileHandle fh = Gdx.files.internal(getTexIndicesDir());
        DataInputStream dis = new DataInputStream(fh.read());

        dis.skipBytes(4);
        grhsData = new GrhData[leReadInt(dis)];

        while (dis.available() > 0) {
            index = leReadInt(dis);

            grhData = new GrhData();
            setGrhData(index, grhData);
            cantFrames = leReadShort(dis);

            if (cantFrames == 1) {
                if (index > 0 && index <= grhsData.length)
                    grhData.addFrame((short)index);
                grhData.setFileNum(leReadInt(dis));
                grhData.getRect().setLeft(leReadShort(dis));
                grhData.getRect().setTop(leReadShort(dis));
                grhData.getRect().setWidth(leReadShort(dis));
                grhData.getRect().setHeight(leReadShort(dis));
                grhData.updateTR();
            }
            else {
                for (int i = 0; i < cantFrames; i++) {
                    frame = leReadShort(dis);
                    if (frame > 0 && frame <= grhsData.length)
                        grhData.addFrame(frame);
                }
                grhData.setSpeed(leReadFloat(dis) / 550);
                grhData.getRect().setWidth(getGrhData(grhData.getFrame((short)0)).getRect().getWidth());
                grhData.getRect().setHeight(getGrhData(grhData.getFrame((short)0)).getRect().getHeight());
            }
        }
        dis.close();
    }

    public GrhData getGrhData(int index) {
        return grhsData[index - 1];
    }

    public void setGrhData(int index, GrhData grhData) {
        grhsData[index - 1] = grhData;
    }

}
