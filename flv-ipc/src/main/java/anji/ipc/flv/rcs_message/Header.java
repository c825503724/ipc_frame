package anji.ipc.flv.rcs_message;

import lombok.Data;

@Data
public class Header {


    private final String flag= "$ANJI_FLV";

    private Integer sequence;

    private Integer type;

    private String version;

    private String encipher;


}
