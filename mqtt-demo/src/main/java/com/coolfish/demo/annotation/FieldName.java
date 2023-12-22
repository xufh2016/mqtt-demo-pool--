package com.coolfish.demo.annotation;

import java.lang.annotation.*;

/**
 * @className: FieldName
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/22
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldName {
    String value();
}
