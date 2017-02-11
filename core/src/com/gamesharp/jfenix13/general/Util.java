package com.gamesharp.jfenix13.general;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import static java.nio.ByteOrder.*;

public class Util {

    public static short leReadShort(DataInputStream dis) throws IOException {
        return leShort(dis.readShort());
    }

    public static int leReadInt(DataInputStream dis) throws IOException {
        return leInt(dis.readInt());
    }

    public static float leReadFloat(DataInputStream dis) throws IOException {
        return leFloat(dis.readFloat());
    }

    public static byte leReadByte(DataInputStream dis) throws IOException {
        return leByte(dis.readByte());
    }

    public static short leShort(short n) {
        ByteBuffer buf = ByteBuffer.allocate(2);
        buf.order(BIG_ENDIAN);
        buf.putShort(n);
        buf.order(LITTLE_ENDIAN);

        return buf.getShort(0);
    }

    public static int leInt(int n) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(BIG_ENDIAN);
        buf.putInt(n);
        buf.order(LITTLE_ENDIAN);

        return buf.getInt(0);
    }

    public static int leFloat(float n) {

        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.order(BIG_ENDIAN);
        buf.putFloat(n);
        buf.order(LITTLE_ENDIAN);

        return buf.getInt(0);
    }

    public static byte leByte(byte n) {
        ByteBuffer buf = ByteBuffer.allocate(1);
        buf.order(BIG_ENDIAN);
        buf.put(n);
        buf.order(LITTLE_ENDIAN);

        return buf.get(0);
    }
}
