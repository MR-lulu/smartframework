package Utils;

import org.junit.Test;
import utils.ClassUtil;

import java.util.Set;

/**
 * Created by lulu on 2017/11/25.
 */
public class ClassUtilTest {
    @Test
    public void getClassSet(){
        Set<Class<?>> set = ClassUtil.getClassSet("utils");
        set.forEach(e->{
            System.out.println(e.getClass().getName());
        });
    }
}
