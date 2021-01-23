package com.zh.server.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.server.config.BasicConstants;
import com.zh.server.config.security.JwtToken;
import com.zh.server.entity.Menu;
import com.zh.server.entity.User;
import com.zh.server.mapper.MenuMapper;
import com.zh.server.mapper.UserMapper;
import com.zh.server.request.user.LoginRequest;
import com.zh.server.response.common.ResponseBase;
import com.zh.server.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ZH
 * @since 2021-01-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDetailsService userDetailsService;//登录实现

    @Autowired
    private PasswordEncoder passwordEncoder;//security自带加密手法,SecurityConfig有引入

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private JwtToken jwtToken;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String authorization;

    @Override
    public ResponseBase login(LoginRequest loginRequest, HttpServletRequest request) {
        //校验验证码
        String captcha=(String) request.getSession().getAttribute("captcha");
        //!captcha.equalsIgnoreCase(loginRequest.getCode()) 忽略大小写去比较字符是否相等
        if (StringUtils.isEmpty(loginRequest.getCode())||!captcha.equalsIgnoreCase(loginRequest.getCode())){
            return ResponseBase.failed(BasicConstants.HttpStatus.NO_CODE.code,BasicConstants.HttpStatus.NO_CODE.msg);
        }

        //登录 userDetailsService的loadUserByUsername方法在securityConfig配置类里有被重写,UserDetails=User
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        if (userDetails == null || !passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            return ResponseBase.failed(BasicConstants.HttpStatus.USERNAME_OR_PASSWORD_ERROR.code, BasicConstants.HttpStatus.USERNAME_OR_PASSWORD_ERROR.msg);
        }
        if (!userDetails.isEnabled()) {
            return ResponseBase.failed(BasicConstants.HttpStatus.FORBID_USER.code, BasicConstants.HttpStatus.FORBID_USER.msg);
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtToken.getToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put(authorization, token);
        tokenMap.put("tokenHead",tokenHead);
        return ResponseBase.success(tokenMap);
    }

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("t_username",username);
        return userMapper.selectOne(wrapper);
    }

    @Override
    public ResponseBase add(User user) {
        User userExist=userMapper.selectOne(new QueryWrapper<User>().eq("t_username",user.getTUsername()));
        if (userExist!=null){
            return ResponseBase.failed(BasicConstants.HttpStatus.USERNAME_EXIST.code,BasicConstants.HttpStatus.USERNAME_EXIST.msg);
        }
        user.setTPassword(passwordEncoder.encode(user.getTPassword()));
        userMapper.insert(user);
        return ResponseBase.success(user);
    }

}
