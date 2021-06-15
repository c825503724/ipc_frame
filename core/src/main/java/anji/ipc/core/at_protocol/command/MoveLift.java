package anji.ipc.core.at_protocol.command;

import anji.ipc.commons.codec.PropertyBytesInfo;
import anji.ipc.commons.utils.RankFieldsByBytesInfo;
import anji.ipc.core.at_protocol.type.UnsignedShort;
import com.google.common.primitives.UnsignedInteger;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.List;

@Getter
@Setter
public class MoveLift extends Move {
    @PropertyBytesInfo(length = 4, type = Float.class, key = 401)
    private Float targetHeight = 0F;


    private static final List<Field> codecFields;

    static {
        codecFields = Move.codecFields;
        codecFields.addAll(RankFieldsByBytesInfo.rank(MoveLift.class));

    }


    public MoveLift() {
        super(UnsignedShort.fromLongBits((short) 1), UnsignedShort.fromLongBits((short) 3),
                UnsignedInteger.fromIntBits((short) 0), UnsignedShort.fromLongBits((short) 20));
    }


    @Override
    public void encode0() {
        getFrame().setData(defaultEncode().array());
    }
}
