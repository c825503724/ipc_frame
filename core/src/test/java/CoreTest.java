import anji.ipc.commons.codec.DefaultBinaryTruncationDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(JUnit4.class)
public class CoreTest {


    @Test
    public void codec() throws Exception {
    }

    private void s1(Integer e) {

    }

    @Test
    public void event() {

    }

    public static void main(String[] argus) {
/*        SerialPortChannel serialPortChannel = new SerialPortChannel();
        serialPortChannel.setBaudrate(115200);
        serialPortChannel.setComName("COM3");
        serialPortChannel.init();*/

    }

    @Test
    public void tcpClient() throws Exception {
    /*    TcpClientChannel tcpClientChannel = new TcpClientChannel("localhost",9999);
        tcpClientChannel.init();
        Thread.currentThread().join();*/

    }

    @Test
    public void t1() throws Exception {
        String p = "C:\\Users\\admin\\Desktop\\新建 XLS 工作表.csv";
        Reader in = new FileReader(p);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        records.iterator().next();
        Map<String, Map<String, Integer>> SA = new HashMap<>();
        for (CSVRecord record : records) {
            String lastName = record.get(0);
            String firstName = record.get(1);
            if (SA.containsKey(lastName)) {
                if (SA.get(lastName).containsKey(firstName)) {
                    Integer s = SA.get(lastName).get(firstName);
                    SA.get(lastName).put(firstName, ++s);
                } else {
                    SA.get(lastName).put(firstName, 1);
                }
            } else {
                SA.put(lastName, new HashMap<>());
            }
        }
        System.out.println("");
        String p1 = "C:\\Users\\admin\\Desktop\\aa.csv",
                p2 = "%s,%s,%s,%s,%s";
        List<String> a1 = new ArrayList<>();
        int a, b, c, d;
        a1.add(String.format(p2, "车", "无码时间过长", "目标位置非法", "丢失多个码", "无码拒绝托盘换向"));

        for (Map.Entry<String, Map<String, Integer>> entity : SA.entrySet()) {
            if (entity.getValue().containsKey("无码时间过长")) {
                a = entity.getValue().get("无码时间过长");
            } else {
                a = 0;
            }
            if (entity.getValue().containsKey("目标位置非法")) {
                b = entity.getValue().get("目标位置非法");
            } else {
                b = 0;
            }
            ;
            if (entity.getValue().containsKey("丢失多个码")) {
                c = entity.getValue().get("丢失多个码");
            } else {
                c = 0;
            }
            ;
            if (entity.getValue().containsKey("无码拒绝托盘换向")) {
                d = entity.getValue().get("无码拒绝托盘换向");
            } else {
                d = 0;
            }

            a1.add(String.format(p2, entity.getKey(), a, b, c, d));
        }
        FileUtils.writeLines(new File(p1), a1.stream().sorted().collect(Collectors.toList()));
    }


}
