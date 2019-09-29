package org.cokebook.mola.spring;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 自动填充模型 spring import ext annotation
 *
 * @date 2019/7/7 16:14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableAspectJAutoProxy
@Import(ModelPopulateRegister.class)
public @interface EnableAutoModelPopulate {

    boolean value() default true;

}
