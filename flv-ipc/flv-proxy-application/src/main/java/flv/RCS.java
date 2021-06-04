package flv;

import anji.ipc.core.at_protocol.Frame;
import anji.ipc.core.channel.Channel;
import anji.ipc.core.channel.MessageWrapper;
import anji.ipc.core.event.Event;
import anji.ipc.core.event.MessageReceiveEvent;
import flv.test.Mock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class RCS {

    @Autowired
    @Qualifier(value = "mcuChannel")
    private Channel channel;


    @PostConstruct
    public void init() throws Exception {
        channel.init();
        Frame f = Mock.mockFrame();
        channel.send(f);
    }

    private static volatile int id = 0;

    public static void consumer(Event o) {
        if (o instanceof MessageReceiveEvent) {
            MessageReceiveEvent e = (MessageReceiveEvent) o;
            Frame f = (Frame) ((MessageWrapper) e.getMessage()).getMessage();
            if (f.getFrameNumber() == null) return;
            int s = Short.toUnsignedInt(f.getFrameNumber().getValue());
            if (id != 0) {
                if (Math.abs(s - id) > 1) {
                    log.info("-----------------超出1");
                }
            }
            id = s;
            log.info("***" + s);

        }
    }
}
