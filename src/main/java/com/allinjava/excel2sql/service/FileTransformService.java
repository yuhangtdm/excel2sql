package com.allinjava.excel2sql.service;

import com.allinjava.excel2sql.dto.ParseExcelDTO;
import java.io.File;

/**
 * @author yuhang   2019/7/25 11:29
 */
public interface FileTransformService {

    /**
     * 转换excel为sql脚本
     * @return sql脚本文件
     */
    File transform(ParseExcelDTO parseExcelDTO);

}
