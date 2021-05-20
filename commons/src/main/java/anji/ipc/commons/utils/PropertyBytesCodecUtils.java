package anji.ipc.commons.utils;

import anji.ipc.commons.codec.DecodeException;
import anji.ipc.commons.codec.EncodeException;
import anji.ipc.commons.codec.PropertyBytes;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class PropertyBytesCodecUtils {

    public static ByteBuf encode(ByteBuf buffer, Object field, PropertyBytes propertyBytes) throws Exception {

        Class<?> c = propertyBytes.type();
        assert field.getClass().equals(propertyBytes.type());
        if (Integer.class.equals(c)) {
            if (propertyBytes.isLittleEndian()) {
                buffer.writeIntLE((Integer) field);
            } else {
                buffer.writeInt((Integer) field);
            }
        } else if (Short.class.equals(c)) {
            if (propertyBytes.isLittleEndian()) {
                buffer.writeShortLE((Short) field);
            } else {
                buffer.writeShort((Short) field);
            }
        } else if (Float.class.equals(c)) {
            buffer.writeFloat((Float) field);
        } else if (Long.class.equals(c)) {
            if (propertyBytes.isLittleEndian()) {
                buffer.writeLongLE((Long) field);
            } else {
                buffer.writeLong((Long) field);
            }
        } else if (Double.class.equals(c)) {
            buffer.writeDouble((Double) field);
        } else if (String.class.equals(c)) {
            buffer.writeCharSequence((String) field, StandardCharsets.UTF_8);
        } else if (Byte.class.equals(c)) {
            buffer.writeByte((byte) field);
        } else if (Boolean.class.equals(c)) {
            buffer.writeBoolean((Boolean) field);
        } else {
            throw new EncodeException();
        }
        return buffer;
    }


    public static Object decode(ByteBuf buffer, PropertyBytes propertyBytes) throws Exception {
        Class<?> c = propertyBytes.type();
        if (Integer.class.equals(c)) {
            if (propertyBytes.isLittleEndian()) {
                return buffer.readIntLE();
            } else {
                return buffer.readInt();
            }
        } else if (Short.class.equals(c)) {
            if (propertyBytes.isLittleEndian()) {
                return buffer.readShortLE();
            } else {
                return buffer.readShort();
            }
        } else if (Float.class.equals(c)) {
            return buffer.readFloat();
        } else if (Long.class.equals(c)) {
            if (propertyBytes.isLittleEndian()) {
                return buffer.readLongLE();
            } else {
                return buffer.readLong();
            }
        } else if (Double.class.equals(c)) {
            return buffer.readDouble();
        } else if (String.class.equals(c)) {
            return buffer.readCharSequence(propertyBytes.length(), StandardCharsets.UTF_8);
        } else if (Byte.class.equals(c)) {
            return buffer.readByte();
        } else if (Boolean.class.equals(c)) {
            return buffer.readBoolean();
        } else {
            throw new DecodeException();
        }
    }

}
