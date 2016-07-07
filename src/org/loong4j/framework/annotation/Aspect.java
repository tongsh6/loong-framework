package org.loong4j.framework.annotation;

/**
 * Created by Loong on 2016/7/7.
 */


import java.lang.annotation.*;

/**
 * 切面注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();

}
