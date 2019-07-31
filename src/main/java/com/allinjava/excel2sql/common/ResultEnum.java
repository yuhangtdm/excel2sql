package com.allinjava.excel2sql.common;

import lombok.Getter;

/**
 * @author: yuhang
 * @date: 2018/8/31
 */
@Getter
public enum ResultEnum {
    SUCC(0,"请求成功"),
    UNKNOWN_ERROR(-1,"未知错误"),
    BAD_REQUEST(400,"客户端请求错误"),
    PARAM_EORROR(404,"参数错误"),
    SERVER_ERROR(500,"服务端错误")
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
