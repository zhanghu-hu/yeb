package com.zh.server.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.server.entity.MenuRole;
import com.zh.server.mapper.yyb.MenuRoleMapper;
import com.zh.server.server.MenuRoleService;
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
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    @Override
    public String updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if (mids == null || mids.length==0){
            return "更新成功";
        }
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        if (mids.length == result){
            return "更新成功";
        }
        return "更新失败";
    }

    @Override
    public List<MenuRole> listByRid(Integer rid) {
        return menuRoleMapper.listByRid(rid);
    }
}
