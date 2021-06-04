package anji.ipc.commons.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteOrder;
import java.util.List;

public class DefaultBinaryTruncationDecoder extends ByteToMessageDecoder {

    private Logger logger = LoggerFactory.getLogger(DefaultBinaryTruncationDecoder.class);

    private final ByteBuf start;
    private final ByteBuf finished;
    private final Integer lengthOffset;
    private final Integer maxFrameLength;
    private final Integer minFrameLength;

    @Setter
    private final ByteOrder byteOrder = ByteOrder.LITTLE_ENDIAN;

    public DefaultBinaryTruncationDecoder(ByteBuf start, ByteBuf finished, Integer lengthOffset,
                                          Integer maxFrameLength, Integer minFrameLength) {
        this.start = start;
        this.finished = finished;
        this.lengthOffset = lengthOffset;
        this.maxFrameLength = maxFrameLength;
        this.minFrameLength = Math.max(start.capacity() + finished.capacity() + lengthOffset, minFrameLength);

    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        st(ctx, in, out);
    }


    private void st(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> objects) throws Exception {
        int i = buffer.readerIndex();
        while (buffer.readableBytes() > minFrameLength) {
            Object m = decode(ctx, buffer);
            if (m != null) {
                objects.add(m);
            }
            if (i == buffer.readerIndex()) {
                break;
            }
            i = buffer.readerIndex();
        }
    }

    String ss(ByteBuf vv) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int a = vv.readerIndex(); a < vv.readableBytes(); ++a) {
            stringBuilder.append(Integer.toHexString(vv.getUnsignedByte(a))).append(" ");
        }
        return stringBuilder.toString();
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
        int r = buffer.readableBytes(),
                sw = start.readableBytes(),
                ew = finished.readableBytes();
        if (r < minFrameLength) {
            return null;
        }
        final int ww = indexOf(buffer, start);
        if (ww < 0) {
            buffer.skipBytes(r);
            return null;
        }
        r = r - ww;
        buffer.skipBytes(ww);
        final int rd = buffer.readerIndex();
        if (minFrameLength < r) {
            int length = byteOrder == ByteOrder.LITTLE_ENDIAN ?
                    buffer.retainedSlice(rd+lengthOffset, 2).readUnsignedShortLE() :
                    buffer.retainedSlice(rd+lengthOffset, 2).readUnsignedShort();
            if (length > maxFrameLength || length < minFrameLength) {
                System.out.println("frame 长度字段：" + length);
                buffer.skipBytes(sw);
                return null;
            }
            if (length <= r) {
                if (buffer.retainedSlice(rd+length - ew,
                        ew).equals(finished)) {
                    return buffer.readRetainedSlice(length);
                } else {
                    buffer.skipBytes(sw);
                    return null;
                }
            }

        }


        return null;


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    /**
     * Returns the number of bytes between the readerIndex of the haystack and
     * the first needle found in the haystack.  -1 is returned if no needle is
     * found in the haystack.
     */
    private static int indexOf(ByteBuf haystack, ByteBuf needle) {
        for (int i = haystack.readerIndex(); i < haystack.writerIndex(); i++) {
            int haystackIndex = i;
            int needleIndex;
            for (needleIndex = 0; needleIndex < needle.capacity(); needleIndex++) {
                if (haystack.getByte(haystackIndex) != needle.getByte(needleIndex)) {
                    break;
                } else {
                    haystackIndex++;
                    if (haystackIndex == haystack.writerIndex() &&
                            needleIndex != needle.capacity() - 1) {
                        return -1;
                    }
                }
            }

            if (needleIndex == needle.capacity()) {
                // Found the needle from the haystack!
                return i - haystack.readerIndex();
            }
        }
        return -1;
    }
}
