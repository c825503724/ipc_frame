package anji.ipc.commons.codec;

import io.netty.buffer.ByteBuf;

public interface Decoder <T>{

    T decode(ByteBuf byteBuf);
}
