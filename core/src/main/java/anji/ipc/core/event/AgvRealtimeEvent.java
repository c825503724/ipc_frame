package anji.ipc.core.event;

import lombok.Getter;

public class AgvRealtimeEvent extends Event {
    private String eventKey = "agvRealtime";
   @Getter
    private Object message;

    public AgvRealtimeEvent(Object message) {
        super(SourceType.MOVE_CONTROLLER, CauseType.ROUTINE, 0, null);
        this.message = message;
    }

    @Override
    public String eventKey() {
        return eventKey;
    }
}
