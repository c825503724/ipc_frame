package anji.ipc.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WholeStateContainer {

    private final Map<String, Object> stateMap = new ConcurrentHashMap<>();

    private static final Object lock = new Object();

    private static WholeStateContainer instance = null;


    public void updateKey(String k, Object v) {
        stateMap.put(k, v);
    }

    public void updateMap(Map<String, Object> n) {
        stateMap.putAll(n);
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
