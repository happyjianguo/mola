package org.cokebook.mola.boot;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wuming
 * @date 2019/7/7 16:14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(DomainPostProcessorRegister.class)
public @interface EnableDDD {

    String value() default "true";

}
