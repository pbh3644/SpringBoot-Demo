package com.util;

import com.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author pangbohuan
 * @description 由于项目中使用了太多(等值比较非等值比较非空比较), 所以自定义各种值比较场景判断减少代码的重复量,并且符合条件抛出异常
 * @date 2018-11-09 14:14
 **/
public final class CompareSceneException {


    /**
     * 自定义检验两个数型是否相等-如果相等的情况下抛出异常
     *
     * @param base 值一
     * @param val  值二
     * @param err  异常信息
     */
    public static void customNumericalEquality(long base, long val, String err) {
        if (base == val) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 自定义检验两个数型值是否相等-如果相等的情况下抛出异常
     *
     * @param base 值一
     * @param val  值二
     * @param err  异常信息
     */
    public static void customNumericalNotEquality(long base, long val, String err) {
        if (base != val) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 如果一个字符值为空值-抛出异常
     *
     * @param val 值一
     * @param err 异常信息
     */
    public static void customStringIsNull(String val, String err) {
        if (StringUtils.isEmpty(val)) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 如果一个字符值不为空值-抛出异常
     *
     * @param val 值一
     * @param err 异常信息
     */
    public static void customStringNotIsNull(String val, String err) {
        if (!StringUtils.isEmpty(val)) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 如果两个字符串相等-抛出异常
     *
     * @param val  值一
     * @param info 值二
     * @param err  异常信息
     */
    public static void customStringEquality(String val, String info, String err) {
        if (val.equals(info)) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 如果两个字符串不相等-抛出异常
     *
     * @param val  值一
     * @param info 值二
     * @param err  异常信息
     */
    public static void customStringNotEquality(String val, String info, String err) {
        if (!val.equals(info)) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 如果一个对象不为空
     *
     * @param object 对象
     * @param err    异常信息
     */
    public static void customObjectIsNotNull(Object object, String err) {
        if (object != null) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 如果一个对象为空
     *
     * @param object 对象
     * @param err    异常信息
     */
    public static void customObjectIsNull(Object object, String err) {
        if (object == null) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 比较两个数值的大小,
     *
     * @param val     变量值
     * @param maximum 最大值
     * @param err     异常信息
     */
    public static void compareSize(long val, long maximum, String err) {
        if (val > maximum) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 布尔值为真的时候,
     *
     * @param val 布尔值
     * @param err 异常信息
     */
    public static void booleanIsTrue(boolean val, String err) {
        if (val) {
            BusinessException.throwBusinessException(err);
        }
    }

    /**
     * 布尔值为假的时候,
     *
     * @param val 布尔值
     * @param err 异常信息
     */
    public static void booleanIsNotTrue(boolean val, String err) {
        if (!val) {
            BusinessException.throwBusinessException(err);
        }
    }
}
