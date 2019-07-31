package com.allinjava.excel2sql.strategy;

import com.allinjava.excel2sql.dto.ParseExcelDTO;
import java.io.File;

/**
 * 策略设计模式接口
 * @author yuhang   2019/7/27 10:34
 */
public interface ParseExcel {

    /**
     * 解析excel
     */
    File parse(ParseExcelDTO parseExcelDTO);

}
