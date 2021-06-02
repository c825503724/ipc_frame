package flv;

import anji.ipc.core.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class RCS {

    @Autowired
    @Qualifier(value = "rcsChannel")
    private Channel channel;


    @PostConstruct
    public void init() {
    }
}
