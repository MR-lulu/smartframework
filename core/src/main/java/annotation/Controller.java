package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lulu on 2017/11/25.
 */
@Target(ElementType.TYPE) //作用于Class
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
