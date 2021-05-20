package anji.ipc.core.event;

public class ChannelConnectedEvent extends Event {

    private String channelName;
    public ChannelConnectedEvent(String channelName) {
        super(null, null, 0, null);
        this.channelName=channelName;

    }

    @Override
    public String eventKey() {
        return channelName;
    }
}
