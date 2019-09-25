package org.cokebook.mola;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author fengzao
 * @date 2019/9/24 17:38
 */
public class ModelFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public <T> T create(ModelBuilder<T> builder) {
        T model = builder.build();
        if (model != null && AnnotationUtils.findAnnotation(model.getClass(), Model.class) != null) {
            applicationContext.getAutowireCapableBeanFactory().autowireBean(model);
        }
        return model;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 模型对象构建器
     *
     * @param <T>
     */
    @FunctionalInterface
    public interface ModelBuilder<T> {
        /**
         * build model
         *
         * @return
         */
        T build();
    }
}
