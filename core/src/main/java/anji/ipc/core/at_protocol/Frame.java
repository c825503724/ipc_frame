package anji.ipc.core.at_protocol;

import anji.ipc.commons.codec.PropertyBytesInfo;
import anji.ipc.core.at_protocol.type.UnsignedShort;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
public class Frame {

    public static final ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
    public static final int lengthIndex = 24;
    public static final int lengthBesideContent;
    public static final List<Field> codecFields;

    static {
        codecFields = Arrays.stream(Frame.class.getDeclaredFields()).filter(field -> field.getAnnotation(PropertyBytesInfo.class) != null)
                .sorted((Comparator.comparingInt(o -> o.getAnnotation(PropertyBytesInfo.class).order())))
                .collect(Collectors.toList());
        lengthBesideContent = codecFields.stream().mapToInt((f) -> f.getAnnotation(PropertyBytesInfo.class).length()).sum();
    }

    @PropertyBytesInfo(length = 2, type = UnsignedShort.class)
    public static final UnsignedShort startMark = new UnsignedShort((short) 0xAABB);

    @PropertyBytesInfo(length = 8, type = UnsignedLong.class, order = 1)
    private UnsignedLong timeStamp;

    @PropertyBytesInfo(length = 2, type = UnsignedShort.class, order = 2)
    private UnsignedShort frameType;
    @PropertyBytesInfo(length = 2, type = UnsignedShort.class, order = 3)
    private UnsignedShort frameNumber;
    @PropertyBytesInfo(length = 2, type = UnsignedShort.class, order = 4)
    private UnsignedShort commandCollection;
    @PropertyBytesInfo(length = 2, type = UnsignedShort.class, order = 5)
    private UnsignedShort commandCode;
    @PropertyBytesInfo(length = 4, type = UnsignedInteger.class, order = 6)
    private UnsignedInteger commandFlag;
    @PropertyBytesInfo(length = 2, type = UnsignedShort.class, order = 7)
    private UnsignedShort dataCount;
    @PropertyBytesInfo(length = 2, type = UnsignedShort.class, order = 8)
    private UnsignedShort frameLength;
    @PropertyBytesInfo(length = 0, type = byte[].class, order = 9)
    private byte[] data;
    @PropertyBytesInfo(length = 1, type = Byte.class, order = 10)
    private Byte check;
    @PropertyBytesInfo(length = 2, type = UnsignedShort.class, order = 11)
    public static final UnsignedShort endMark = new UnsignedShort((short) 0xEEFF);

    public static ByteBuf getStartMarkBytes() {
        ByteBuf byteBuffer = Unpooled.buffer(2);
        return byteOrder.equals(ByteOrder.LITTLE_ENDIAN) ?
                byteBuffer.writeShortLE(startMark.getValue()) : byteBuffer.writeShort(startMark.getValue());
    }

    public static ByteBuf getEndMarkBytes() {
        ByteBuf byteBuffer = Unpooled.buffer(2);
        return byteOrder.equals(ByteOrder.LITTLE_ENDIAN) ?
                byteBuffer.writeShortLE(endMark.getValue()) : byteBuffer.writeShort(endMark.getValue());
    }

    public ByteBuf encode() {
        frameLength = new UnsignedShort((short) (29 + (data != null ? data.length : 0)));
        ByteBuf byteBuf = Unpooled.buffer();
        try {

            for (Field f : codecFields) {
                Class c = f.getType();
                Object v = f.get(this);
                f.setAccessible(true);
                if (c.equals(UnsignedShort.class)) {
                    if (byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
                        byteBuf.writeShortLE(((UnsignedShort) v).getValue());
                    } else {
                        byteBuf.writeShort(((UnsignedShort) v).getValue());
                    }
                } else if (c.equals(UnsignedInteger.class)) {
                    if (byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
                        byteBuf.writeIntLE(((UnsignedInteger) v).intValue());
                    } else {
                        byteBuf.writeInt(((UnsignedInteger) v).intValue());
                    }
                } else if (c.equals(UnsignedLong.class)) {
                    if (byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
                        byteBuf.writeLongLE(((UnsignedLong) v).longValue());
                    } else {
                        byteBuf.writeLong(((UnsignedLong) v).longValue());
                    }
                } else if (c.equals(ByteBuf.class) && v != null) {
                    byteBuf.writeBytes((ByteBuf) v);
                } else if (c.equals(byte[].class) && v != null) {
                    byteBuf.writeBytes((byte[]) v);
                } else if (c.equals(byte.class)) {
                    if (v != null) {
                        byteBuf.writeByte((byte) v);
                    } else {
                        byteBuf.writeByte(0);
                    }
                }
            }
        } catch (IllegalAccessException e) {


        }
        ByteBuf bb = byteBuf.retainedSlice(byteBuf.readerIndex(), byteBuf.readableBytes() - 3);
        final int[] i = new int[1];
        bb.forEachByte((b) -> {
            i[0] += b;
            i[0] &= 0xff;
            return true;
        });
        byteBuf.setByte(byteBuf.readableBytes() - 3, i[0]);
        return byteBuf;
    }

    public static Frame decode(ByteBuf byteBuffer) {
        Frame frame = new Frame();
        int marks = getStartMarkBytes().readableBytes();
        byteBuffer.skipBytes(marks);
        codecFields.remove(0);
        codecFields.remove(codecFields.size() - 1);
        try {

            for (Field f : codecFields) {
                f.setAccessible(true);
                Class c = f.getAnnotation(PropertyBytesInfo.class).type();
                Object v = null;
                if (c.equals(UnsignedShort.class)) {
                    Short s = byteOrder == ByteOrder.LITTLE_ENDIAN ? byteBuffer.readShortLE() : byteBuffer.readShort();
                    v = new UnsignedShort(s);
                } else if (c.equals(UnsignedLong.class)) {
                    Long s = byteOrder == ByteOrder.LITTLE_ENDIAN ? byteBuffer.readLongLE() : byteBuffer.readLong();
                    v = UnsignedLong.fromLongBits(s);
                } else if (c.equals(UnsignedInteger.class)) {
                    Integer s = byteOrder == ByteOrder.LITTLE_ENDIAN ? byteBuffer.readIntLE() : byteBuffer.readInt();
                    v = UnsignedInteger.fromIntBits(s);
                } else if (c.equals(byte[].class)) {
                    v = new byte[frame.getFrameLength().getValue() - lengthIndex];
                    byteBuffer.readBytes((byte[]) v);
                } else if (c.equals(byte.class)) {
                    v = byteBuffer.readByte();
                }
                f.set(frame, v);
            }
        }catch (IllegalAccessException e){

        }
        byteBuffer.skipBytes(marks);

        return frame;
    }
}
