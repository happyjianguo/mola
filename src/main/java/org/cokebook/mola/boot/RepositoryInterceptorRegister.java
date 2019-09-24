package org.cokebook.mola.boot;

import org.cokebook.mola.RepositoryBeanPostProcessor;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * Model Repository Interceptor Register
 * Note: 该类的作用是提供按条件决定引入 DDD 增强的功能
 *
 * @author wuming
 * @date 2019/7/7 16:16
 */
public class RepositoryInterceptorRegister implements ImportSelector {
    public static final String TRUE = "TRUE";

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> annotation = importingClassMetadata.getAnnotationAttributes(EnableAutoPopulateModel.class.getName(), true);
        boolean enable = TRUE.equalsIgnoreCase((String) annotation.get("value"));
        if (enable) {
            return new String[]{
                    RepositoryBeanPostProcessor.class.getName()
            };
        }
        return new String[0];
    }
}
