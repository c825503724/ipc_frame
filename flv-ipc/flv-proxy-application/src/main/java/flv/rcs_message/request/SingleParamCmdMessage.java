package flv.rcs_message.request;

import flv.rcs_message.BaseMessage;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SingleParamCmdMessage extends BaseMessage {

    private Number value;
}
