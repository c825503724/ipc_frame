package anji.ipc.core.event;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

public class UpdateEvent extends Event {

    private String eventKey = "update";
    @Getter
    @Setter
    private Map metas;

    public UpdateEvent(SourceType sourceType, CauseType causeType, int priority, TargetObject targetObject) {
        super(sourceType, causeType, priority, targetObject);
    }

    @Override
    public String eventKey() {
        return eventKey ;
    }
}
