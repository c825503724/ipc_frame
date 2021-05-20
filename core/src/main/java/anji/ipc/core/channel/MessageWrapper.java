package anji.ipc.core.channel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageWrapper {
    String type;

    Object message;



}
