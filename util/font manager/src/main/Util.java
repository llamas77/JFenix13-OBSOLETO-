package main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Util {
    public static int bigToLittle_Int(int bigendian)
    {
        ByteBuffer buf = ByteBuffer.allocate(4);

        buf.order(ByteOrder.BIG_ENDIAN);
        buf.putInt(bigendian);

        buf.order(ByteOrder.LITTLE_ENDIAN);
        return buf.getInt(0);
    }

    public static int littleToBig_Int(int littleendian)
    {
        ByteBuffer buf = ByteBuffer.allocate(4);

        buf.order(ByteOrder.LITTLE_ENDIAN);
        buf.putInt(littleendian);

        buf.order(ByteOrder.BIG_ENDIAN);
        return buf.getInt(0);
    }

    public static float bigToLittle_Float(float bigendian)
    {
        ByteBuffer buf = ByteBuffer.allocate(4);

        buf.order(ByteOrder.BIG_ENDIAN);
        buf.putFloat(bigendian);

        buf.order(ByteOrder.LITTLE_ENDIAN);
        return buf.getFloat(0);
    }

    public static short bigToLittle_Short(short bigendian)
    {
        ByteBuffer buf = ByteBuffer.allocate(2);

        buf.order(ByteOrder.BIG_ENDIAN);
        buf.putShort(bigendian);

        buf.order(ByteOrder.LITTLE_ENDIAN);
        return buf.getShort(0);
    }

    public static byte bigToLittle_Byte(byte bigendian)
    {
        ByteBuffer buf = ByteBuffer.allocate(1);

        buf.order(ByteOrder.BIG_ENDIAN);
        buf.put(bigendian);

        buf.order(ByteOrder.LITTLE_ENDIAN);
        return buf.get(0);
    }
}
