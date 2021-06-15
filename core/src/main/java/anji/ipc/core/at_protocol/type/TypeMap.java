package anji.ipc.core.at_protocol.type;

import com.google.common.primitives.UnsignedInteger;
import com.google.common.primitives.UnsignedLong;

import java.util.HashMap;
import java.util.Map;

public class TypeMap {

    static private final Map<Integer, Class> typeMap = new HashMap<>();
    private static void register(Integer i,Class c){
        typeMap.put(i,c);
    }
    static {
        register(0,Byte.class);
        register(1,UnsignedByte.class);
        register(2,Short.class);
        register(3,UnsignedShort.class);
        register(4,Integer.class);
        register(5, UnsignedInteger.class);
        register(6,Long.class);
        register(7, UnsignedLong.class);
        register(8,Float.class);
        register(9,Double.class);
    }
    public static Class getDataType(Integer i){
        return typeMap.get(i);
    }


}
