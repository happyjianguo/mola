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
 * @author wuming
 * @date 2019/7/2 17:20
 */

@Slf4j
@Aspect
public class DomainPostProcessor implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Around("@within(org.springframework.stereotype.Repository)")
    public Object inject(ProceedingJoinPoint point) throws Throwable {
        Object domain = point.proceed();
        if (domain == null) {
            return domain;
        }
        new PropertiesInjector().inject(domain);
        return domain;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public class PropertiesInjector {
        private final Set<Class> JAVA_WRAPPER_CLASSES = Sets.newHashSet(String.class, Short.class, Integer.class, Long.class, Byte.class, Character.class, Double.class, Float.class);
        private Set<Object> processed = new HashSet<>();

        public void inject(Object domain) {
            if (domain == null || processed.contains(domain)) {
                return;
            }
            processed.add(domain);
            Class clazz = domain.getClass();
            if (clazz.isPrimitive() || JAVA_WRAPPER_CLASSES.contains(clazz)) {
                return;
            }
            if (domain instanceof Collection) {
                for (Object obj : (Collection) domain) {
                    inject(obj);
                }
            }
            if (domain instanceof Map) {
                for (Object key : ((Map) domain).keySet()) {
                    inject(key);
                }
                for (Object value : ((Map) domain).values()) {
                    inject(value);
                }
            }
            if (domain.getClass().isAnnotationPresent(Domain.class)) {
                applicationContext.getAutowireCapableBeanFactory().autowireBean(domain);
                ReflectionUtils.doWithFields(domain.getClass(), (field) -> {
                    field.setAccessible(true);
                    inject(field.get(domain));
                });
            }
        }

    }
}
