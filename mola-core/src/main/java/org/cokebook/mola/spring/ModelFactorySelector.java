package org.cokebook.mola.spring;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * ModelFactorySelector
 * Note: 该类的作用是提供按条件决定引入 DDD 增强的功能
 *
 * @date 2019/7/7 16:16
 */
public class ModelFactorySelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotation = importingClassMetadata.getAnnotationAttributes(EnableAutoModelPopulate.class.getName(), true);
        boolean enable = Boolean.TRUE.equals(annotation.get("value"));
        if (enable) {
            return new String[]{
                    SimpleModelFactory.class.getName()
            };
        }
        return new String[0];
    }
}
