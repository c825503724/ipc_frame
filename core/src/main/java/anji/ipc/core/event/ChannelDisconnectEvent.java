package anji.ipc.core.event;

public class ChannelDisconnectEvent extends ChannelConnectedEvent {
    public ChannelDisconnectEvent(String channelName) {
        super(channelName);
    }
}
