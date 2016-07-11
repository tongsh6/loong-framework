package org.loong4j.framework;

import org.loong4j.framework.helper.*;
import org.loong4j.framework.util.ClassUtil;

/**
 * Created by loong on 2016/3/15.
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }

    public static void main(String[] args) {
        init();
    }
}
