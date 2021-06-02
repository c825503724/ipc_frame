package anji.ipc.commons.codec;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface Decoder <T>{

    T decode(ByteBuf byteBuf);
}
