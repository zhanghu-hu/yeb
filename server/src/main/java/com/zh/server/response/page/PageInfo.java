package com.zh.server.response.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {

    /**
     * 总数量
     */
    private Long total;

    /**
     * 数据
     */
    private List<?> pageData;
}
