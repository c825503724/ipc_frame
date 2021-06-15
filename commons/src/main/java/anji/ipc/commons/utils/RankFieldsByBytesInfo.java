package anji.ipc.commons.utils;

import anji.ipc.commons.codec.PropertyBytesInfo;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankFieldsByBytesInfo {


    public static List<Field> rank(Class c) {


        return Arrays.stream(c.getDeclaredFields()).filter(field -> field.getAnnotation(PropertyBytesInfo.class) != null)
                .sorted((Comparator.comparingDouble(o -> o.getAnnotation(PropertyBytesInfo.class).order())))
                .collect(Collectors.toList());
    }
}
