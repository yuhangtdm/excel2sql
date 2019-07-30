package com.allinjava.excel2sql.strategy;

import com.allinjava.excel2sql.dto.ParseExcelDTO;
import com.allinjava.excel2sql.util.PoiUtil;
import org.springframework.stereotype.Component;
import java.io.File;

/**
 * @author za-yuhang   2019/7/27 10:34
 */
@Component
public class PoiExcelStrategy implements ParseExcel {

    @Override
    public File parse(ParseExcelDTO parseExcelDTO) {
        if (parseExcelDTO.isHasTableHead()){

        }else {
            PoiUtil.getSqlFile(parseExcelDTO.getSourceFile(),parseExcelDTO.getColumnList(),parseExcelDTO.getStartIndex(),
                    parseExcelDTO.getDatabaseName(),parseExcelDTO.getTableName(),parseExcelDTO.getTmpPath());
        }
        // 判断如果有表头
            // 目的是excel表头取的是列名 但是表头顺序和表的顺序不一致 取表头顺序
            // 判断表头的列和数据库表字段的列 是否全部一致
            // 如果全部一致 只是顺序不一致 则按照表头来取
            // 如果不一致 则按照表的对比 有的赋值 没有的为null
        // 如果没表头
            // 则按照数据库的列取表里 表列不够 则取null
        return null;
    }

}
