package com.zh.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zh.server.config.BasicConstants;
import com.zh.server.response.common.ResponseBase;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当访问接口没有权限时，自定义返回结果
 * @author ZH
 * @date 2021-01-21
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        ResponseBase base=ResponseBase.failed(BasicConstants.HttpStatus.PERMISSION_DENIED.code,BasicConstants.HttpStatus.PERMISSION_DENIED.msg);

        //写入头文件
        PrintWriter out=httpServletResponse.getWriter();
        out.write(new ObjectMapper().writeValueAsString(base));//将对象转变成json字符串

        out.flush();
        out.close();
    }
}
