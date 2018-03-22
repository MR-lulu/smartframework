package annotation;

import java.lang.annotation.*;

/**
 * Created by blue on 2018/1/11.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
    String value();
}