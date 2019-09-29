package org.cokebook.mola;

/**
 * 模型对象构建器
 *
 * @date 2019/9/29 14:12
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
