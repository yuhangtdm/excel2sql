package com.allinjava.excel2sql.strategy.impl;

import com.allinjava.excel2sql.dto.ParseExcelDTO;
import com.allinjava.excel2sql.strategy.ParseExcel;
import com.allinjava.excel2sql.util.PoiUtil;
import org.springframework.stereotype.Component;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 策略设计模式实现
 * @author yuhang   2019/7/27 10:34
 */
@Component
public class PoiExcelStrategy implements ParseExcel {

    @Override
    public File parse(ParseExcelDTO parseExcelDTO) {
        File sqlFile = PoiUtil.getSqlFile(parseExcelDTO.getSourceFile(), parseExcelDTO.getColumnList(),
                                              parseExcelDTO.getStartIndex(), parseExcelDTO.getDatabaseName(),
                                              parseExcelDTO.getTableName(), parseExcelDTO.getTmpPath());
        return sqlFile;
    }

}
