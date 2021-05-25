package anji.ipc.flv.rcs_message;

import java.util.HashMap;
import java.util.Map;

public class MessageTypeMap {
    public enum Type {
        LOGIN_REQ,
        LOGIN_RESPONSE,
        START_MOVE,
        KEEP_MOVE,
        STOP_MOVE,
        STATE_REPORT,
        STATE_RESP,
        SOFT_STOP,
        SOFT_STOP_RESP,
        ESTOP,
        ESTOP_RESP,
        RESUME,
        RESUME_RESP,
        LIFT_UP,
        LIFT_UP_RESP,
        LIFT_DOWN,
        LIFT_DOWN_RESP,
        INIT_POSE,
        INIT_POSE_RESP,
        CHARGE,
        CHARGE_RESP,
        STOP_CHARGE,
        STOP_CHARGE_RESP,
        SET_MAX_V,
        SET_MAX_V_RESP,
        RESET_ER,
        RESET_ER_RESP,
    }

    private final static Map<Type, Integer> maps = new HashMap<>(Type.values().length);

    private static void register(Type type) {
        maps.put(type, initNumber++);
    }

    public static Integer getTypeValue(Type t) {
        return maps.get(t);
    }

    private static int initNumber = 1;

    static {

        for (Type t : Type.values()) {
            register(t);
        }

    }

}
