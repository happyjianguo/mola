package org.cokebook.mola.spring;

import org.cokebook.mola.Model;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by xm on 2019/10/1.
 */
public class ModelPopulateBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes enableAutoModelPopulate = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableAutoModelPopulate.class.getName(), false));
        if (enableAutoModelPopulate.getBoolean("value")) {
            ModelPopulateConfig.registerRepositoryBeanPostProcessorIfNecessary(registry);
        }
        final Class<? extends Annotation>[] classes = (Class<? extends Annotation>[]) enableAutoModelPopulate.getClassArray("annotations");
        if (classes.length > 0) {
            if (classes.length == 1 && classes[0] == Model.class) {
                return;
            }
            ModelPopulateConfig.forceRepositoryBeanPostProcessorToAnnotations(registry, Arrays.asList(classes));
        }
    }

}
