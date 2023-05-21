package com.zh.server.server;

import com.zh.server.entity.Joblevel;
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
public interface JoblevelService extends IService<Joblevel> {

    List<Joblevel> listALL();
}
