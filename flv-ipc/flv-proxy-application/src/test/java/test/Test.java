package test;

import anji.ipc.flv.jaxb.map.Model;
import flv.rcs_message.response.FlvStateReportedMessage;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.JAXBContext;
import java.io.File;

@RunWith(JUnit4.class)
public class Test {


    @org.junit.Test
    public void ss1() throws Exception {
        System.out.println("");
    }


    @org.junit.Test
    public void jax() throws Exception {

        JAXBContext jaxbContext = JAXBContext.newInstance(Model.class.getPackage().getName());
        Model model = (Model) jaxbContext.createUnmarshaller().unmarshal(new File("C:\\Users\\admin\\Documents\\WeChat Files\\wxid_crpxtgdq72zq42\\FileStorage\\File\\2021-05\\ipc_ver_map 2.xml"));
        System.out.println("");
    }
}
