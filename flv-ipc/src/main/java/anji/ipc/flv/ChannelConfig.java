package anji.ipc.flv;

import anji.ipc.core.channel.Channel;
import anji.ipc.core.channel.SerialPortChannel;
import anji.ipc.core.channel.TcpClientChannel;
import anji.ipc.flv.utils.JSONToRcsMessageUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.netty.buffer.Unpooled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Configuration
public class ChannelConfig {

    @Bean(name = "rcsChannel")
    public Channel rcsChannel() {
        String rcsIp = "";
        int port = 0;
        final Gson wgson = new Gson();
        return new TcpClientChannel(rcsIp, port, "rcs", null,
                (o) -> Unpooled.wrappedBuffer(wgson.toJson(o).getBytes(StandardCharsets.UTF_8)),
                (p) -> {
                JsonElement e= JsonParser.parseString(p.readCharSequence(p.readableBytes(),StandardCharsets.UTF_8).toString());
            return JSONToRcsMessageUtil.toMessageObject(e);
        }, null);
    }


    @Bean(name = "mcuChannel")
    public Channel mcuChannel() {
        String comName = "";
        int baudrate=0;
        Channel channel = new SerialPortChannel(comName,baudrate,)

        return null;
    }

}
