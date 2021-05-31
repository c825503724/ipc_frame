package test;

import anji.ipc.core.channel.Channel;
import flv.Flv;
import flv.rcs_message.response.CmdRespMessage;
import flv.rcs_message.response.DeviceInfoRegisteredMessage;
import flv.rcs_message.response.FlvStateReportedMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Flv.class)
public class EnvironmentTest {
    @Autowired
    @Resource(name = "rcsChannel")
    private Channel channel;

    @Test
    public void ss() throws Exception{
        FlvStateReportedMessage flvStateReportedMessage = mockObject(FlvStateReportedMessage.class);
        DeviceInfoRegisteredMessage deviceInfoRegisteredMessage= mockObject(DeviceInfoRegisteredMessage.class);
        CmdRespMessage cmdRespMessage = mockObject(CmdRespMessage.class);
        channel.init();
        channel.send(flvStateReportedMessage);
        channel.send(deviceInfoRegisteredMessage);
        channel.send(cmdRespMessage);
        System.out.println(channel);

    }

    public static <T> T mockObject(Class<T> c) throws Exception {
        if (c.equals(Object.class)) {
            return null;
        }
        T o = c.getDeclaredConstructor().newInstance();
        mockFields(o,c);
        if (!c.getSuperclass().equals(Object.class)) {
            mockFields(o,c.getSuperclass());
        }
        if (c.getSuperclass().getSuperclass()!=null&&!c.getSuperclass().getSuperclass().equals(Object.class)) {
            mockFields(o,c.getSuperclass().getSuperclass());
        }
        return o;
    }


    private static  void mockFields(Object o,Class c)throws Exception{
        for (Field f : c.getDeclaredFields()) {
            Class t = f.getType();
            f.setAccessible(true);
            if (t.equals(String.class)) {
                f.set(o, "aa");
            } else if (t.equals(Integer.class)) {
                f.set(o, 11);
            } else if (t.equals(Float.class)) {
                f.set(o, 0.0F);
            } else if (t.equals(List.class)) {
                f.set(o, new ArrayList<>());
            } else if (t.equals(Boolean.class)) {
                f.set(o, true);
            }else {
                f.set(o, mockObject(t));
            }
        }


    }
}
