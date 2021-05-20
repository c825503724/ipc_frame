package anji.ipc.core.procedure;

import anji.ipc.core.event.CombinedCommandEvent;
import anji.ipc.core.event.Event;
import anji.ipc.core.event.StopProcedureEvent;
import com.google.common.eventbus.Subscribe;

public class MocCombinedCommandEventProcedure implements AgvProcedure<CombinedCommandEvent, StopProcedureEvent
                                                ,Event,Event> {

    private volatile boolean cancel = false;

    private ProcedureSate state = ProcedureSate.NOT_START;

    @Override
    @Subscribe
    public void process(CombinedCommandEvent e) {
        System.out.println("");
    }


    @Subscribe
    @Override
    public void cancel(StopProcedureEvent event) {
        cancel = true;
    }

    @Override
    public void pause(Event e) {

    }

    @Override
    public void resume(Event e) {

    }

    @Override
    public void reset() {

    }
}
