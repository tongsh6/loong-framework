package org.loong4j.framework;

import org.loong4j.framework.helper.BeanHelper;
import org.loong4j.framework.helper.ClassHelper;
import org.loong4j.framework.helper.ControllerHelper;
import org.loong4j.framework.helper.IocHelper;
import org.loong4j.framework.util.ClassUtil;

/**
 * Created by loong on 2016/3/15.
 */
public final class HelperLoader {
    public static void init(){
        Class<?>[]classList={ClassHelper.class,
        BeanHelper.class,
        IocHelper.class,
        ControllerHelper.class
        };
        for (Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName());
        }
    }
}
