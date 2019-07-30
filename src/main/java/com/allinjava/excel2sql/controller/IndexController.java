
package com.allinjava.excel2sql.controller;

import com.allinjava.excel2sql.dto.ParseExcelDTO;
import com.allinjava.excel2sql.service.FileTransformService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author za-yuhang   2019/7/25 9:46
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

    @RequestMapping("/parseExcel")
    public void parseExcel(ParseExcelDTO parseExcelDTO,
                       HttpServletResponse response,
                       HttpServletRequest request)  {
        InputStream in = null;
        OutputStream out = null;
        try {
            String tmpPath = request.getServletContext().getRealPath("/tmp");
            parseExcelDTO.setTmpPath(tmpPath);
            parseExcelDTO.setUrl(JDBC_URL_PREFIX + parseExcelDTO.getHost() + ":" + parseExcelDTO.getPort()
                    + "/" + parseExcelDTO.getDatabaseName());

            File sqlFile = fileTransformService.transform(parseExcelDTO);
            in = new FileInputStream(sqlFile);
            response.addHeader("Content-Disposition", "attachment;fileName=" + "abc.txt");
            int len = 0;
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            log.error("IndexController.parseExcel error,", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                log.error("", e);
            }
            try {
                out.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

}
