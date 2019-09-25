package org.cokebook.mola;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.ReflectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author fengzao
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
        new PropertiesInjector().inject(model);
        return model;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public class PropertiesInjector {
        private final Set<Class> JAVA_WRAPPER_CLASSES = Sets.newHashSet(String.class, Short.class, Integer.class, Long.class, Byte.class, Character.class, Double.class, Float.class);
        private Set<Object> processed = new HashSet<>();

        public void inject(Object model) {
            if (model == null || processed.contains(model)) {
                return;
            }
            processed.add(model);
            Class clazz = model.getClass();
            if (clazz.isPrimitive() || JAVA_WRAPPER_CLASSES.contains(clazz)) {
                return;
            }
            if (model instanceof Collection) {
                for (Object obj : (Collection) model) {
                    inject(obj);
                }
            }
            if (model instanceof Map) {
                for (Object key : ((Map) model).keySet()) {
                    inject(key);
                }
                for (Object value : ((Map) model).values()) {
                    inject(value);
                }
            }
            if (model.getClass().isAnnotationPresent(Model.class)) {
                applicationContext.getAutowireCapableBeanFactory().autowireBean(model);
                ReflectionUtils.doWithFields(model.getClass(), (field) -> {
                    field.setAccessible(true);
                    inject(field.get(model));
                });
            }
        }

    }
}
