package flv.utils;

import anji.ipc.core.at_protocol.M;
import anji.ipc.core.utils.ATIDS_Generator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ATIDS_GeneratorImpl extends ATIDS_Generator {

    private boolean inited = false;

    private String directoryPath;

    private final String f1 = "Page_FLV.csv";
    private final String prexStr = "seg_";
    private final String endStr = ".csv";


    public ATIDS_GeneratorImpl(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public Map<Integer, M> getMs() {

//todo

        return null;
    }

    private List<Integer> IDS;
    private Map<String,M> ms;

    @Override
    public List<Integer> getRankIDS() {
        if (inited) {
            return IDS;
        }
        try {
            List<Integer> result = new ArrayList<>();
            File r = FileUtils.getFile(directoryPath, f1);
            String[] s0 = FileUtils.readLines(r, "UTF-8").get(1).split(",");
            for (int i = 1; i < s0.length - 1; ++i) {
                File file = FileUtils.getFile(directoryPath, prexStr + s0[i] + endStr);
                String[] s1 = FileUtils.readLines(file, "utf-8").get(4).split(",");
                for (int j = 1; j < s1.length - 1; ++j) {
                    result.add(Integer.valueOf(s1[j]));
                }
            }
            inited = true;
            IDS = result;
            return result;
        } catch (Exception e) {
            log.error("解析ID配置文件出错！");
        }

        return null;
    }

    public Integer getIndexByName(String name){
        int s=ms.get(name).getId();
        for( int i=0;i<IDS.size()-1;++i){
            if(IDS.get(i).equals(s)){
                return i;
            }
        }
        return -1;
    }
}
