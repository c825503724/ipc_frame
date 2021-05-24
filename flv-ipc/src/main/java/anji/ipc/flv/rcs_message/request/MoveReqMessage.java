package anji.ipc.flv.rcs_message.request;

import anji.ipc.flv.rcs_message.Header;
import anji.ipc.flv.rcs_message.Task;
import lombok.Data;

import java.util.List;

@Data
public class MoveReqMessage {

    private Header header;

    private Task task;

    private List<String> nextPaths;

    private Integer isPreciseStop;
}
