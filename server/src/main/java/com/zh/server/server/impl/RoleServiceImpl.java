package com.zh.server.server.impl;

import com.zh.server.entity.Role;
import com.zh.server.mapper.RoleMapper;
import com.zh.server.server.RoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
