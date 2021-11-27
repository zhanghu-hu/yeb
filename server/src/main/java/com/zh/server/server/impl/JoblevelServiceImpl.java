package com.zh.server.server.impl;

import com.zh.server.entity.Joblevel;
import com.zh.server.mapper.yyb.JoblevelMapper;
import com.zh.server.server.JoblevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZH
 * @since 2021-01-22
 */
@Service
public class JoblevelServiceImpl extends ServiceImpl<JoblevelMapper, Joblevel> implements JoblevelService {

    @Autowired
    private JoblevelMapper joblevelMapper;

    @Override
    public List<Joblevel> listALL() {
        return joblevelMapper.listALL();
    }
}
