package com.allinjava.excel2sql.controller;

import com.allinjava.excel2sql.common.constants.FileDownloadConstant;
import com.allinjava.excel2sql.dto.ParseExcelDTO;
import com.allinjava.excel2sql.service.FileTransformService;
import com.allinjava.excel2sql.util.FileDownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;

/**
 * @author yuhang   2019/7/25 9:46
 */
@Controller
@Slf4j
public class IndexController {

    private static final String JDBC_URL_PREFIX = "jdbc:mysql://";

    @Autowired
    private FileTransformService fileTransformService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 解析excel
     * 要求: excel的源数据需要和表的列保持一致
     */
    @RequestMapping("/parseExcel")
    public void parseExcel(@Valid  ParseExcelDTO parseExcelDTO,
                                   HttpServletResponse response,
                                   HttpServletRequest request)  {

        String tmpPath = request.getServletContext().getRealPath(FileDownloadConstant.TMP_PATH);
        parseExcelDTO.setTmpPath(tmpPath);
        parseExcelDTO.setUrl(JDBC_URL_PREFIX + parseExcelDTO.getHost()
                                + ":" + parseExcelDTO.getPort()
                                + "/" + parseExcelDTO.getDatabaseName());
        File sqlFile = fileTransformService.transform(parseExcelDTO);
        FileDownloadUtil.download(sqlFile,response);
    }

}
