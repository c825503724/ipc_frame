package anji.ipc.core.at_protocol;

import anji.ipc.commons.codec.PropertyBytesInfo;
import anji.ipc.commons.utils.RankFieldsByBytesInfo;
import anji.ipc.core.at_protocol.type.UnsignedShort;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.List;

@Data
@Setter
@Getter
@Slf4j
public class Frame implements Encoder{

    public static final ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;
    public static final int lengthIndex = 24;
    public static final int lengthBesideContent;
    public static final List<Field> codecFields;

    static {
        codecFields = RankFieldsByBytesInfo.rank(Frame.class);
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

    @Override
    public int length() {
        return (lengthBesideContent + (data != null ? data.length : 0));
    }

    @Override
    public List<Field> getRandFields() {
        return codecFields;
    }

    public ByteBuf encode0() {
        ByteBuf buf =  defaultEncode();
        int r = buf.readableBytes();
        buf.setByte(r - 3, getCheck(buf, buf.readerIndex(), r - 3));
        return buf;
    }

    public static byte getCheck(ByteBuf byteBuf, int from, int length) {
        int a = 0;
        for (int i = 0; i < length; ++i) {
            a += byteBuf.getUnsignedByte(i+from);
            a &= 0xff;
        }
        return (byte) a;
    }

    public static Frame decode(ByteBuf byteBuffer) {
        Frame frame = new Frame();
        int r = byteBuffer.readableBytes(),
                frameLength = byteOrder == ByteOrder.LITTLE_ENDIAN ? byteBuffer.getShortLE(lengthIndex) : byteBuffer.getShort(lengthIndex);
        if (r < frameLength) {
            log.error("frame 长度校验失败！");
            byteBuffer.skipBytes(r);
            return frame;
        }
        int marks = getStartMarkBytes().readableBytes();
        byteBuffer.skipBytes(marks);
        try {

            for (int i = 1; i < codecFields.size() - 1; ++i) {
                Field f = codecFields.get(i);
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
                    int l = frame.getFrameLength().getValue() - lengthBesideContent;
                    if (l > 0) {
                        v = new byte[l];
                        byteBuffer.readBytes((byte[]) v);
                    }
                } else if (c.equals(Byte.class)) {
                    v = byteBuffer.readByte();
                }
                f.set(frame, v);
            }
        } catch (IllegalAccessException e) {

        }
        byteBuffer.skipBytes(marks);

        return frame;
    }

    public void setTimeStamp0(Long timeStamp) {
        this.timeStamp = UnsignedLong.fromLongBits(timeStamp);
    }

    public void setFrameType0(Short frameType) {
        this.frameType =new UnsignedShort( frameType);
    }

    public void setFrameNumber0(Short frameNumber) {
        this.frameNumber =new UnsignedShort( frameNumber);
    }

    public void setCommandCollection0(Short commandCollection) {
        this.commandCollection = new UnsignedShort(commandCollection);
    }

    public void setCommandCode0(Short commandCode) {
        this.commandCode = new UnsignedShort(commandCode);
    }

    public void setCommandFlag0(Integer commandFlag) {
        this.commandFlag = UnsignedInteger.fromIntBits(commandFlag);
    }

    public void setDataCount0(Short dataCount) {
        this.dataCount = new UnsignedShort(dataCount);
    }

    public void setFrameLength0(Short frameLength) {
        this.frameLength = new UnsignedShort(frameLength);
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setCheck(Byte check) {
        this.check = check;
    }
}
