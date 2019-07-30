package com.allinjava.excel2sql.strategy;

import com.allinjava.excel2sql.dto.ParseExcelDTO;
import java.io.File;

/**
 * @author za-yuhang   2019/7/27 10:34
 */
public interface ParseExcel {

    /**
     * 解析excel
     */
    File parse(ParseExcelDTO parseExcelDTO);

}
