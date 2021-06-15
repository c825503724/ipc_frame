import anji.ipc.core.at_protocol.Lift;
import anji.ipc.core.at_protocol.MoveLift;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CoreTest {


    @Test
    public void codec() throws Exception {

        MoveLift moveLift = new MoveLift();
        moveLift.encode();
    }
}
