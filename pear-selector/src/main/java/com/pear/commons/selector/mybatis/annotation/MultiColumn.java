package com.pear.commons.selector.mybatis.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by littlehui on 2016/10/23 0023.
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MultiColumn {

    String[] value() default "";

    String sign() default "";
}
