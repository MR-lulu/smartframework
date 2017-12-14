package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by lulu on 2017/12/12.
 * Bean 助手类
 */
public final class BeanHelper {
    /**
     * 定义Bean 映射
     */
    private static final Map<Class<?>,Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        beanClassSet.forEach(beanClass->{
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        });
    }

    /**
     * 获取Bean 映射
     * @return
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取Bean 实例
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("不能获取该类型的Bean");
        }
        return (T) BEAN_MAP.get(cls);
    }
}
