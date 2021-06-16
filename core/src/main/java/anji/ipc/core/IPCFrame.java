package anji.ipc.core;

import anji.ipc.commons.utils.AutoOutLinkBlockingQueue;
import anji.ipc.core.channel.MessageWrapper;
import anji.ipc.core.event.Event;
import com.google.common.eventbus.EventBus;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class IPCFrame {

    private volatile boolean inited = false;

    @Setter
    private AgvController agvController;

    protected final Logger logger = LoggerFactory.getLogger(IPCFrame.class);


    private final LinkedList<InitProcess> initProcesses = new LinkedList<>();


    private final Map<String, EventBus> eventBusMap = new Hashtable<>();


    @Getter
    private final AutoOutLinkBlockingQueue<MessageWrapper> agvStatesMessage;

    private final ScheduledExecutorService eventProducersPool =
            new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setName("agv内部事件周期生成执行线程");
                    return t;
                }
            });


    private IPCFrame(Integer maxQueueSize) {
        this.agvStatesMessage = new AutoOutLinkBlockingQueue<>(maxQueueSize);
    }


    public void init() {
        bindDefaultEventBus();
        bindDefaultEventListens();
        checkStartCondition();
        addDefaultStartProcedureEvent();
    }

    public void addInitProcess(InitProcess initProcess) {
        initProcesses.add(initProcess);
    }

    public void processInitProcesses() {
        if (inited) {
            throw new RuntimeException("初始化已做过！");
        }
        for (InitProcess initProcess : initProcesses) {
            String name = initProcess.name();
            try {
                initProcess.process();
                if (logger.isDebugEnabled()) {
                    logger.debug("执行流程---{}", name);
                }
            } catch (Exception e) {
                logger.error("执行流程--{}失败", name);
            }
        }

    }

    public abstract void bindDefaultEventBus();

    public abstract void bindDefaultEventListens();

    public abstract void checkStartCondition();

    public abstract void addDefaultStartProcedureEvent();

    public void addEventProducer(EventProducer eventProducer, long period, TimeUnit timeUnit) {
        eventProducersPool.schedule(() -> {
            Event e = eventProducer.deal(agvStatesMessage);
            if (e != null) {
                postEvent(e);
            }
        }, period, timeUnit);
    }


    public void bindEvenForEventBus(Class<? extends Event> ec) {
        if (eventBusMap.containsKey(ec.toString())) {
            throw new RuntimeException("该类型事件总线已经存在");
        }
        eventBusMap.put(ec.toString(), new EventBus());
    }

    public void postEvent(Event event) {
        if (!event.getTargetObject().equals(Event.TargetObject.MOVE_CONTROLLER)) {
            Optional.of(eventBusMap.get(event.getClass().toString())).get().post(event);
            return;
        }
        Optional.of(agvController).get().enqueueMoveControllerEvent(event);
    }

    protected void postAgvControllerEvent(Event event) {
        Optional.of(eventBusMap.get(event.getClass().toString())).get().post(event);
    }

    public void addEventListener(Class<? extends Event> ec, Object ex) {
        Optional.of(eventBusMap.get(ec.toString())).get().register(ex);
    }


}
