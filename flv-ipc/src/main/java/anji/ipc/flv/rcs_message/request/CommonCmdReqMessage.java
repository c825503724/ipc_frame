package anji.ipc.flv.rcs_message.request;

import anji.ipc.flv.rcs_message.Header;
import anji.ipc.flv.rcs_message.Task;
import lombok.Data;

@Data
public class CommonCmdReqMessage {
    private Header header;
    private Task task;

}
