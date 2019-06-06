package com.util.result;

/**
 * @author pangbohuan
 * @description 响应状态码
 * @date 2018-09-28 13:59
 **/
public final class ResponseStatusCode {

    /**
     * 操作成功
     */
    static final int SUCCEED = 200;

    /**
     * 业务操作失败
     */
    static final int BUSINESS_EXCEPTIONS = 400;


    /**
     * 权限不足
     */
    static final int LIMITED_AUTHORITY = 403;

    /**
     * 系统异常
     */
    static final int SYSTEM_EXCEPTION = 555;

    /**
     * token异常
     */
    static final int TOKEN_EXCEPTION = 405;
}
