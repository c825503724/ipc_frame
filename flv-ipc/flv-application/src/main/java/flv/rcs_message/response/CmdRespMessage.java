package flv.rcs_message.response;

import flv.rcs_message.TaskMessage;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CmdRespMessage extends TaskMessage {


    private Integer result;

    @SerializedName("ecode")
    private Integer errorCode;

    private String reason;


}
