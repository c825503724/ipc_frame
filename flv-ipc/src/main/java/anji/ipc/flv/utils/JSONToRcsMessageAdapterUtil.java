package anji.ipc.flv.utils;

import anji.ipc.flv.rcs_message.BaseMessage;
import anji.ipc.flv.rcs_message.IllegalJsonFormat;
import anji.ipc.flv.rcs_message.JsonMeta;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static anji.ipc.flv.rcs_message.MessageTypeMap.getMessageClass;

public class JSONToRcsMessageAdapterUtil {


    public static Class<?extends BaseMessage> toMessageObject(JsonElement s) {
        JsonObject o = s.getAsJsonObject();
        if (o.has(JsonMeta.head)) {
            o = o.get(JsonMeta.head).getAsJsonObject();
            if (o.has(JsonMeta.messageTyp)) {
                return getMessageClass(o.get(JsonMeta.messageTyp).getAsInt());

            }
        }
        throw new IllegalJsonFormat("json消息体格式不对！");
    }


}
