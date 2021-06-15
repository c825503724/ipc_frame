package anji.ipc.core.channel;

import anji.ipc.commons.codec.Decoder;
import anji.ipc.commons.codec.Encoder;
import anji.ipc.core.event.Event;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.function.Consumer;

@AllArgsConstructor
public abstract class Channel<R, P> {

    public  class MessageEncoder extends MessageToByteEncoder<R> {
        @Override
        protected void encode(ChannelHandlerContext ctx, R msg, ByteBuf out) throws Exception {
            out.writeBytes(encoder.encoder(msg));
        }

    }

    @NonNull
    protected String channelName;

    protected ByteToMessageDecoder splitter;


    @NonNull
    protected Encoder<R> encoder;

    @NonNull
    protected Decoder<P> decoder;

    protected Consumer<Event> eventConsumer;


    public abstract boolean init();

    public abstract boolean getConnectState();

    public abstract void send(R t);

    public abstract void close();


}
