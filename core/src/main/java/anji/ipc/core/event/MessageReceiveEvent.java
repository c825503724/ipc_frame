package anji.ipc.core.event;



public class MessageReceiveEvent extends Event {

    private Object message;
    private String eventKey;

    public MessageReceiveEvent(Object message) {
        super(null, null, 0, null);
        this.message = message;
    }

    @Override
    public String eventKey() {
        return eventKey;
    }

    public Object getMessage() {
        return message;
    }

    public String getEventKey() {
        return eventKey;
    }
}
