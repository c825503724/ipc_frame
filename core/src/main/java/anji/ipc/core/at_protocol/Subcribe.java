package anji.ipc.core.at_protocol;

import anji.ipc.core.at_protocol.type.UnsignedShort;
import com.google.common.primitives.UnsignedInteger;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.List;

public class Subcribe extends FrameType {

    private List<UnsignedShort> IDS;

    public Subcribe(List<UnsignedShort> IDS) {
        super(UnsignedShort.fromLongBits((short) 0),
                UnsignedShort.fromLongBits((short) 5),
                UnsignedInteger.fromIntBits(1),
                UnsignedShort.fromLongBits((short) 0));
        Subcribe.this.IDS = IDS;
    }

    @Override
    public void encode0() {
        ByteBuf byteBuf = Unpooled.buffer(IDS.size() * 2);
        for (UnsignedShort s : IDS) {
            if (Frame.byteOrder.equals(ByteOrder.LITTLE_ENDIAN)) {
                byteBuf.writeShortLE(s.getValue());
            } else {
                byteBuf.writeShort(s.getValue());
            }
        }
        getFrame().setData(byteBuf.array());
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public List<Field> getRandFields() {
        return null;
    }
}
