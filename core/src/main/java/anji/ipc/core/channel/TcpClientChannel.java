package anji.ipc.core.channel;

import anji.ipc.commons.codec.Decoder;
import anji.ipc.commons.codec.Encoder;
import anji.ipc.core.event.ChannelConnectedEvent;
import anji.ipc.core.event.ChannelDisconnectEvent;
import anji.ipc.core.event.Event;
import anji.ipc.core.event.MessageReceiveEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TcpClientChannel<R, P> extends Channel<R, P> {

    private String serverIp;

    private Integer serverPort;

    private Integer reconnectDelay = 5;

    private Integer readTimeout = 100;

    private final Logger logger = LoggerFactory.getLogger(TcpClientChannel.class);

    private EventLoopGroup group;

    private Bootstrap bootstrap;

    private CompositeDecoderAndReconnectHandle reconnectHandle = new CompositeDecoderAndReconnectHandle();

    private ChannelFuture channelFuture;

    private Boolean idleCheck = true;

    public TcpClientChannel(boolean idleCheck, String serverIp, Integer serverPort, String channelName, ByteToMessageDecoder splitter,
                            Encoder<R> encoder, Decoder<P> decoder, Consumer<Event> c) {
        super(channelName, splitter, encoder, decoder, c);
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.idleCheck = idleCheck;

    }

    @ChannelHandler.Sharable
    class CompositeDecoderAndReconnectHandle extends MessageToMessageDecoder<P> {


        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            logger.info(String.format("tcp断开服务器连接,%s秒后开始尝试重连", reconnectDelay));
            if (eventConsumer != null) {
                eventConsumer.accept(new ChannelDisconnectEvent(channelName));
            }
            ctx.channel().eventLoop().schedule(TcpClientChannel.this::connect, reconnectDelay, TimeUnit.SECONDS);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (eventConsumer != null) {
                eventConsumer.accept(new MessageReceiveEvent(new MessageWrapper(channelName, decoder.decode((ByteBuf) msg))));
                ((ByteBuf) msg).release();
            }
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            if (eventConsumer != null) {
                eventConsumer.accept(new ChannelConnectedEvent(channelName));
            }

            logger.info(String.format("tcp连上服务器%s", ctx.channel().remoteAddress()));
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (!(evt instanceof IdleStateEvent)) {
                return;
            }
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                logger.info("主动断开服务器连接");
                ctx.close();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }


        @Override
        protected void decode(ChannelHandlerContext ctx, P msg, List<Object> out) throws Exception {

        }
    }

    @Override
    public boolean init() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(serverIp, serverPort)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                new MessageEncoder(),
                                reconnectHandle);
                        if (idleCheck) {
                            ch.pipeline().addLast(new IdleStateHandler(readTimeout, 0, 0));
                        }
                        if (splitter != null) {
                            ch.pipeline().addLast(splitter);
                        }
                    }
                });
        return connect();
    }


    private boolean connect() {
        try {
            channelFuture = bootstrap.connect().sync();
        } catch (Exception e) {
            logger.warn("鏈接失敗");
            return false;
        }
        return true;
    }

    @Override
    public boolean getConnectState() {
        if (channelFuture == null) {
            return false;
        }
        return channelFuture.channel().isActive();
    }

    @Override
    public void send(R t) {
        if (getConnectState()) {
            channelFuture.channel().writeAndFlush(t);
        }
    }

    @Override
    @PreDestroy
    public void close() {
        if (group != null) {
            try {
                channelFuture.channel().closeFuture().sync();
                group.shutdownGracefully();
            } catch (InterruptedException e) {
                return;
            }

        }
    }
}
