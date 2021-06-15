package test;

import anji.ipc.core.at_protocol.Frame;
import flv.test.Mock;
import flv.utils.ATIDS_GeneratorImpl;
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
       st();
    }


    public void st(){
        String p = "C:\\Users\\admin\\Downloads\\AT_HAL_MCU-Single_Steering_Wheel_Control-7ae944ee7b0e0d3e02683e6822f99a5b0c5f08f9\\AT_Doc\\AT_Config";
        ATIDS_GeneratorImpl atids_generator = new ATIDS_GeneratorImpl(p);
        atids_generator.getRankIDS();
        System.out.println("");

    }


}
