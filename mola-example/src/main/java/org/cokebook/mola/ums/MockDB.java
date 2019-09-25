package org.cokebook.mola.ums;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟的数据库
 *
 * @author fengzao
 * @date 2019/9/25 17:28
 */
public class MockDB {
    /**
     * 用户id,name 表数据
     */
    public static final Map<Long, String> ID_NAMES;

    static {
        ID_NAMES = new HashMap<Long, String>();
        ID_NAMES.put(1L, "fengzao");
        ID_NAMES.put(2L, "luoshen");
        ID_NAMES.put(3L, "chidian");
        ID_NAMES.put(4L, "futu");
        ID_NAMES.put(5L, "hemu");
    }

}
