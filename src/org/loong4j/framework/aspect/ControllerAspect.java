package org.loong4j.framework.aspect;

import org.loong4j.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * 拦截Controller所有方法
 * Created by Loong on 2016/7/11.
 */
public class ControllerAspect extends AspectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.debug("-------- begin --------");
        LOGGER.debug(String.format("class:%s", cls.getName()));
        LOGGER.debug(String.format("method:%s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object reslut) throws Throwable {
        LOGGER.debug(String.format("time:%dms", System.currentTimeMillis() - begin));
        LOGGER.debug("-------- end --------");
    }
}
