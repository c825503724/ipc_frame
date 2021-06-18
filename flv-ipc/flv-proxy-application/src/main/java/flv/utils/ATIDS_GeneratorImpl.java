package flv.utils;

import anji.ipc.core.at_protocol.M;
import anji.ipc.core.utils.ATIDS_Generator;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class ATIDS_GeneratorImpl extends ATIDS_Generator {

    private boolean inited = false;

    private String directoryPath;

    private final String f1 = "Page_FLV.csv";
    private final String idFileName = "general_data_dict.csv";
    private final String prexStr = "seg_";
    private final String endStr = ".csv";
    private final String[] head = new String[]{"id", "name", "type", "length", "mark_zh", "unit"};
    Map<Integer, M> mMap = new HashMap<>();


    private final Table<Integer, String, M> mTable = HashBasedTable.create();

    public ATIDS_GeneratorImpl(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public void init() {
        try {
            CSVParser parser = CSVParser.parse(new FileInputStream(FileUtils.getFile(directoryPath, idFileName)),
                    StandardCharsets.UTF_8, CSVFormat.DEFAULT.withHeader(head).withSkipHeaderRecord());
            parser.forEach((c) -> {
                int id = Integer.parseInt(c.get(head[0]));
                String name = c.get(head[1]);
                int type = Integer.parseInt(c.get(head[2]));
                int length = Integer.parseInt(c.get(head[3]));
                String mark = c.get(head[4]);
                String unit = c.get(head[5]);
                mMap.put(id, new M(id, 0, type, name, length, mark, unit));
            });
            List<Integer> result = new ArrayList<>();
            File r = FileUtils.getFile(directoryPath, f1);
            String[] s0 = FileUtils.readLines(r, "UTF-8").get(1).split(",");
            for (int i = 1; i < s0.length; ++i) {
                File file = FileUtils.getFile(directoryPath, prexStr + s0[i] + endStr);
                String[] s1 = FileUtils.readLines(file, "utf-8").get(4).split(",");
                for (int j = 1; j < s1.length; ++j) {
                    int id = Integer.parseInt(s1[j]);
                    M m = mMap.get(id);
                    int index = result.size();
                    m.setIndex(index);
                    mTable.put(index, m.getName(), m);
                    result.add(id);
                }
            }
            IDS = result;
            inited = true;
        } catch (IOException e) {
            log.error("ID 根文件解析失败");
        }

    }


    private List<Integer> IDS;

    @Override
    public List<Integer> getRankIDS() {
        if (inited) {
            return IDS;
        }
        return null;
    }

    public Integer getIndexByName(String name) {
        if (inited) {
            Map<Integer, M> cm = mTable.column(name);
            return Optional.of(cm).get().entrySet().iterator().next().getValue().getIndex();
        }
        throw new RuntimeException("初始化id映射未完成");
    }

    public M getMByIndex(Integer m) {
        if (inited) {
            Map<String, M> cm = mTable.row(m);
            return Optional.of(cm).get().entrySet().iterator().next().getValue();
        }
        throw new RuntimeException("初始化id映射未完成");
    }
}
