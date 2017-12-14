package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by blue on 2017/12/14.
 */
public final class PropsUtil {
    public static Properties loadProps(String fileName){
        Properties properties = null;
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (in == null) {
                throw new FileNotFoundException(fileName + "配置文件没有找到!");
            }
            properties = new Properties();
            properties.load(in);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    public static String getString(Properties properties,String key){
        return getString(properties,key,"");
    }
    public static String getString(Properties properties,String key,String defaultValue){
        return properties.containsKey(key) ? defaultValue : properties.getProperty(key);
    }
}
