package org.cokebook.mola.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @date 2019/7/2 17:20
 */

@Slf4j
@Aspect
public class RepositoryBeanPostProcessor implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Around("@within(org.springframework.stereotype.Repository)")
    public Object inject(ProceedingJoinPoint point) throws Throwable {
        Object model = point.proceed();
        if (model == null) {
            return model;
        }
        new PropertiesInjector(applicationContext.getAutowireCapableBeanFactory()).inject(model);
        return model;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


}
