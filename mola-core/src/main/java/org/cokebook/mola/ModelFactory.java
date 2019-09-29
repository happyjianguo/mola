package org.cokebook.mola;

/**
 * 模型对象工厂接口
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
    <T> T create(ModelBuilder<T> builder);

}
