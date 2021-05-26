package flv.test;

import anji.ipc.core.channel.Channel;
import anji.ipc.flv.Flv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
@SpringBootTest(classes = Flv.class)
public class EnvironmentTest {
    @Autowired
    @Resource(name = "rcsChannel")
    private Channel channel;


    public void ss(){
        System.out.println(channel);

    }
}
