package anji.ipc.core.event;

public class StopProcedureEvent extends Event {

    private final String eventKey = "stopProcedure";

    public StopProcedureEvent(SourceType sourceType) {
        super(sourceType, CauseType.MUST_TO_DO, Integer.MAX_VALUE, TargetObject.IPC);
    }

    @Override
    public String eventKey() {
        return eventKey;
    }


}
