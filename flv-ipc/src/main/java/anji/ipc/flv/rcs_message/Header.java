package anji.ipc.flv.rcs_message;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Header {


    @SerializedName("msg_flag")
    private final String flag = "$ANJI_FLV";

    @SerializedName("msg_sequence")
    private Integer sequence;

    @SerializedName(JsonMeta.messageTyp)
    private Integer type;

    @SerializedName("msg_version")
    private String version;

    @SerializedName("msg_encipher")
    private String encipher;


}
