package anji.ipc.core;

import anji.ipc.commons.utils.AutoOutLinkBlockingQueue;
import anji.ipc.core.channel.MessageWrapper;
import anji.ipc.core.event.Event;

public interface EventProducer {


    Event deal(AutoOutLinkBlockingQueue< MessageWrapper> messages);


}
