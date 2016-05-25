package org.loong4j.framework;

import org.apache.commons.lang3.ArrayUtils;
import org.loong4j.framework.bean.Data;
import org.loong4j.framework.bean.Handler;
import org.loong4j.framework.bean.Param;
import org.loong4j.framework.bean.View;
import org.loong4j.framework.helper.BeanHelper;
import org.loong4j.framework.helper.ConfigHelper;
import org.loong4j.framework.helper.ControllerHelper;
import org.loong4j.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Loong on 2016/5/25.
 */

@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    public void init(ServletConfig servletConfig) {
        //初始化helper类
        HelperLoader.init();

        //获取servletContext对象（用户注册servlet）
        ServletContext servletContext = servletConfig.getServletContext();
        //注册处理jsp的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        //注册处理静态资源的默认servlet
        ServletRegistration defualtServlet = servletContext.getServletRegistration("default");
        defualtServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取请求方法和请求路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();

        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);

        if (handler != null) {
            //获取Controller类及其bean实例
            Class<?> controllerCLass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerCLass);

            //创建请求参数对象
            Map<String, Object> paramMap = new HashMap<String, Object>();
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();

                String paramValue = request.getParameter(paramName);

                paramMap.put(paramName, paramValue);
            }

            String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
            if (StringUtil.isNotEmpty(body)) {
                String[] params = StringUtil.splitString(body, "&");
                if (ArrayUtils.isNotEmpty(params)) {
                    for (String param : params) {
                        String[] array = StringUtil.splitString(param, "=");
                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);

            //调用Action方法
            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);

            //处理Action方法返回值
            if (result instanceof View) {
                View view = (View) result;
                String path = view.getPath();
                if (StringUtil.isNotEmpty(path)) {
                    response.sendRedirect(request.getContextPath() + path);
                } else {
                    Map<String, Object> model = view.getModel();
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                           request.setAttribute(entry.getKey(),entry.getValue());
                    }

                    request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(request,response);
                }
            } else if (result instanceof Data) {
                Data data = (Data)result;
                Object model = data.getModel();
                if(model !=null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            } else {
                System.out.println("result 未识别。。。result="+result.toString());
            }
        }
    }
}
