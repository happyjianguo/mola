package org.cokebook.mola.spring;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启自动填充模型
 *
 * @author fengzao
 * @date 2019/7/7 16:14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableAspectJAutoProxy
@Import(RepositoryInterceptorRegister.class)
public @interface EnableAutoPopulateModel {

    String value() default "true";

}
