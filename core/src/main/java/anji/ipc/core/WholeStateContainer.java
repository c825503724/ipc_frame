package anji.ipc.core;

import anji.ipc.core.event.UpdateEvent;
import com.google.common.eventbus.Subscribe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WholeStateContainer {

    private final Map<String, Object> stateMap = new ConcurrentHashMap<>();

    private static final Object lock = new Object();

    private static WholeStateContainer instance = null;

    @Subscribe
    public void updateKey(String k, Object v) {
        stateMap.put(k, v);
    }

    @SuppressWarnings("unchecked")
    public void updateMap(UpdateEvent updateEvent) {
        String key = updateEvent.getKey();
        Object inofos = updateEvent.getInfos();
        if (updateEvent.eventKey() != null) {
            stateMap.put(key, updateEvent.getInfos());
        } else if (inofos instanceof Map) {
            stateMap.putAll((Map) inofos);
        }
    }

    private WholeStateContainer() {

    }

    public static WholeStateContainer instance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new WholeStateContainer();
            }
            return instance;
        }
    }
}
