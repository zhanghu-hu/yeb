package com.zh.server.config.exception;

import com.zh.server.config.BasicConstants;
import com.zh.server.response.common.ResponseBase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@Slf4j  //日志输出
@RestControllerAdvice  //全局异常捕获标签
public class GlobalException {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = MethodArgumentNotValidException.class)  //处理实体校验异常
    public ResponseBase handler(MethodArgumentNotValidException e) {
        logger.error(e.getMessage(), e);
        //筛选出重要的错误信息(每次返回最先错的那个)
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return ResponseBase.failed(400,objectError.getDefaultMessage());
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseBase handler(SQLException e){
        logger.error(e.getMessage());
        //返回外键错误
        if (e instanceof SQLIntegrityConstraintViolationException){
            return ResponseBase.failed(BasicConstants.HttpStatus.SQL_FOREIGN_KEY_ERROE.code,BasicConstants.HttpStatus.SQL_FOREIGN_KEY_ERROE.msg);
        }
        return ResponseBase.failed(BasicConstants.HttpStatus.SQL_ERROR.code,BasicConstants.HttpStatus.SQL_ERROR.msg);
    }

    @ExceptionHandler(value = Exception.class)  //处理全部异常，Exception为所有异常的父类
    public ResponseBase handler(Exception e) {
        logger.error(e.getMessage(), e);
        return ResponseBase.failed(BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.code,BasicConstants.HttpStatus.INTERNAL_SERVER_ERROR.msg);
    }
}
