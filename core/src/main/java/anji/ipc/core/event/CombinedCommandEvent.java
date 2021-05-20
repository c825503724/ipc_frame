package anji.ipc.core.event;

import lombok.Getter;
import lombok.Setter;

public class CombinedCommandEvent extends Event {

    @Getter
    @Setter
    private Object meta;
    private String eventKey;

    public CombinedCommandEvent(SourceType sourceType, CauseType causeType, int priority, String e) {
        super(sourceType, causeType, priority, TargetObject.MOVE_CONTROLLER);
        this.eventKey = e;
    }

    @Override
    public String eventKey() {
        return this.eventKey;
    }
}
