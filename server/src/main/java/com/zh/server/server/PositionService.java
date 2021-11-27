package com.zh.server.server;

import com.zh.server.entity.Position;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
public interface PositionService extends IService<Position> {

    /**
     * 获取全部职位
     * @return
     */
    List<Position> listALL();
}
