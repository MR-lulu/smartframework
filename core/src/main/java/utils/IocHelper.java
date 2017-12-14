package utils;

import annotation.Inject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by blue on 2017/12/13.
 */
public final class IocHelper {
    static {
        //获取所有的Bean
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if (!beanMap.isEmpty()){
            //遍历BeanMap
            beanMap.forEach((k,v)->{
                //获取bean的字段
                Field[] beanFileds = k.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFileds)){
                    for (Field field : beanFileds){
                        //判断字段是否加了@Inject注解
                        if (field.isAnnotationPresent(Inject.class)){
                            Object inject = beanMap.get(field.getType());
                            if (inject!=null){
                                //通过反射注入
                                ReflectionUtil.setField(v,field,inject);
                            }
                        }
                    }
                }
            });
        }
    }
}
