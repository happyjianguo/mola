package org.cokebook.mola.injector;

/**
 * 注入器
 * Note: 模型对象属性注入器
 *
 * @date 2019/9/30 14:09
 */
public interface Injector {

    /**
     * 模型对象注入属性
     *
     * @param model
     */
    void inject(Object model);


    /**
     * 对象处理器
     */
    interface Processor {
        /**
         * 模型对象处理
         *
         * @param model
         * @param injector
         */
        void process(Object model, Injector injector);
    }
}
