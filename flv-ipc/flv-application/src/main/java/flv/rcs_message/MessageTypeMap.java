package flv.rcs_message;

import flv.rcs_message.request.CommonCmdReqMessage;
import flv.rcs_message.request.MoveReqMessage;
import flv.rcs_message.request.SingleParamCmdMessage;
import flv.rcs_message.response.CmdRespMessage;
import flv.rcs_message.response.DeviceInfoRegisteredMessage;
import flv.rcs_message.response.FlvStateReportedMessage;

import java.util.HashMap;
import java.util.Map;

public class MessageTypeMap {
    public enum Type {
        LOGIN_REQ,
        LOGIN_RESPONSE,
        MOVE,
        MOVE_RESP,
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

    private final static Map<Integer, Class> classMap = new HashMap<>(Type.values().length);

    private static void register(Type type, Class c) {
        classMap.put(initNumber, c);
        maps.put(type, initNumber++);
    }
    private static void register(Type type) {
       register(type,CmdRespMessage.class);
    }

    public static Class<?extends BaseMessage> getMessageClass(Integer t){
        return classMap.get(t);
    }
    public static Integer getTypeValue(Type t) {
        return maps.get(t);
    }

    private static int initNumber = 1;

    static {

       register(Type.LOGIN_REQ, DeviceInfoRegisteredMessage.class);
       register(Type.LOGIN_RESPONSE);
       register(Type.MOVE, MoveReqMessage.class);
       register(Type.MOVE_RESP);
       register(Type.STATE_REPORT, FlvStateReportedMessage.class);
       register(Type.STATE_RESP);
       register(Type.SOFT_STOP, CommonCmdReqMessage.class);
       register(Type.SOFT_STOP_RESP);
       register(Type.ESTOP,CommonCmdReqMessage.class);
       register(Type.ESTOP_RESP);
       register(Type.RESUME,CommonCmdReqMessage.class);
       register(Type.RESUME_RESP);
       register(Type.LIFT_UP,CommonCmdReqMessage.class);
       register(Type.LIFT_UP_RESP);
       register(Type.LIFT_DOWN,CommonCmdReqMessage.class);
       register(Type.LIFT_DOWN_RESP);
       register(Type.INIT_POSE,CommonCmdReqMessage.class);
       register(Type.INIT_POSE_RESP);
       register(Type.CHARGE,CommonCmdReqMessage.class);
       register(Type.CHARGE_RESP);
       register(Type.STOP_CHARGE,CommonCmdReqMessage.class);
       register(Type.STOP_CHARGE_RESP);
       register(Type.SET_MAX_V, SingleParamCmdMessage.class);
       register(Type.SET_MAX_V_RESP);
       register(Type.RESET_ER,CommonCmdReqMessage.class);
       register(Type.RESET_ER_RESP);
    }

}
