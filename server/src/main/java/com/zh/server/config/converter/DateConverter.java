package com.zh.server.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 全局日期转换
 * 返回前端的字段前仍要加上@JsonFormat标签
 * 时间会被默认转成yyyy-MM-dd形式，慎用
 * @author ZH
 * @date 2021-01-27
 */
@Component
public class DateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String s) {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));//s是时间字符串
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
