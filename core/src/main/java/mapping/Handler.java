package mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import utils.BeanHelper;
import utils.ReflectionUtil;

import java.lang.reflect.Method;

/**
 * Created by blue on 2017/12/13.
 */
@Getter
@AllArgsConstructor
public class Handler {
    private Class<?> controllerClass;
    private Method actionMethod;

    /**
     * 执行controller中的方法
     * @param param
     * @return
     */
    public Object doHandler(Param param){
        Object controller = BeanHelper.getBean(controllerClass);
        return ReflectionUtil.invokeMethod(controller,actionMethod,param);
    }
}
