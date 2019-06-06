package com.config.aop;

import com.alibaba.fastjson.JSONObject;
import com.util.result.MyResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pangbohuan
 * @description 利用Spring Aop 前通知获取请求参数打印,返回后通知统一封装Controller层所有返回信息-json格式,但排除ExcludeResultJson注解标记的方法
 * @date 2018-11-16 17:51
 **/
@Aspect
@Component
@Slf4j
public class ControllerResultHandler {

    /**
     * 返回格式为json
     */
    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    /**
     * 编码格式
     * UTF-8
     */
    public static final String UTF8 = "UTF-8";

    @Resource
    private HttpServletResponse response;

    /**
     * 排除自定义返回格式切入点定义
     */
    @Pointcut("@annotation(com.config.aop.annotation.CustomResultFormat)")
    public void customResultFormat() {
    }

    /**
     * Controller层切入点定义
     */
    @Pointcut("execution(* com.controller..*.*(..))")
    public void controller() {
    }

    /**
     * 返回后通知-封装所有返回信息为JSON格式
     */
    @AfterReturning(value = "controller() && !customResultFormat()", returning = "result")
    public void controllerResult(Object result) throws Throwable {
        response.setContentType(CONTENT_TYPE);
        String jsonString = JSONObject.toJSONString(webResult(result));
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] bytes = jsonString.getBytes(UTF8);
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
        responseRubbishRecycle(result, jsonString, bytes);
    }

    /**
     * 定义返回前端的统一格式
     * 增删改成功提示信息内容或者是查询列表内容
     */
    private MyResult webResult(Object obj) {
        return MyResult.ok(obj);
    }

    /**
     * controller层的东西只是为了传输给前端,用完后可以立即置空,让垃圾回收器更好的去判断并回收
     */
    private void responseRubbishRecycle(Object result, String jsonString, byte[] bytes) {
        result = null;
        jsonString = null;
        bytes = null;
    }
}
