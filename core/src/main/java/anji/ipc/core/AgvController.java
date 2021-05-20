package anji.ipc.core;

import anji.ipc.core.channel.Channel;
import anji.ipc.core.event.ControllerCommandEvent;
import anji.ipc.core.event.Event;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class AgvController {

    private final Logger logger = LoggerFactory.getLogger(AgvController.class);

    private final List<Event> moveControllerEventQueue = Collections.synchronizedList(new LinkedList<>());

    private IPCFrame ipcFrame;

    @Getter
    @Setter
    private Channel channel;

    private AgvController() {

    }


    private static AgvController instance = null;

    public synchronized static AgvController getAgvControllerInstance() {
        if (instance == null) {
            instance = new AgvController();
        }
        return instance;
    }

    public void init() {
        if (Optional.of(channel).get().init()) {
            startControllerWorkThread();
        }
    }

    public boolean isConnect() {
        return channel.getConnectState();
    }

    protected final Object sendLock = new Object();


    @Subscribe
    public void dispatchCommand(ControllerCommandEvent event) {
        synchronized (sendLock) {
            //todo
        }
    }

    private void startControllerWorkThread() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (moveControllerEventQueue.size() == 0) {
                        TimeUnit.SECONDS.sleep(1);
                        continue;
                    }
                    ipcFrame.postAgvControllerEvent(moveControllerEventQueue.remove(0));
                } catch (Exception e) {

                }
            }
        });
        thread.setName("controllerWorker");
        thread.start();
    }

    protected boolean enqueueMoveControllerEvent(Event e) {
        Event.TargetObject targetObject = e.getTargetObject();
        Event.CauseType causeType = e.getCauseType();
        if (targetObject == Event.TargetObject.MOVE_CONTROLLER) {
            if (causeType == Event.CauseType.WARNING && moveControllerEventQueue.contains(e)) {
                logger.debug(String.format("运动控制告警事件%s,已存在", e.eventKey()));
                return false;
            } else {
                moveControllerEventQueue.add(e);
                return true;
            }
        }
        return true;
    }
}
