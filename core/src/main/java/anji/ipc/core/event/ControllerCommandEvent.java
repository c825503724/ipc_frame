package anji.ipc.core.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class ControllerCommandEvent extends Event {
    @Getter
    @Setter
    private Map<String, Object> metas;
    private String eventKey;

    public ControllerCommandEvent(SourceType sourceType, int priority, String eventKey) {
        super(sourceType, CauseType.ROUTINE, priority, TargetObject.MOVE_CONTROLLER);
        this.eventKey = eventKey;
    }

    @Override
    public String eventKey() {
        return eventKey;
    }
}
