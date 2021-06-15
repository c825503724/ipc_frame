package anji.ipc.core.at_protocol;

import anji.ipc.core.at_protocol.type.UnsignedShort;
import com.google.common.primitives.UnsignedInteger;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public abstract class FrameType implements Encoder{


    private Frame frame = new Frame();

    private UnsignedShort commandCollection;

    private UnsignedShort commonCode;


    private UnsignedInteger commandFlag;


    private UnsignedShort count;



    public FrameType(UnsignedShort commandCollection, UnsignedShort commonCode,
                     UnsignedInteger commandFlag, UnsignedShort count) {
        this.commandCollection = commandCollection;
        this.commonCode = commonCode;
        this.commandFlag = commandFlag;
        this.count = count;
        frame.setCommandCollection(commandCollection);
        frame.setCommandCode(commonCode);
        frame.setCommandFlag(commandFlag);
    }


    public ByteBuf encode() {

        encode0();
        return frame.encode0();
    }

    abstract void encode0();

}
