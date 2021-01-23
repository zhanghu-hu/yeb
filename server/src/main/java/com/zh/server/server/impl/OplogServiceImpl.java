package com.zh.server.server.impl;

import com.zh.server.entity.Oplog;
import com.zh.server.mapper.OplogMapper;
import com.zh.server.server.OplogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Service
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements OplogService {

}
