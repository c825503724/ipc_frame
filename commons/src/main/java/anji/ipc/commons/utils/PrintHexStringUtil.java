package anji.ipc.commons.utils;

import io.netty.buffer.ByteBuf;

public class PrintHexStringUtil {


    public static String ss(ByteBuf vv) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int a = vv.readerIndex(); a < vv.readableBytes(); ++a) {
            stringBuilder.append(Integer.toHexString(vv.getUnsignedByte(a))).append(" ");
        }
        return stringBuilder.toString();
    }
}
