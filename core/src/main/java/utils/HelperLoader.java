package utils;

import mapping.HandlerMapping;

/**
 * Created by blue on 2017/12/13.
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classes = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                HandlerMapping.class
        };
        for (Class<?> cls : classes){
            ClassUtil.loadClass(cls.getName(),true);
        }
    }
}
