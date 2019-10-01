package org.cokebook.mola.spring;

import com.google.common.collect.Sets;
import org.cokebook.mola.Model;
import org.cokebook.mola.injector.Injector;
import org.cokebook.mola.injector.processer.ArrayProcessor;
import org.cokebook.mola.injector.processer.CollectionProcessor;
import org.cokebook.mola.injector.processer.MapProcessor;
import org.cokebook.mola.utils.Clazz;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @date 2019/9/30 11:25
 */
public class SpringPropertiesInjector implements Injector {

    private ApplicationContext applicationContext;
    private AutowireCapableBeanFactory beanFactory;
    private List<Processor> innerInjectorProcessor;
    private List<Processor> customerInjectorProcessor;
    private final Set<Class<? extends Annotation>> annotations = new HashSet<>(4);

    public SpringPropertiesInjector(ApplicationContext applicationContext) {
        this(applicationContext, new HashSet<>(Arrays.asList(Model.class)));
    }

    public SpringPropertiesInjector(ApplicationContext applicationContext, Set<Class<? extends Annotation>> markAnnotations) {
        this.applicationContext = applicationContext;
        this.beanFactory = applicationContext.getAutowireCapableBeanFactory();
        this.innerInjectorProcessor = Collections.unmodifiableList(
                Arrays.asList(new ArrayProcessor(), new CollectionProcessor(),
                        new MapProcessor(), new ModelProcessor())
        );
        this.customerInjectorProcessor = applicationContext.getBeansOfType(Injector.Processor.class).values().stream()
                .filter(processor -> !innerInjectorProcessor.contains(processor))
                .collect(Collectors.toList());
        this.annotations.add(Model.class);
        if (markAnnotations != null) {
            this.annotations.addAll(markAnnotations);
        }
    }


    /**
     * Java 常用类: 该字段主要用于辅助做一些快速过滤
     */
    private final Set<Class> JAVA_COMMON_CLASSES = Collections.unmodifiableSet(Sets.newHashSet(
            Class.class, String.class, Date.class, Object.class, StringBuffer.class, Thread.class,
            Void.class, Package.class
    ));

    private Set<Object> processed = new HashSet<>();

    @Override
    public void inject(Object model) {
        if (model == null || processed.contains(model)) {
            return;
        }
        processed.add(model);
        final Class clazz = model.getClass();
        if (Clazz.isPrimitive(model) || Clazz.isJavaWrapper(model)
                || JAVA_COMMON_CLASSES.contains(clazz)) {
            return;
        }
        for (Processor customerProcessor : this.customerInjectorProcessor) {
            customerProcessor.process(model, this);
        }
        for (Processor innerProcessor : this.innerInjectorProcessor) {
            innerProcessor.process(model, this);
        }
    }

    public class ModelProcessor implements Injector.Processor {

        @Override
        public void process(Object model, Injector injector) {
            if (clazzMatchAnnotations(model.getClass())) {
                beanFactory.autowireBean(model);
                ReflectionUtils.doWithFields(model.getClass(), (field) -> {
                    field.setAccessible(true);
                    inject(field.get(model));
                });
            }
        }
    }

    /**
     * 判断特定 Clazz 是否存在特定标记注解之一 {@link #annotations}
     *
     * @param clazz
     * @return
     */
    private boolean clazzMatchAnnotations(Class clazz) {
        for (Class<? extends Annotation> markAnnotationClazz : annotations) {
            if (clazz.getAnnotation(markAnnotationClazz) != null) {
                return true;
            }
        }
        return false;
    }


}
