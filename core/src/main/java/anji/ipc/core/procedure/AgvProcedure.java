package anji.ipc.core.procedure;

import anji.ipc.core.event.Event;

public interface AgvProcedure<T extends Event,S extends Event,P extends Event,R extends Event> {

    enum ProcedureSate {
        NOT_START,
        STARTED,
        ERROR,
        FINISHED
    }

    void process(T e);

    void cancel(S e);

    void reset();

    void pause(P e);

    void resume(R e);


}
