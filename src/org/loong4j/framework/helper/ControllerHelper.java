package org.loong4j.framework.helper;

/**
 * Created by Loong   on 2016/3/15.
 */

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.loong4j.framework.annotation.Action;
import org.loong4j.framework.bean.Handler;
import org.loong4j.framework.bean.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 */
public final class ControllerHelper {
    /**
     * 用于存放请求与处理器的映射关系（action map）
     */
    private static final Map<Request,Handler> ACTION_MAP=new HashMap<Request,Handler>();
    static {
        Set<Class<?>> controllerClassSet=ClassHelper.getControllerClassSet();
        if(CollectionUtils.isNotEmpty(controllerClassSet)){
            for(Class<?> controllerClass:controllerClassSet){
                Method[]methods=controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(methods))
                {
                    for (Method method:methods){
                        if(method.isAnnotationPresent(Action.class)){
                            Action action=method.getAnnotation(Action.class);

                            String mapping=action.value();
                            if(mapping.matches("\\w+:/\\w*")){
                                String[]array=mapping.split(":");
                                if(ArrayUtils.isNotEmpty(array)&&array.length==2){
                                    String requestMethod=array[0];
                                    String requestPath=array[1];
                                    Request request=new Request(requestMethod,requestPath);
                                    Handler handler=new Handler(controllerClass,method);
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request=new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
