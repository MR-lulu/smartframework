package config;

import org.apache.commons.lang3.StringUtils;
import utils.PropsUtil;

import java.net.URL;
import java.util.Properties;

/**
 * Created by blue on 2017/12/14.
 */
public final class ConfigHepler {
    private static final Properties CONFIG_PROPS =
            PropsUtil.loadProps(ConfigConstans.CONFIG_FILE);

    public static String getJdbcDriver(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstans.JDBC_DRIVER);
    }
    public static String getJbcUrl(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstans.JDBC_URL);
    }
    public static String getJdbcUsername(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstans.JDBC_USERNAME);
    }
    public static String getJdbcPassword(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstans.JDBC_PASSWORD);
    }

    public static String getAppBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstans.APP_BASE_PACKAGE);
    }
    //jsp路径
    public static String getAppJspPath(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstans.APP_JSP_PATH,
                "/WEB-INF/view/");
    }
    //静态资源路径
    public static String getAppAssetPath(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstans.APP_ASSET_PATH,
                "/asset/");
    }
}
