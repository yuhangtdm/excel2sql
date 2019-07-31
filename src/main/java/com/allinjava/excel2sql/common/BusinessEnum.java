package com.allinjava.excel2sql.common;

import lombok.Getter;

/**
 * 业务枚举
 * @author yuhang   2019/7/31 17:00
 */
@Getter
public enum  BusinessEnum {
    FILE_NOT_NULL(405,"上传文件不能为空"),
    FILE_UPLOAD_ERROR(406,"上传文件出错"),
    FILE_FORMAT_ERROR(407,"文件格式不对，不是excel文件"),
    DATABASE_INFO_ERROR(408,"数据库信息错误,得不到表的列字段"),
    SQL_ERROR(407,"生成sql文件失败"),
    ;

    private Integer code;
    private String msg;

    BusinessEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }

}
