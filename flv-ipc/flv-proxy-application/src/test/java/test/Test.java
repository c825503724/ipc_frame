package test;

import anji.ipc.core.at_protocol.Frame;
import anji.ipc.flv.jaxb.map.Model;
import flv.test.Mock;
import flv.utils.ATIDS_GeneratorImpl;
import io.netty.buffer.ByteBuf;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.JAXBContext;
import java.io.File;

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

    @org.junit.Test
    public void jax1() throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(Model.class.getPackage().getName());
        Model model = (Model) jaxbContext.createUnmarshaller().unmarshal(new File("C:\\Users\\admin\\Documents\\WeChat Files\\wxid_crpxtgdq72zq42\\FileStorage\\File\\2021-05\\ipc_ver_map 2.xml"));
        System.out.println("");
    }


}
