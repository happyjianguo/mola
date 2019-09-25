package org.cokebook.mola;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模型对象标识注解 : 该注解用于声明实体类是一个充血的领域模型类
 *
 * @author fengzao
 * @date 2019/7/2 17:24
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Model {
}
