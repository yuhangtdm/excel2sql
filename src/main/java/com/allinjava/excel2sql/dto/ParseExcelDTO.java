
package com.allinjava.excel2sql.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * @author yuhang   2019/7/27 9:58
 */
@Data
//@Validated
public class ParseExcelDTO implements Serializable {

    /**
     * 源文件
     */
    @NotNull(message = "excel文件不能为空")
    private MultipartFile file;

    /**
     * 主机地址
     */
    @NotBlank(message = "数据库主机地址不能为空")
    private String host;

    /**
     * 端口号
     */
    @NotNull(message = "端口号不能为空")
    private Integer port;

    /**
     * 数据库名称
     */
    @NotBlank(message = "数据库名称不能为空")
    private String databaseName;

    /**
     * 数据库url
     */
    private String url;

    /**
     * 数据库用户名
     */
    @NotBlank(message = "数据库用户名不能为空")
    private String userName;

    /**
     * 数据库密码
     */
    @NotBlank(message = "数据库密码不能为空")
    private String password;

    /**
     * 数据库表名
     */
    @NotBlank(message = "数据库表名不能为空")
    private String tableName;

    /**
     * 解析表开始行 默认为2
     */
    @NotNull(message = "解析开始行不能为空")
    private Integer startIndex;

    /**
     * 是否按表头解析
     */
    private boolean hasTableHead;

    /**
     * 表头所在行
     */
    private Integer headIndex;

    /**
     * 临时文件存放地址
     */
    private String tmpPath;

    /**
     * excel的源数据
     */
    private File sourceFile;

    /**
     * 数据库table的列集合
     */
    private List<String> columnList;

}
