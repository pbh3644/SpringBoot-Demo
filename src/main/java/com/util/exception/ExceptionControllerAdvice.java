package com.util.exception;

import com.util.result.MyResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author pangbohuan
 * @date 2018/1/25
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * 全局业务处理异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public MyResult userException(BusinessException ex) {
        //业务异常400状态码
        log.error("业务处理异常", ex);
        return MyResult.errorMsg(ex.getMessage());
    }

    /**
     * 全局异常捕捉处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Throwable.class)
    public MyResult exceptionHandler(Exception ex) {
        log.error("系统发生异常:{} {}", ex.getMessage(), ex);
        return MyResult.errorMsg(ex.getMessage());
    }
}
