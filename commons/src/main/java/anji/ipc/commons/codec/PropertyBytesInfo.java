package anji.ipc.commons.codec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyBytesInfo {
    int length();

    float order() default 0;

    Class<?> type() ;

    int key() default -1;
}
