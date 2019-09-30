package org.cokebook.mola.injector.processer;

import org.cokebook.mola.injector.Injector;
import org.cokebook.mola.utils.Clazz;

/**
 * @date 2019/9/30 14:47
 */
public class ArrayProcessor implements Injector.Processor {

    @Override
    public void process(Object model, Injector injector) {
        if (model.getClass().isArray() && !Clazz.isPrimitive(model)) {
            Object[] modelArray = (Object[]) model;
            for (Object item : modelArray) {
                injector.inject(item);
            }
        }
    }
}
