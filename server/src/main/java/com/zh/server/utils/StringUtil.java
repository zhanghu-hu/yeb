package com.zh.server.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huzhang
 * @description 对字符串做常见处理
 * @date 2021-08-29
 */
public class StringUtil {

    /**
     * 将字符串转成整型集合
     *
     * @param str   待转字符串
     * @param split 分隔符
     * @return
     */
    public static List<Integer> StringToIntegers(String str, String split) {
        if (str == null)
            return null;
        List<Integer> result = new ArrayList<>();
        //StringUtils具有缓存效果，适合多次调用的情况
        String[] split1 = StringUtils.split(str, split);
        for (int i = 0; i < split1.length; i++) {
            result.add(Integer.parseInt(split1[i]));
        }
        return result;
    }
}
