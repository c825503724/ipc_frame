package flv.rcs_message.request;

import flv.rcs_message.TaskMessage;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MoveReqMessage extends TaskMessage {


    @SerializedName("next_path")
    private List<String> nextPaths;

    @SerializedName("is_precise_stop")
    private Integer isPreciseStop;
}
