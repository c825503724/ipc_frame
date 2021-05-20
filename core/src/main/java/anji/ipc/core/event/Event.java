package anji.ipc.core.event;

import lombok.Getter;

public abstract class Event {
    @Getter
    private final long createTime = System.currentTimeMillis();

    public enum SourceType {
        UPSTREAM, IPC, MOVE_CONTROLLER
    }

    public enum TargetObject {
        UPSTREAM,
        IPC,
        MOVE_CONTROLLER
    }

    public enum CauseType {
        ROUTINE, WARNING, MUST_TO_DO
    }

    @Getter
    final private int priority;

    @Getter
    final private SourceType sourceType;

    @Getter
    final private CauseType causeType;

    @Getter
    final private TargetObject targetObject;

    public abstract String eventKey();

    public Event(SourceType sourceType, CauseType causeType, int priority, TargetObject targetObject) {
        this.sourceType = sourceType;
        this.causeType = causeType;
        this.priority = priority;
        this.targetObject = targetObject;
    }


}
