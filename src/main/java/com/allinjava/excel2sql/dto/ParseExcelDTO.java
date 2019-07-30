
package com.allinjava.excel2sql.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * @author za-yuhang   2019/7/27 9:58
 */
@Data
public class ParseExcelDTO implements Serializable {

    /**
     * 源文件
     */
    private MultipartFile file;

    /**
     * 主机地址
     */
    private String host;

    /**
     * 端口号
     */
    private int port;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 数据库url
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String userName;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 是否包含表头
     */
    private boolean hasTableHead;

    /**
     * 表头的行数 默认为1
     */
    private Integer headIndex;

    /**
     * 解析表开始行 默认为2
     */
    private Integer startIndex;

    /**
     * 临时文件存放地址
     */
    private String tmpPath;

    /**
     * excel的源数据
     */
    private File sourceFile;

    private List<String> columnList;

}
