package test;

import anji.ipc.core.at_protocol.Frame;
import flv.test.Mock;
import io.netty.buffer.ByteBuf;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Test {


    @org.junit.Test
    public void ss1() throws Exception {
    }


    @org.junit.Test
    public void jax() throws Exception {
        int i = 1;
        Frame f = Mock.mockFrame();
        long fd = System.currentTimeMillis();
        while (i-- > 0) {
            ByteBuf buf = f.encode0();
            Frame d = Frame.decode(buf);
        }
        System.out.println("end:"+(System.currentTimeMillis()-fd)/1000);
    }


}
