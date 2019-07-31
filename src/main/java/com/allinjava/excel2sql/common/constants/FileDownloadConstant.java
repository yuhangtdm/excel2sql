
package com.allinjava.excel2sql.common.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author yuhang   2019/7/31 11:31
 */
public interface FileDownloadConstant {

    String DOWNLOAD_HEADER = "Content-Disposition";

    String DOWNLOAD_FILENAME = "attachment;fileName=";

    String TMP_PATH = "/upload/";

    List<String> EXCEL_FORMART = Arrays.asList("xls","xlsx");

}
