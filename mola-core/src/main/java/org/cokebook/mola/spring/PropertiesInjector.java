package org.cokebook.mola.spring;

import com.google.common.collect.Sets;
import org.cokebook.mola.Model;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.util.ReflectionUtils;

import java.util.*;

/**
 * @date 2019/9/30 11:25
 */
public class PropertiesInjector {

    private AutowireCapableBeanFactory beanFactory;

    public PropertiesInjector(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * Java  基本类型包装类
     */
    private final Set<Class> JAVA_WRAPPER_CLASSES = Collections.unmodifiableSet(Sets.newHashSet(
            Boolean.class, Short.class, Integer.class, Long.class, Byte.class, Character.class,
            Double.class, Float.class
    ));

    /**
     * Java 常用类: 该字段主要用于辅助做一些快速过滤
     */
    private final Set<Class> JAVA_COMMON_CLASSES = Collections.unmodifiableSet(Sets.newHashSet(
            Class.class, String.class, Date.class, Object.class, StringBuffer.class, Thread.class,
            Void.class, Package.class
    ));
    /**
     * 基本类型集合
     */
    private final Set<Class> PRIMITIVE_TYPES = Collections.unmodifiableSet(Sets.newHashSet(
            boolean[].class, byte[].class, char[].class, short[].class,
            int[].class, long[].class, float[].class, double[].class,
            boolean.class, byte.class, char.class, short.class,
            int.class, long.class, float.class, double.class
    ));

    private Set<Object> processed = new HashSet<>();

    public void inject(Object model) {
        if (model == null || processed.contains(model)) {
            return;
        }
        processed.add(model);
        final Class clazz = model.getClass();
        if (PRIMITIVE_TYPES.contains(clazz) ||
                JAVA_WRAPPER_CLASSES.contains(clazz) ||
                JAVA_COMMON_CLASSES.contains(clazz)) {
            return;
        }
        handleOnArray(model);
        handleOnCollection(model);
        handleOnMap(model);
        handleOnModel(model);
    }

    private void handleOnModel(Object model) {
        if (model.getClass().isAnnotationPresent(Model.class)) {
            beanFactory.autowireBean(model);
            ReflectionUtils.doWithFields(model.getClass(), (field) -> {
                field.setAccessible(true);
                inject(field.get(model));
            });
        }
    }

    private void handleOnArray(Object model) {
        if (model.getClass().isArray() && !PRIMITIVE_TYPES.contains(model.getClass())) {
            Object[] modelArray = (Object[]) model;
            for (Object item : modelArray) {
                inject(item);
            }
        }
    }

    private void handleOnCollection(Object model) {
        if (model instanceof Collection) {
            for (Object obj : (Collection) model) {
                inject(obj);
            }
        }
    }

    private void handleOnMap(Object model) {
        if (model instanceof Map) {
            for (Object key : ((Map) model).keySet()) {
                inject(key);
            }
            for (Object value : ((Map) model).values()) {
                inject(value);
            }
        }
    }

}
