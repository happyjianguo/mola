package org.cokebook.mola.spring;

import org.cokebook.mola.Model;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by xm on 2019/10/1.
 */
public class ModelPopulateConfig {

    private static final String REPOSITORY_BEAN_POST_PROCESSOR_BEAN_NAME = RepositoryBeanPostProcessor.class.getPackage().getName() + ".innerRepositoryBeanPostProcessor";

    public static void registerRepositoryBeanPostProcessorIfNecessary(BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(REPOSITORY_BEAN_POST_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition beanDefinition = new RootBeanDefinition(RepositoryBeanPostProcessor.class);
            beanDefinition.setRole(2);
            beanDefinition.getPropertyValues().add("annotations", new HashSet<>(Arrays.asList(Model.class)));
            registry.registerBeanDefinition(REPOSITORY_BEAN_POST_PROCESSOR_BEAN_NAME, beanDefinition);
        }
    }

    public static void forceRepositoryBeanPostProcessorToAnnotations(BeanDefinitionRegistry registry, List<Class<? extends Annotation>> annotations) {
        BeanDefinition beanDefinition = registry.getBeanDefinition(REPOSITORY_BEAN_POST_PROCESSOR_BEAN_NAME);
        if (beanDefinition != null) {
            Set<Class<? extends Annotation>> pv = (Set<Class<? extends Annotation>>) beanDefinition.getPropertyValues().get("annotations");
            if (annotations != null) {
                Set<Class<? extends Annotation>> targetAnnotations = new HashSet<>(pv);
                targetAnnotations.addAll(annotations);
                beanDefinition.getPropertyValues().add("annotations", targetAnnotations);
            }
        }
    }
}
