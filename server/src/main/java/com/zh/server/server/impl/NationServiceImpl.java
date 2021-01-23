package com.zh.server.server.impl;

import com.zh.server.entity.Nation;
import com.zh.server.mapper.NationMapper;
import com.zh.server.server.NationService;
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
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements NationService {

}
