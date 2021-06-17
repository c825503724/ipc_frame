package flv.command;

import anji.ipc.commons.codec.PropertyBytesInfo;
import anji.ipc.commons.utils.RankFieldsByBytesInfo;
import anji.ipc.core.at_protocol.FrameType;
import anji.ipc.core.at_protocol.type.UnsignedShort;
import com.google.common.primitives.UnsignedInteger;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.List;

@Getter
@Setter
public class Move extends FrameType {

    @PropertyBytesInfo(length = 4, type = Float.class,key = 0)
    private Float sp1x = 0F;

    @PropertyBytesInfo(length = 4, order = 1, type = Float.class,key = 1)
    private Float sp1y = 0f;

    @PropertyBytesInfo(length = 4, order = 2, type = Float.class,key = 2)
    private Float sp2x = 0F;

    @PropertyBytesInfo(length = 4, order = 3, type = Float.class,key = 3)
    private Float sp2y = 0F;

    @PropertyBytesInfo(length = 4, order = 4, type = Float.class,key = 4)
    private Float sp3x = 0F;

    @PropertyBytesInfo(length = 4, order = 5, type = Float.class,key = 5)
    private Float sp3y = 0F;

    @PropertyBytesInfo(length = 4, order = 6, type = Float.class,key = 6)
    private Float sp4x = 0F;

    @PropertyBytesInfo(length = 4, order = 7, type = Float.class,key = 7)
    private Float sp4y = 0F;

    @PropertyBytesInfo(length = 4, order = 8, type = Float.class,key = 8)
    private Float l1x = 0F;
    @PropertyBytesInfo(length = 4, order = 9, type = Float.class,key = 9)
    private Float l1y = 0F;
    @PropertyBytesInfo(length = 4, order = 10, type = Float.class,key = 10)
    private Float l2x = 0F;
    @PropertyBytesInfo(length = 4, order = 11, type = Float.class,key = 11)
    private Float l2y = 0F;
    @PropertyBytesInfo(length = 4, order = 12, type = Float.class,key = 12)
    private Float rad = 0F;
    @PropertyBytesInfo(length = 4, order = 13, type = Float.class,key = 13)

    private Float distance = 0F;

    @PropertyBytesInfo(length = 4, order = 14, type = Float.class,key = 14)
    private Float mv = 0F;
    @PropertyBytesInfo(length = 4, order = 15, type = Float.class,key = 15)
    private Float ev = 0F;

    @PropertyBytesInfo(length = 1, order = 16, type = Byte.class,key = 16)
    private Byte pType = 0;

    @PropertyBytesInfo(length = 1, order = 17, type = Byte.class,key = 17)
    private Byte forward = 0;

    @PropertyBytesInfo(length = 1, order = 18, type = Byte.class,key = 18)
    private Byte stopType = 0;

    protected static final List<Field> codecFields;

    static {
        codecFields = RankFieldsByBytesInfo.rank(Move.class);

    }


    public Move(UnsignedShort commandCollection, UnsignedShort commonCode, UnsignedInteger commandFlag, UnsignedShort count) {
        super(commandCollection, commonCode, commandFlag, count);
    }


    public Move() {
        super(UnsignedShort.fromLongBits((short) 1), UnsignedShort.fromLongBits((short) 1),
                UnsignedInteger.fromIntBits((short) 0), UnsignedShort.fromLongBits((short) 19));
    }

    @Override
    public void encode0() {
        getFrame().setData(defaultEncode().array());
    }

    @Override
    public int length() {
        return defaultLength();
    }

    @Override
    public List<Field> getRandFields() {
        return codecFields;
    }
}
