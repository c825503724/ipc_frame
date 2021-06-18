package anji.ipc.core.at_protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class M {
    private Integer id;
    private Integer index;

    private Integer type;

    private String name;

    private Integer length;

    private String description;

    private String unit;

}
