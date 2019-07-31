package com.allinjava.excel2sql.exception;

import com.allinjava.excel2sql.common.BusinessEnum;
import com.allinjava.excel2sql.common.ResultEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private Integer errorCode;
    private String msg;

    public BusinessException() {
        super();
        this.errorCode = ResultEnum.UNKNOWN_ERROR.getCode();
        this.msg = ResultEnum.UNKNOWN_ERROR.getMsg();
    }

    public BusinessException(Integer code, String msg) {
        super();
        this.errorCode = code;
        this.msg = msg;
    }

    public BusinessException(ResultEnum resultEnum) {
        super();
        this.errorCode = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public BusinessException(ResultEnum resultEnum, String msg) {
        super();
        this.errorCode = resultEnum.getCode();
        this.msg = msg;
    }

    public BusinessException(BusinessEnum businessEnum) {
        super();
        this.errorCode = businessEnum.getCode();
        this.msg = businessEnum.getMsg();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

}
