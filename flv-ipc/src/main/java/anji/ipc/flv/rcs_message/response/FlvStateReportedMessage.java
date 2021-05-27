package anji.ipc.flv.rcs_message.response;

import anji.ipc.flv.rcs_message.BaseMessage;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class FlvStateReportedMessage extends BaseMessage {

    @SerializedName("X")
    private Integer x;

    @SerializedName("Y")
    private Integer y;

    @SerializedName("Heading")
    private Integer heading;

    @SerializedName("motion_status")
    private Integer motionStatus;

    @SerializedName("lift_status")
    private Integer liftStatus;
    @SerializedName("tray_id")
    private Integer trayId;
    @SerializedName("is_charging")
    private Boolean charging;
    @SerializedName("Battery")
    private Integer battery;
    @SerializedName("path_id")
    private String pathId;
    @SerializedName("node_id")
    private String nodeId;
    @SerializedName("on_track")
    private Boolean onTrack;
    @SerializedName("error_no")
    private Integer errorNumber;

    @SerializedName("current_command")
    private Integer currentCommand;
    @SerializedName("last_finished_command")
    private Integer lastFinishedCommand;
    @SerializedName("finish_percentage")
    private Float finishPercentage;
    @SerializedName("expected_x")
    private Integer expectedX;
    @SerializedName("expected_y")
    private Integer expectedY;
    @SerializedName("expected_heading")
    private Integer expectedHeading;
    @SerializedName("length_unit")
    private String lengthUnit;


}
