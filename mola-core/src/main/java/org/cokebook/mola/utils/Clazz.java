package org.cokebook.mola.utils;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Set;

/**
 * @date 2019/9/30 14:50
 */
public final class Clazz {

    /**
     * 基本类型集合
     */
    public static final Set<Class> PRIMITIVE_TYPES = Collections.unmodifiableSet(Sets.newHashSet(
            boolean[].class, byte[].class, char[].class, short[].class,
            int[].class, long[].class, float[].class, double[].class,
            boolean.class, byte.class, char.class, short.class,
            int.class, long.class, float.class, double.class
    ));

    /**
     * Java  基本类型包装类
     */
    private static final Set<Class> JAVA_WRAPPER_CLASSES = Collections.unmodifiableSet(Sets.newHashSet(
            Boolean.class, Short.class, Integer.class, Long.class, Byte.class, Character.class,
            Double.class, Float.class
    ));

    /**
     * 判断对象类型是否基本类型
     * Note: 对象类型判定的基本条件 : 对象类型属于8种基本类型OR对应的基本类型数组之一
     *
     * @param object
     * @return
     */
    public static final boolean isPrimitive(Object object) {
        if (object == null) {
            return false;
        }
        return PRIMITIVE_TYPES.contains(object.getClass());
    }

    public static final boolean isJavaWrapper(Object object) {
        if (object == null) {
            return false;
        }
        return JAVA_WRAPPER_CLASSES.contains(object.getClass());
    }


}
