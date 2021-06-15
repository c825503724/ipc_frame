package flv;

import anji.ipc.commons.codec.Decoder;
import anji.ipc.commons.codec.DefaultBinaryTruncationDecoder;
import anji.ipc.commons.codec.Encoder;
import anji.ipc.commons.utils.PrintHexStringUtil;
import anji.ipc.core.at_protocol.Frame;
import anji.ipc.core.at_protocol.FrameType;
import anji.ipc.core.channel.Channel;
import anji.ipc.core.channel.SerialPortChannel;
import anji.ipc.core.channel.TcpClientChannel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import flv.utils.JSONToRcsMessageAdapterUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class ChannelConfig {

    @Autowired
    private Gson gson;

    @Bean(name = "rcsChannel")
    public Channel rcsChannel() {
        String rcsIp = "localhost";
        int port = 6000;
        return new TcpClientChannel(true, rcsIp, port, "rcs", null,
                (o) -> Unpooled.wrappedBuffer(gson.toJson(o).getBytes(StandardCharsets.UTF_8)),
                (p) -> {
                    JsonElement e = JsonParser.parseString(p.readCharSequence(p.readableBytes(), StandardCharsets.UTF_8).toString());
                    return gson.fromJson(e, JSONToRcsMessageAdapterUtil.toMessageObject(e));
                }, System.out::println);
    }


  @Bean(name = "mcuChannel")
       public Channel mcuChannel() {
           String com = "COM1";
           int buadrate = 115200*4;
           return new SerialPortChannel<>(com, buadrate, "mcuChannel",
                   new DefaultBinaryTruncationDecoder(Frame.getStartMarkBytes(), Frame.getEndMarkBytes(),
                           Frame.lengthIndex, 1024, Frame.lengthBesideContent),
                   FrameType::encode, Frame::decode, RCS::consumer);

       }
    volatile long i = 0;



}
