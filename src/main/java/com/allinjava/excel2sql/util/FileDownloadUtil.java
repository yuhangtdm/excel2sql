package com.allinjava.excel2sql.util;

import com.allinjava.excel2sql.common.constants.FileDownloadConstant;
import com.allinjava.excel2sql.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author yuhang   2019/7/31 12:06
 */
@Slf4j
public class FileDownloadUtil {

    /**
     * 浏览器下载文件
     */
    public static void download(File file, HttpServletResponse response){
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(file);
            response.addHeader(FileDownloadConstant.DOWNLOAD_HEADER, FileDownloadConstant.DOWNLOAD_FILENAME
                    + new String(file.getName().getBytes(),"UTF-8"));
            out = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            log.error("ioexception,",e);
            throw new BusinessException(-1,"下载出现io异常");
        }
    }

}
