package anji.ipc.flv.rcs_message.response;

import anji.ipc.flv.rcs_message.Header;
import lombok.Data;

@Data
public class DeviceInfoRegisteredMessage {
    private Header messageHeader;

    private String serialNumber;

    private String deviceId;

    private String model;

    private String version;

    private Integer length;

    private Integer width;

    private Integer height;

    private Integer turningRadius;

    private String mapVersion;



}
