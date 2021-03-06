package utils;

import annotation.Controller;
import annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 自动扫描工具类
 * Created by lulu on 2017/11/24.
 */
@Slf4j
public class ClassUtil {
    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类（将isInitialized设置为false可以提高加载类的性能）
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInitialized){
        Class<?> cls;
        try {
            cls = Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("加载类出错！");
            throw new RuntimeException(e);
        }
        return cls;
    }

    /**
     * 获取包下所有类的Set集合
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet = new HashSet<>();
        try {
            //获取文件中所有资源的url
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    //判断url的协议（1.file://.... 2.jar://...
                    if (protocol.equals("file")){
                        String packagePath = url.getPath().replaceAll("%20","");//解决路径中含有空格的情况
                        packagePath =  URLDecoder.decode(packagePath,"utf-8"); //解决路径包含中文的情况
                        addClass(classSet,packagePath,packageName);
                    } else if (protocol.equals("jar")){
                        JarURLConnection jarURLConnection = (JarURLConnection)
                                url.openConnection();
                        if (jarURLConnection != null) {
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile != null){
                                Enumeration<JarEntry> jarEntries = jarFile.entries();
                                while (jarEntries.hasMoreElements()){
                                    JarEntry jarEntry = jarEntries.nextElement();
                                    String jarEntryName = jarEntry.getName();
                                    if (jarEntryName.equals(".class")){
                                        String className = jarEntryName.substring(0,
                                                jarEntryName.lastIndexOf("."))
                                                .replaceAll("/",".");
                                        doAddClass(classSet,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            log.debug("扫描包出错！",e);
            throw new RuntimeException(e);
        }
        return classSet;
    }

    private static void  addClass(Set<Class<?>> classSet,String packagePath,String packageName){
        //获取包下所有的文件，并进行过滤（filenameFilter)
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) ||
                        file.isDirectory();
            }
        });
        for (File file : files){
            String fileName = file.getName();
            //如果是文件则处理，若是文件夹则递归处理
            if (file.isFile()){
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if (StringUtils.isNotBlank(packageName)){
                    className = packageName + "." +className;
                }
                doAddClass(classSet,className);
            } else {
                String subPackagePath = fileName;
                if (StringUtils.isNotBlank(packagePath)){
                    subPackagePath = packagePath +"/" +subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtils.isNotBlank(packageName)){
                    subPackageName = packageName +"."+subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    /**
     * 将使用了@Controller或者@Service注解的类加到Set集合中
     * @param classSet
     * @param className
     */
    private static void doAddClass(Set<Class<?>> classSet,String className){
        Class<?> cls = loadClass(className,false);
        classSet.add(cls);
    }
}
