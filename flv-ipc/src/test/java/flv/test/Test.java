package flv.test;

import anji.ipc.flv.rcs_message.response.FlvStateReportedMessage;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Test {


    @org.junit.Test
    public void ss1() throws Exception {
        FlvStateReportedMessage message = EnvironmentTest.mockObject(FlvStateReportedMessage.class);
        System.out.println("");
    }
}
