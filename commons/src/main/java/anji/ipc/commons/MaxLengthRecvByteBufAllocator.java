package anji.ipc.commons;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.internal.PlatformDependent;
import lombok.Data;

@Data
public class MaxLengthRecvByteBufAllocator extends PooledByteBufAllocator {

    private int max = 1024*4;



    @Override
    public ByteBuf ioBuffer(int initialCapacity) {
        int m= Math.max(initialCapacity, max);
        if (PlatformDependent.hasUnsafe()) {
            return directBuffer(initialCapacity,m);
        }
        return heapBuffer(initialCapacity,m);
    }
}
