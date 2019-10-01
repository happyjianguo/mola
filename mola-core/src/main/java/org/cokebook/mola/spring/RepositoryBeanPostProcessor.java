package org.cokebook.mola.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.cokebook.mola.injector.Injector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @date 2019/7/2 17:20
 */

@Slf4j
@Aspect
public class RepositoryBeanPostProcessor implements ApplicationContextAware, ModelPopulateInjectorAware {

    private ApplicationContext applicationContext;
    private Set<Class<? extends Annotation>> annotations;

    @Around("@within(org.springframework.stereotype.Repository)")
    public Object inject(ProceedingJoinPoint point) throws Throwable {
        Object model = point.proceed();
        if (model == null) {
            return model;
        }
        getInjector().inject(model);
        return model;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Injector getInjector() {
        if (annotations == null || annotations.isEmpty()) {
            return new SpringPropertiesInjector(applicationContext);
        }
        return new SpringPropertiesInjector(applicationContext, annotations);
    }

    public Set<Class<? extends Annotation>> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Set<Class<? extends Annotation>> annotations) {
        this.annotations = annotations;
    }
}
