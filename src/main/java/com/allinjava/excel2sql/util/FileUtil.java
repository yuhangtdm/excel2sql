
package com.allinjava.excel2sql.util;

/**
 * @author za-yuhang   2019/7/27 11:58
 */
public class FileUtil {

    public static String getFileName(String fullFileName){
        return fullFileName.substring(0,fullFileName.lastIndexOf("."));
    }
}
