package com.allinjava.excel2sql.common;

import lombok.Data;
import java.io.Serializable;


@Data
public class CommonResult<T> implements Serializable {

    private static final long serialVersionUID = -5061242067855702147L;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public CommonResult(){}

    public CommonResult(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static <T> CommonResult success(T data){
        return new CommonResult(ResultEnum.SUCC.getCode(),
                                ResultEnum.SUCC.getMsg(),
                                data);
    }

    public static  CommonResult error(ResultEnum resultEnum){
        return new CommonResult(resultEnum.getCode(), resultEnum.getMsg());
    }

}
