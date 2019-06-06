package com.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author pangbohuan
 * @description 请求工具类
 * @date 2019-01-24 10:02
 **/
public final class RequestUtil {

    /**
     * 获取当前的request
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 返回请求携带的token
     *
     * @return token
     */
    public static String getToken() {
        return getRequest().getHeader(SystemCommonConstants.TOKEN);
    }
}
