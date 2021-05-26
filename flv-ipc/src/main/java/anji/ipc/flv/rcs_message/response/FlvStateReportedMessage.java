package anji.ipc.flv.rcs_message.response;

import anji.ipc.flv.rcs_message.Header;
import lombok.Data;

@Data
public class FlvStateReportedMessage {

    private Header header;

    private Integer x;

    private Integer y;

    private Integer heading;

    private Integer motionStatus;

    private Integer liftStatus;

    private Integer trayId;

    private Boolean charging;

    private Integer battery;

    private String pathId;

    private String nodeId;

    private Boolean onTrack;

    private Integer errorNumber;

    private Integer currentCommand;

    private Integer lastFinishedCommand;

    private Float finishPercentage;

    private Integer expectedX;

    private Integer expectedY;

    private Integer expectedHeading;

    private String lengthUnit;


}
