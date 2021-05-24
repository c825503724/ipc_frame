package anji.ipc.flv.rcs_message.response;

import anji.ipc.flv.rcs_message.Header;
import anji.ipc.flv.rcs_message.Task;
import lombok.Data;

@Data
public class CmdResqMessage {

    private Header header;

    private Task task;

    private Integer result;

    private Integer errorCode;

    private String reason;


}
