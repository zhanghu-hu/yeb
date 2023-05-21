package com.zh.server.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.server.entity.Role;
import com.zh.server.entity.User;
import com.zh.server.request.user.LoginRequest;
import com.zh.server.response.common.ResponseBase;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZH
 * @since 2021-01-20
 */
public interface UserService extends IService<User> {

    /**
     * 登录
     * @param loginRequest
     * @param request
     * @return
     */
    ResponseBase login(LoginRequest loginRequest, HttpServletRequest request);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 注册用户
     * @param user
     * @return
     */
    ResponseBase add(User user);

    /**
     * 根据用户ID查询角色列表
     * 与请求菜单的角色做对比，看看用户请求的合法性
     * @param adminId
     * @return
     */
    List<Role> getRolesByAdminId(Integer adminId);

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    List<User> getAllUsers(String keywords);

    /**
     * 获取所有操作员
     * @param keywords
     * @return
     */
    List<User> getAllAdmins(String keywords);

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    ResponseBase updateAdminRole(Integer adminId,Integer[] rids);
}
