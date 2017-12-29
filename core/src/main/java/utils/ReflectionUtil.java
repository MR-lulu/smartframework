package utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lulu on 2017/12/12.
 * 反射工具类
 */
@Slf4j
public final class ReflectionUtil {
    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        log.error("创建实例失败！");
        return instance;
    }

    /**
     * 调用方法
     * @param obj
     * @param method
     * @param args
     * @return
     */
    public static Object invokeMethod(Object obj, Method method,Object... args){
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj,args);
        } catch (Exception e){
            log.error("调用方法失败！");
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     * @param obj
     * @param field
     * @param value
     */
    public static void setField(Object obj, Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(obj,value);
        } catch (Exception e){
            log.error("设置成员变量失败！");
            throw new RuntimeException(e);
        }
    }
}
