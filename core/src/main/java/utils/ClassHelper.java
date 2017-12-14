package utils;

import annotation.Controller;
import annotation.Service;
import config.ConfigHepler;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lulu on 2017/12/12.
 */
public class ClassHelper {
    /**
     * 定义类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHepler.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet("");
    }
    /**
     * 获取应用包名下的所有类
     * @return
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取应用包名下的所有Service类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        classSet.forEach(cls->{
            if (cls.isAnnotationPresent(Service.class)){
                classSet.add(cls);
            }
        });
        return classSet;
    }

    /**
     * 获取应用包名下的所有Controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<>();
        classSet.forEach(cls->{
            if (cls.isAnnotationPresent(Controller.class)){
                classSet.add(cls);
            }
        });
        return classSet;
    }

    /**
     * 获取应用包名下的所有Bean类（包括Service和Controller)
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }
}
