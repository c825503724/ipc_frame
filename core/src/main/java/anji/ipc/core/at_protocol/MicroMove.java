package anji.ipc.core.at_protocol;

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
public class MicroMove extends FrameType {

    @PropertyBytesInfo(length = 4, type = Float.class,key = 401)
    private Float length;

    private static final List<Field> codecFields;
    static {
        codecFields= RankFieldsByBytesInfo.rank(MicroMove.class.getEnclosingClass());

    }




    public MicroMove() {
        super(UnsignedShort.fromLongBits((short) 1), UnsignedShort.fromLongBits((short) 5),
                UnsignedInteger.fromIntBits((short) 0), UnsignedShort.fromLongBits((short) 1));
    }

    @Override
    public int length() {
        return defaultLength();
    }

    @Override
    public List<Field> getRandFields() {
        return codecFields;
    }

    @Override
    void encode0() {
        getFrame().setData(defaultEncode().array());

    }
}
