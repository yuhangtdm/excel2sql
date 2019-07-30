
package com.allinjava.excel2sql.service.impl;

import com.allinjava.excel2sql.dto.ParseExcelDTO;
import com.allinjava.excel2sql.service.FileTransformService;
import com.allinjava.excel2sql.strategy.ParseExcelFactory;
import com.allinjava.excel2sql.util.JdbcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author za-yuhang   2019/7/26 10:00
 */
@Service
@Slf4j
public class FileTransformServiceImpl implements FileTransformService {

    @Autowired
    private ParseExcelFactory parseExcelFactory;

    @Override
    public File transform(ParseExcelDTO parseExcelDTO){
        // 1.获取源文件的数据
        File tmpCatalog = new File(parseExcelDTO.getTmpPath());
        if (tmpCatalog.exists()){
            tmpCatalog.mkdirs();
        }
        File sourceFile = new File(parseExcelDTO.getTmpPath()+"/"+parseExcelDTO.getFile().getOriginalFilename());
        try {
            parseExcelDTO.getFile().transferTo(sourceFile);
        } catch (IOException e) {
            log.error("catch io exception,",e);
        }

        // 2.得到数据库表的字段
        List<String> columnList = JdbcUtil.getColumnList(parseExcelDTO);
        if (CollectionUtils.isEmpty(columnList)){
            //todo 异常处理
        }

        // 3.解析excel得到脚本
        File destFile = parseExcelFactory.getInstance("poi").parse(parseExcelDTO);
        return destFile;
    }
}
