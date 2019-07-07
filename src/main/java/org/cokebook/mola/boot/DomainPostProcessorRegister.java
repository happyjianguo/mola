package org.cokebook.mola.boot;

import org.cokebook.mola.DomainPostProcessor;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * Domain Spring import selector
 * Note: 该类的作用是提供按条件决定引入 DDD 增强的功能
 *
 * @author wuming
 * @date 2019/7/7 16:16
 */
public class DomainPostProcessorRegister implements ImportSelector {
    public static final String TRUE = "TRUE";

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotation = importingClassMetadata.getAnnotationAttributes(EnableDDD.class.getName(), true);
        boolean enable = TRUE.equalsIgnoreCase((String) annotation.get("value"));
        if (enable) {
            return new String[]{
                    DomainPostProcessor.class.getName()
            };
        }
        return new String[0];
    }
}
