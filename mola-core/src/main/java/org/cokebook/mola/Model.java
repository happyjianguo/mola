package org.cokebook.mola;

import java.lang.annotation.*;

/**
 * 模型对象标识注解
 * <p>
 * Note: 该注解是一个标识类型注解, 用于声明实体类是一个充血的领域模型类
 *
 * @date 2019/7/2 17:24
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
}
