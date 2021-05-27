package anji.ipc.flv.rcs_message.request;

import anji.ipc.flv.rcs_message.BaseMessage;
import lombok.Data;

@Data
public class SingleParamCmdMessage extends BaseMessage {

    private Number value;
}
