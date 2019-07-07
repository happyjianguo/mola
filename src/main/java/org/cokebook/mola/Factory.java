package org.cokebook.mola;

/**
 * @author wuming
 * @date 2019/7/5 15:07
 */
public interface Factory<T> {
    /**
     * 构建对象
     *
     * @return
     */
    T build();
}
