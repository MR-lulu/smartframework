package mapping;

import annotation.Action;
import org.apache.commons.lang3.ArrayUtils;
import utils.ClassHelper;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by blue on 2017/12/13.
 * 请求路径与处理器之间的映射
 */
public final class HandlerMapping {
    private static final Map<Request,Handler> HANDLE_MAP = new HashMap<>();

    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (!controllerClassSet.isEmpty()){
            //遍历Controller
            controllerClassSet.forEach(controllerClass->{
                Method[] methods = controllerClass.getDeclaredMethods();
                for (Method method : methods){
                    //判断Controller的方法中是否带有Action注解
                    if (method.isAnnotationPresent(Action.class)){
                        String mapping = method.getAnnotation(Action.class).value();
                        if (mapping.matches("\\w+:/\\w*")){
                            String[] array = mapping.split(":");
                            if (ArrayUtils.isNotEmpty(array) && array.length==2){
                                Request request = new Request(array[0],array[1]);
                                Handler handler = new Handler(controllerClass,method);
                                HANDLE_MAP.put(request,handler);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 获取Handler
     * @param requestMethod
     * @param requestPath
     * @return
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        return HANDLE_MAP.get(new Request(requestMethod,requestPath));
    }
}
