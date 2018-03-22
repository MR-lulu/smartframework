package mapping;

import com.alibaba.fastjson.JSON;
import config.ConfigHepler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import utils.CodeUtil;
import utils.HelperLoader;
import utils.StreamUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by blue on 2017/12/14.
 */
@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
@Slf4j
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关的helper类
        super.init();
        HelperLoader.init();
        //获取Servlet上下文对象
        ServletContext context = servletConfig.getServletContext();
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHepler.getAppJspPath() + "*");
        ServletRegistration defaultServlet = context.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHepler.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath =  req.getPathInfo();
        req.setCharacterEncoding("utf-8");

        //获取Handler 处理器
        Handler handler = HandlerMapping.getHandler(requestMethod,requestPath);
        if (handler != null){
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<>();
            /*
             * Enumeration接口中定义了一些方法，通过这些方法可以枚举（一次获得一个）对象集合中的元素。
             *这种传统接口已被迭代器取代，虽然Enumeration 还未被遗弃，但在现代代码中已经被很少使用了。
             *尽管如此，它还是使用在诸如Vector和Properties这些传统类所定义的方法中，
             *除此之外，还用在一些API类，并且在应用程序中也广泛被使用。
             */
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String paramName = parameterNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            String body = CodeUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if (StringUtils.isNotBlank(body)){
                String[] params = body.split("&");
                if (ArrayUtils.isNotEmpty(params)){
                    Arrays.stream(params).forEach(param->{
                        String[] array = param.split("=");
                        if (ArrayUtils.isNotEmpty(array) && array.length==2){
                            paramMap.put(array[0],array[1]);
                        }
                    });
                    log.info("参数：{}",paramMap);
                }
            }
            Param param = new Param(paramMap);
            //执行handler
            Object rusult = handler.doHandler(param);
            //返回jsp页面
            if (rusult instanceof View){
                View view = (View) rusult;
                String path = view.getPath();
                if (StringUtils.isNotBlank(path)){
                    if (path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath()+path);
                    } else {
                        Map<String,Object> model = view.getModel();
                        if (model!=null) {
                            model.forEach((k, v) -> {
                                req.setAttribute(k, v);
                            });
                        }
                        req.getRequestDispatcher(ConfigHepler.getAppJspPath()+path).forward(req,resp);
                    }
                }
            } else if (rusult instanceof Data){
                Data data = (Data) rusult;
                Object model = data.getModel();
                if (model != null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("UTF-8");
                    PrintWriter writer = resp.getWriter();
                    //转为json格式
                    String json = JSON.toJSONString(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
