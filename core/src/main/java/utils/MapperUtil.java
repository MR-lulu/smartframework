package utils;

import annotation.Mapper;
import annotation.Param;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by blue on 2018/1/11.
 */
public class MapperUtil {
    private static Map<Integer, String> params = new HashMap<>();
    private Map<String, Object> paramList = new HashMap<>();
    private String sql = "select * from users where id = #{shopId} and id = #{id}";

    public <T> T getMapper(Class<?> cls) {
        dealParam(cls);

        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{cls}, new ProxyHandler());
    }

    private void test(Object[] args) {
        params.forEach((k, v) -> {
            paramList.put(v, args[k]);
        });
    }

    private void dealParam(Class<?> cls) {
        Method[] methods = cls.getMethods();
        for (Method method : methods) {
            Parameter[] parameters = method.getParameters();
            int i = 0;
            for (Parameter parameter : parameters) {
                if (parameter.isAnnotationPresent(Param.class)) {
                    params.put(i, parameter.getAnnotation(Param.class).value());

                } else {
                    params.put(i, "param" + i);
                }
                i++;
            }
        }
    }

    public class ProxyHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            params.forEach((k, v) -> {
                System.out.println(k + ":" + v);
            });
            test(args);
            paramList.forEach((k, v) -> {
                System.out.println(k + ":" + v);
            });
            Pattern pattern = Pattern.compile("(#\\{.*\\})");
            Matcher matcher = pattern.matcher(sql);

            //sql = matcher.replaceAll();
            if (matcher.find()) {
                System.out.println(matcher.group(1));
                sql = matcher.replaceAll(String.valueOf(paramList.get("shopId")));
            }
            //System.out.println(sql);
            return null;
        }
    }
}
