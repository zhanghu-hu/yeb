package com.zh.server.utils;

import java.math.BigDecimal;

/**
 * @author : ZH
 * @description : 解决计算时double数据精度缺失问题
 * @date : 2021-08-22
 */
public class NumberUtil {

    /**
     * 保留指定精度的除法运算
     * @param d1 被除数
     * @param d2 除数
     * @param round 小数点位数
     * @return
     */
    public static Double division(Double d1, Double d2,Integer round) {
        BigDecimal bigDecimal1 = BigDecimal.valueOf(d1);
        BigDecimal bigDecimal2 = BigDecimal.valueOf(d2);
        return bigDecimal1.divide(bigDecimal2,round).doubleValue();
    }
}
