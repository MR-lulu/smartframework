package mapping;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import utils.BeanHelper;
import utils.CodeUtil;
import utils.HelperLoader;
import utils.StreamUtil;

import javax.servlet.*;
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
public class DispatcherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        //初始化相关的helper类
        HelperLoader.init();
        //获取Servlet上下文对象
        ServletContext context = servletConfig.getServletContext();
        ServletRegistration jspServlet = context.getServletRegistration("jsp");
        jspServlet.addMapping();//TODO
        ServletRegistration defaultServlet = context.getServletRegistration("default");
        defaultServlet.addMapping();//TODO
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath =  req.getPathInfo();

        //获取Handler 处理器
        Handler handler = HandlerMapping.getHandler(requestMethod,requestPath);
        if (handler != null){
            //创建请求参数对象
            Map<String,Object> paramMap = new HashMap<>();
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
                        model.forEach((k,v)->{
                           req.setAttribute(k,v);
                        });
                        //TODO req.getRequestDispatcher(ConfigHelper.getAppJspPath())
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
