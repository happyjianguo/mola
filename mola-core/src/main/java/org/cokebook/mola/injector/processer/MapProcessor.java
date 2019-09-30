package org.cokebook.mola.injector.processer;

import org.cokebook.mola.injector.Injector;

import java.util.Map;

/**
 * @date 2019/9/30 14:58
 */
public class MapProcessor implements Injector.Processor {

    @Override
    public void process(Object model, Injector injector) {
        if (model instanceof Map) {
            for (Object key : ((Map) model).keySet()) {
                injector.inject(key);
            }
            for (Object value : ((Map) model).values()) {
                injector.inject(value);
            }
        }
    }
}
