package org.cokebook.mola;

/**
 * 模型对象工厂
 *
 * @date 2019/9/29 14:02
 */
public interface ModelFactory {


    /**
     * 创建新模型对象
     *
     * @param builder
     * @param <T>
     * @return
     */
    public <T> T create(ModelBuilder<T> builder);

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
