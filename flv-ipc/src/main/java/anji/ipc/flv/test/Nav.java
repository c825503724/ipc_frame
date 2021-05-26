package anji.ipc.flv.test;

import anji.ipc.core.channel.Channel;
import anji.ipc.core.channel.MessageWrapper;
import anji.ipc.core.channel.SerialPortChannel;
import anji.ipc.core.channel.TcpClientChannel;
import anji.ipc.core.event.MessageReceiveEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Nav {
    private volatile Channel tcpChannel = null;
    private volatile Channel serialChannel = null;

    @Value("${ip}")
    String rcsIp = "localhost";
    @Value("${p}")
    int port = 6000;
    @Value("${com}")
    String com = "COM2";
    @Value("${bd}")
    int p = 115200;

    @PostConstruct
    public void start() throws Exception {
        SerialPortChannel serialPortChannel = new SerialPortChannel(com, p, "seral", null, (o) -> (ByteBuf) o, (j) -> {ByteBuf b=j.copy();j.discardReadBytes();return b;}, this::consumer2);
        serialPortChannel.init();
        tcpChannel = serialPortChannel;
        TcpClientChannel tcpClientChannel = new TcpClientChannel(false, rcsIp, port, "rcs", null,
                (o) -> (ByteBuf) o,
                (j) -> {ByteBuf b=j.copy();j.discardReadBytes();return b;}, this::consumer);
        serialChannel=tcpClientChannel;
        tcpClientChannel.init();
    }

    public void consumer2(Object o) {
        if (serialChannel == null) {
            return;
        }
        if (o instanceof MessageReceiveEvent) {
            MessageReceiveEvent messageReceiveEvent = (MessageReceiveEvent) o;
            if (messageReceiveEvent.getMessage() != null) {
                serialChannel.send(((MessageWrapper) messageReceiveEvent.getMessage()).getMessage());
            }
        }
    }

    public void consumer(Object o) {
        if (tcpChannel == null) {
            return;
        }
        if (o instanceof MessageReceiveEvent) {
            MessageReceiveEvent messageReceiveEvent = (MessageReceiveEvent) o;
            if (messageReceiveEvent.getMessage() != null) {
                tcpChannel.send(((MessageWrapper) messageReceiveEvent.getMessage()).getMessage());
            }
        }
    }
}
