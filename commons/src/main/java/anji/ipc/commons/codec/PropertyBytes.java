package anji.ipc.commons.codec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyBytes {
    int length();

    boolean isLittleEndian() default true;//1大端，2小端

    int order() default 0;

    Class<?> type() ;
}
