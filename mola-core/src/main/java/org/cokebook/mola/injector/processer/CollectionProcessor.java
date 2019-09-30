package org.cokebook.mola.injector.processer;

import org.cokebook.mola.injector.Injector;

import java.util.Collection;

/**
 * @date 2019/9/30 14:46
 */
public class CollectionProcessor implements Injector.Processor {

    @Override
    public void process(Object model, Injector injector) {
        if (model instanceof Collection) {
            for (Object obj : (Collection) model) {
                injector.inject(obj);
            }
        }
    }
}
