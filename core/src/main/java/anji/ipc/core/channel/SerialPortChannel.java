package anji.ipc.core.channel;

import anji.ipc.commons.codec.Decoder;
import anji.ipc.commons.codec.DefaultBinaryTruncationDecoder;
import anji.ipc.commons.codec.Encoder;
import anji.ipc.core.event.ChannelConnectedEvent;
import anji.ipc.core.event.Event;
import anji.ipc.core.event.MessageReceiveEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.rxtx.RxtxChannel;
import io.netty.channel.rxtx.RxtxDeviceAddress;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.function.Consumer;

public class SerialPortChannel<R,P> extends Channel<R,P> {


    @Setter
    private String comName;

    @Setter
    private Integer baudrate;

    private EventLoopGroup group;
    private Bootstrap bootstrap;
    private ChannelFuture channelFuture;


    private final Logger logger = LoggerFactory.getLogger(SerialPortChannel.class);

    public SerialPortChannel(String comName, Integer baudrate, String channelName, ByteToMessageDecoder splitter,
                             Encoder<R> encoder, Decoder<P> decoder,Consumer<Event> m) {
        super(channelName, splitter, encoder, decoder,m);
        this.comName = comName;
        this.baudrate = baudrate;
    }

    class RxtxClientHandler extends MessageToMessageDecoder<ByteBuf> {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {

        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            if(eventConsumer!=null){
            eventConsumer.accept(new ChannelConnectedEvent(channelName));}
            logger.info("串口打开成功");
        }


        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if(eventConsumer!=null){
            eventConsumer.accept(new MessageReceiveEvent(new MessageWrapper(channelName, decoder.decode((ByteBuf) msg))));}
            ((ByteBuf)msg).release();
        }

    }


    @Override
    public boolean init() {
        group = new OioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(RxtxChannel.class)
                .handler((new ChannelInitializer<RxtxChannel>() {
                    @Override
                    protected void initChannel(RxtxChannel ch) throws Exception {
                        ch.config().setBaudrate(baudrate);
                        ch.pipeline().addLast(
                                new RxtxClientHandler(),
                                new MessageEncoder());
                        if(splitter!=null){ch.pipeline().addLast(splitter);}
                    }
                }));
        return connect();
    }

    private boolean connect() {
        try {
            channelFuture = bootstrap.connect(new RxtxDeviceAddress(comName)).sync();
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }


    @PreDestroy
    private void stop() throws Exception {
        channelFuture.channel().closeFuture().sync();
        group.shutdownGracefully();
    }

    @Override
    public boolean getConnectState() {
        return channelFuture.channel().isActive();
    }

    @Override
    public void send(R t) {
        if (channelFuture.channel().isActive()) {
            channelFuture.channel().writeAndFlush(t);
        } else {
            logger.error("串口未打开");
            throw new ErrorConnectException("串口未打开");
        }
    }

    @Override
    @PreDestroy
    public void close() {
        try {
            stop();
        } catch (Exception e) {
            logger.error("关闭串口失败");
        }
    }
}
