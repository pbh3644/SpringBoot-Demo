package com.util;

import com.entity.User;

/**
 * @author : pangbohuan
 * @time : 2018/7/25 15:17
 */
public final class SecurityUtil {

    /**
     * 获取当前用户
     *
     * @return ScanUserLogin
     */
    public static User getUser() {
        return (User) RequestUtil.getRequest().getAttribute(SystemCommonConstants.ADMIN);
    }

    /**
     * 返回userId, 如果未登录，则返回 admin
     *
     * @return userId
     */
    public static String getUserId() {
        return getUser().getId();
    }

    /**
     * 返回用户名
     *
     * @return userId
     */
    public static String getLoginNo() {
        return getUser().getName();
    }
}
