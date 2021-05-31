package flv.rcs_message;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public abstract class BaseMessage {
    @SerializedName(JsonMeta.head)
    private Header header;

}
