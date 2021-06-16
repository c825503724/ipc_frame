package anji.ipc.core.at_protocol;

import anji.ipc.core.at_protocol.type.UnsignedShort;
import com.google.common.primitives.UnsignedInteger;

import java.lang.reflect.Field;
import java.util.List;

public class FramePool {


    static class EmptyFrame extends FrameType {
        public EmptyFrame(Integer commandCollection, Integer commandCode,
                          Integer commandFlag, Integer count) {
            super(UnsignedShort.fromLongBits(commandCollection.shortValue()),
                    UnsignedShort.fromLongBits(commandCode.shortValue()),
                    UnsignedInteger.fromIntBits(commandFlag),
                    UnsignedShort.fromLongBits(count.shortValue()));
        }

        @Override
       public void encode0() {

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

    public final static FrameType CheckConnected = new EmptyFrame(0, 0, 0, 0);
    public final static FrameType NodataAns = new EmptyFrame(0, 1, 0, 3);
    public final static FrameType AskData = new EmptyFrame(0, 2, 2, 0);
    public final static FrameType NOdataAns = new EmptyFrame(0, 4, 0, 3);
    public final static FrameType SyncTime = new EmptyFrame(0, 4, 1, 0);
    public final static FrameType SubscribeData = new EmptyFrame(0, 6, 0, 3);

}
