package org.loong4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.loong4j.framework.annotation.Inject;
import org.loong4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by Loong on 2016/1/13.
 * 依赖注入助手类
 */
public final class IocHelper {
    static {
        //获取所有的Bean类与Bean实例之间的映射关系
        Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtils.sizeIsEmpty(beanMap)){
            //遍历BeanMap
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass=beanEntry.getKey();
                Object beanInstance=beanEntry.getValue();

                Field[] beanFields=beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)){
                    for (Field beanField : beanFields) {
                        if(beanField.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass=beanField.getType();
                            Object beanFieldInstance =beanMap.get(beanFieldClass);
                            if (beanFieldInstance!=null){
                                ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
