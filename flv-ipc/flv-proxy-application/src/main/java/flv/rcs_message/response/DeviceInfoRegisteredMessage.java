package flv.rcs_message.response;

import flv.rcs_message.BaseMessage;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeviceInfoRegisteredMessage extends BaseMessage {



    @SerializedName("serial_no")
    private String serialNumber;


    @SerializedName("device_id")
    private String deviceId;

    private String model;

    private String version;

    private Integer length;

     private Integer width;

     private Integer height;

    @SerializedName("turning_radius")
    private Integer turningRadius;

    @SerializedName("map_version")
    private String mapVersion;



}
