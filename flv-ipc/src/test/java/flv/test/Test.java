package flv.test;

import anji.ipc.core.channel.Channel;
import anji.ipc.core.channel.MessageWrapper;
import anji.ipc.core.channel.SerialPortChannel;
import anji.ipc.core.channel.TcpClientChannel;
import anji.ipc.core.event.MessageReceiveEvent;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class Test {
    private volatile Channel channel = null;

    @org.junit.Test
    public void tcp() throws Exception {
        String rcsIp = "localhost";
        int port = 6000;
        final Gson wgson = new Gson();
        String com = "COM2";
        int p = 115200;

        SerialPortChannel serialPortChannel = new SerialPortChannel(com, p, "seral", null, (o) -> (ByteBuf) o, (b) -> b, null);
        serialPortChannel.init();
        channel = serialPortChannel;
        TcpClientChannel tcpClientChannel = new TcpClientChannel(rcsIp, port, "rcs", null,
                (o) -> Unpooled.wrappedBuffer(wgson.toJson(o).getBytes(StandardCharsets.UTF_8)),
                (j) -> j, this::consumer);

        tcpClientChannel.init();

        TimeUnit.HOURS.sleep(2);
    }


    public void consumer(Object o) {
        if (channel == null) {
            return;
        }
        if (o instanceof MessageReceiveEvent) {
            MessageReceiveEvent messageReceiveEvent = (MessageReceiveEvent) o;
            if (messageReceiveEvent.getMessage() != null) {
                channel.send(((MessageWrapper) messageReceiveEvent.getMessage()).getMessage());
            }
        }
    }
}
