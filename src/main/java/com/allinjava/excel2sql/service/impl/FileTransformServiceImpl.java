package com.allinjava.excel2sql.service.impl;

import com.allinjava.excel2sql.common.BusinessEnum;
import com.allinjava.excel2sql.common.constants.FileDownloadConstant;
import com.allinjava.excel2sql.dto.ParseExcelDTO;
import com.allinjava.excel2sql.exception.BusinessException;
import com.allinjava.excel2sql.service.FileTransformService;
import com.allinjava.excel2sql.strategy.ParseExcelFactory;
import com.allinjava.excel2sql.util.FileUtil;
import com.allinjava.excel2sql.util.JdbcUtil;
import com.allinjava.excel2sql.util.PoiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author yuhang   2019/7/26 10:00
 */
@Service
@Slf4j
public class FileTransformServiceImpl implements FileTransformService {

    @Autowired
    private ParseExcelFactory parseExcelFactory;

    @Value("${parseStrategy}")
    private String parseStrategy;

    @Override
    public File transform(ParseExcelDTO parseExcelDTO){
        // step1.构建excel源文件对象
        File tmpPath = new File(parseExcelDTO.getTmpPath());
        if (!tmpPath.exists()){
            tmpPath.mkdirs();
        }
        File sourceFile = new File(tmpPath,parseExcelDTO.getFile().getOriginalFilename());
        if (parseExcelDTO.getFile().isEmpty()){
            throw new BusinessException(BusinessEnum.FILE_NOT_NULL);
        }
        String format = FileUtil.getFileFormat(parseExcelDTO.getFile().getOriginalFilename());
        if (!FileDownloadConstant.EXCEL_FORMART.contains(format)){
            throw new BusinessException(BusinessEnum.FILE_FORMAT_ERROR);
        }
        try {
            parseExcelDTO.getFile().transferTo(sourceFile);
        } catch (IOException e) {
            log.error("upload file exception",e);
            throw new BusinessException(BusinessEnum.FILE_UPLOAD_ERROR);
        }
        parseExcelDTO.setSourceFile(sourceFile);

        // step2.得到excel的表头或table的列集合
        List<String> columnList = null;
        if (parseExcelDTO.isHasTableHead()){
            columnList = PoiUtil.listTableHead(parseExcelDTO.getSourceFile(), parseExcelDTO.getHeadIndex());
        }else {
            columnList = JdbcUtil.getColumnList(parseExcelDTO);
        }
        if (CollectionUtils.isEmpty(columnList)){
            log.error("get a empty column list");
            throw new BusinessException(BusinessEnum.DATABASE_INFO_ERROR);
        }
        parseExcelDTO.setColumnList(columnList);

        // step3.解析excel得到脚本
        File destFile = parseExcelFactory.getInstance(parseStrategy).parse(parseExcelDTO);

        // step4.删除excel源文件以及校验生成sql文件是否成功
        if (sourceFile != null) {
            sourceFile.delete();
        }
        if (destFile == null){
            throw new BusinessException(BusinessEnum.SQL_ERROR);
        }

        return destFile;
    }

}
