package anji.ipc.commons.codec;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface Encoder<T> {

    ByteBuf encoder(T t);
}
