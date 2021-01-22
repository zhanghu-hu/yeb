package com.zh.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zh.server.config.BasicConstants;
import com.zh.server.response.common.ResponseBase;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当未登录或者token失效时访问接口时，自定义的返回结果
 *
 * @author ZH
 * @date 2021-01-21
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        ResponseBase base=ResponseBase.failed(BasicConstants.HttpStatus.NO_LOGIN.code,BasicConstants.HttpStatus.NO_LOGIN.msg);
        //写进头文件
        PrintWriter out=httpServletResponse.getWriter();
        out.write(new ObjectMapper().writeValueAsString(base));//将对象转换成json字符串

        out.flush();//清空缓冲区，爆炸数据全部写入不丢失
        out.close();
    }
}
