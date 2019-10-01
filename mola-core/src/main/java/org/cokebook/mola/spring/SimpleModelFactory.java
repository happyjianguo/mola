package org.cokebook.mola.spring;

import org.cokebook.mola.ModelBuilder;
import org.cokebook.mola.ModelFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @date 2019/9/24 17:38
 */
public class SimpleModelFactory implements ModelFactory, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private ModelPopulateInjectorAware modelPopulateInjectorAware;

    @Override
    public <T> T create(ModelBuilder<T> builder) {
        T model = builder.build();
        if (model != null) {
            modelPopulateInjectorAware.getInjector().inject(model);
        }
        return model;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.modelPopulateInjectorAware = applicationContext.getBean(ModelPopulateInjectorAware.class);
    }


}
