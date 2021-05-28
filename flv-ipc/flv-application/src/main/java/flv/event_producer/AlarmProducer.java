package flv.event_producer;

import anji.ipc.commons.utils.AutoOutLinkBlockingQueue;
import anji.ipc.core.EventProducer;
import anji.ipc.core.channel.MessageWrapper;
import anji.ipc.core.event.Event;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("alarm_producer")
public class AlarmProducer implements EventProducer {


    @Override
    public Event deal(AutoOutLinkBlockingQueue<MessageWrapper> messages) {
        return null;
    }


}
