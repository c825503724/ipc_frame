package anji.ipc.core.utils;

import anji.ipc.core.at_protocol.M;

import java.util.List;
import java.util.Map;

public abstract class ATIDS_Generator {



    public abstract List<Integer> getRankIDS();

    public abstract M getMByIndex(Integer m);

    public abstract Integer getIndexByName(String name);
}
