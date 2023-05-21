package com.zh.server.server.impl;

import com.zh.server.entity.Position;
import com.zh.server.mapper.yyb.PositionMapper;
import com.zh.server.server.PositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<Position> listALL() {
        return positionMapper.listALL();
    }
}
