package com.zh.server.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

/**
 * @description 加载全国测试点位数据进入内存（点位是随机产生的，并非真实数据）
 * @author ZH
 * @date 2021-8-13
 */
@Order(3)//规定重启时执行顺序，越小越靠前
public class NationwidePointTask implements CommandLineRunner {
    /**
     * 重启时执行
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

    }
}
