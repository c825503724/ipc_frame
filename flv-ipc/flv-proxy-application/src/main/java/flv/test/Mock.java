package flv.test;

import anji.ipc.commons.codec.PropertyBytesInfo;
import anji.ipc.core.at_protocol.Frame;
import anji.ipc.core.at_protocol.type.UnsignedShort;
import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

import java.lang.reflect.Field;

public class Mock {


    public static Frame mockFrame() throws Exception {
        Frame frame = new Frame();
        for (int i = 1; i < Frame.codecFields.size() - 1; ++i) {
            Field f = Frame.codecFields.get(i);
            f.setAccessible(true);
            Class c = f.getAnnotation(PropertyBytesInfo.class).type();
            if (c.equals(UnsignedShort.class)) {
                f.set(frame, new UnsignedShort((short) 0));
            } else if (c.equals(UnsignedLong.class)) {
                f.set(frame, UnsignedLong.fromLongBits(System.currentTimeMillis()));
            } else if (c.equals(UnsignedInteger.class)) {
                f.set(frame, UnsignedInteger.fromIntBits(0));
            } else if (c.equals(Byte.class)) {
                f.set(frame, (byte) 0);
            }
        }
        return frame;
    }
}
