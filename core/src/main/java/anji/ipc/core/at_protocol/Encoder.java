package anji.ipc.core.at_protocol;

import anji.ipc.commons.codec.PropertyBytesInfo;
import anji.ipc.core.at_protocol.type.UnsignedShort;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.List;

public interface Encoder {

    int length();

    List<Field> getRandFields();


    default int defaultLength() {
        return getRandFields().stream().mapToInt((f) -> f.getAnnotation(PropertyBytesInfo.class).length()).sum();

    }

    default ByteBuf defaultEncode() {
        ByteBuf byteBuf = Unpooled.buffer(length());
        try {

            for (Field f : getRandFields()) {
                Class c = f.getType();
                Object v = f.get(this);
                f.setAccessible(true);
                int key = f.getAnnotation(PropertyBytesInfo.class).key();
                if (key > -1) {
                    byteBuf.writeShortLE(key);
                }
                if (c.equals(UnsignedShort.class)) {
                    if (Frame.byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
                        byteBuf.writeShortLE(((UnsignedShort) v).getValue());
                    } else {
                        byteBuf.writeShort(((UnsignedShort) v).getValue());
                    }
                } else if (c.equals(UnsignedInteger.class)) {
                    if (Frame.byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
                        byteBuf.writeIntLE(((UnsignedInteger) v).intValue());
                    } else {
                        byteBuf.writeInt(((UnsignedInteger) v).intValue());
                    }
                } else if (c.equals(UnsignedLong.class)) {
                    if (Frame.byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
                        byteBuf.writeLongLE(((UnsignedLong) v).longValue());
                    } else {
                        byteBuf.writeLong(((UnsignedLong) v).longValue());
                    }
                } else if (c.equals(ByteBuf.class) && v != null) {
                    byteBuf.writeBytes((ByteBuf) v);
                } else if (c.equals(byte[].class) && v != null) {
                    byteBuf.writeBytes((byte[]) v);
                } else if (c.equals(Byte.class)) {
                    if (v != null) {
                        byteBuf.writeByte((Byte) v);
                    } else {
                        byteBuf.writeByte(0);
                    }
                }
            }
        } catch (IllegalAccessException e) {


        }
        return byteBuf;
    }
}
