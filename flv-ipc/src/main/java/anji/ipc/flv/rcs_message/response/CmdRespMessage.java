package anji.ipc.flv.rcs_message.response;

import anji.ipc.flv.rcs_message.TaskMessage;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class CmdRespMessage extends TaskMessage {


    private Integer result;

    @SerializedName("ecode")
    private Integer errorCode;

    private String reason;


}
