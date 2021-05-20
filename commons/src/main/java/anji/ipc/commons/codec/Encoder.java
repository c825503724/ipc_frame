package anji.ipc.commons.codec;

import io.netty.buffer.ByteBuf;

public interface Encoder<T> {

     ByteBuf encoder(T t);
}
