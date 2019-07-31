
package com.allinjava.excel2sql.util;

/**
 * @author yuhang   2019/7/27 11:58
 */
public class FileUtil {



    /**
     * 获取 文件名
     */
    public static String getFileName(String fullFileName){
        return fullFileName.substring(0,fullFileName.lastIndexOf("."));
    }

    /**
     * 获取文件格式
     * @param fullFileName
     * @return
     */
    public static String getFileFormat(String fullFileName){
        return fullFileName.substring(fullFileName.lastIndexOf(".")+1);
    }

}
