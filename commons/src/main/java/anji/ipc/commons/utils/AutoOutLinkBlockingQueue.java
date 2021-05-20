package anji.ipc.commons.utils;

import java.util.concurrent.LinkedBlockingQueue;

public class AutoOutLinkBlockingQueue<T> extends LinkedBlockingQueue<T> {
    public AutoOutLinkBlockingQueue(int capacity) {
        super(capacity);
    }

    public synchronized void inQueue(T t) {
        if (remainingCapacity() == 0) {
            poll();
        }
        try {
            put(t);
        } catch (InterruptedException e) {

        }


    }


}
