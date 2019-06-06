package com.util.exception;

/**
 * @author pangbohuan
 * @description 同一业务异常
 * @date 2018-09-19 10:38
 **/
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 3212950966960496693L;


    private BusinessException() {
    }

    private BusinessException(String message) {
        super(message);
    }

    public static BusinessException throwBusinessException(String err) {
        throw new BusinessException(err);
    }
}
