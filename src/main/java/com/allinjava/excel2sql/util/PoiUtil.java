package com.allinjava.excel2sql.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
public class PoiUtil {

    private static final String INSERT_SQL = "INSERT INTO ";

    private static final String VALUES = "VALUES";

    private static final String PRIFIX = "`";

    private static final String CONNECT_POINT = ".";

    private static final String CONNECT_SPACE = " ";

    private static final String CONNECT_COMMA = ",";

    private static final String CONNECT_LEFT_BRACKETS = "(";

    private static final String CONNECT_RIGHT_BRACKETS = ")";

    private static final String CONNECT_SEMICOLON = ";";

    private static final String CONNECT_QUOTATION = "'";

    private static final String NULL = "NULL";

    private static final String CONNECT_PATH = "/";

    private static final String DEST_FILE_SUFFIX = ".sql";


    /**
     *  生成sql文件
     * @param excelFile excel源数据
     * @param columnList table的列集合
     * @param startIndex 开始解析的行数
     * @param databaseName 数据库名称
     * @param tableName 表名
     * @param tmpPath 存放文件的临时路径
     */
    public static File getSqlFile(File excelFile, List<String> columnList, int startIndex,
                                  String databaseName, String tableName, String tmpPath) {

        File destFile = null;
        BufferedWriter bufferedWriter = null;
        try {
            String fileName = excelFile.getName();
            Sheet sheet = getSheet(excelFile);
            destFile = new File(tmpPath + CONNECT_PATH + FileUtil.getFileName(fileName) + DEST_FILE_SUFFIX);
            bufferedWriter = new BufferedWriter(new FileWriter(destFile));
            int columnSize = columnList.size();
            int totalRows = sheet.getLastRowNum();

            for (int i = startIndex - 1; i <= totalRows; i++) {
                StringBuilder sql = new StringBuilder();
                sql.append(INSERT_SQL).append(PRIFIX).append(databaseName).append(PRIFIX)
                        .append(CONNECT_POINT).append(PRIFIX).append(tableName).append(PRIFIX)
                        .append(CONNECT_LEFT_BRACKETS);
                for (int j = 0; j < columnSize; j++) {
                    if (j != columnSize - 1) {
                        sql.append(PRIFIX).append(columnList.get(j)).append(PRIFIX).append(CONNECT_COMMA);
                    } else {
                        sql.append(PRIFIX).append(columnList.get(j)).append(PRIFIX);
                    }
                }
                sql.append(CONNECT_RIGHT_BRACKETS).append(CONNECT_SPACE)
                        .append(VALUES).append(CONNECT_LEFT_BRACKETS);
                for (int j = 0; j < columnSize; j++) {
                    Cell cell = sheet.getRow(i).getCell(j);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case STRING:
                                sql.append(CONNECT_QUOTATION).append(cell.getStringCellValue())
                                        .append(CONNECT_QUOTATION).append(CONNECT_COMMA);
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    sql.append(CONNECT_QUOTATION).append(getDate(cell.getDateCellValue()))
                                            .append(CONNECT_QUOTATION).append(CONNECT_COMMA);
                                } else {
                                    HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
                                    String value = dataFormatter.formatCellValue(cell);
                                    sql.append(CONNECT_QUOTATION).append(value)
                                            .append(CONNECT_QUOTATION).append(CONNECT_COMMA);
                                }
                                break;
                            default:
                                sql.append(NULL).append(CONNECT_COMMA);
                                break;
                        }
                    } else {
                        sql.append(NULL).append(CONNECT_COMMA);
                    }

                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(CONNECT_RIGHT_BRACKETS).append(CONNECT_SEMICOLON);
                bufferedWriter.write(sql.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (Exception e) {
            log.error("generate sql catch error", e);
            return null;
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                log.error("ioException",e);
            }
        }
        return destFile;
    }

    /**
     * 获取表的头数据
     * @param excelFile 源文件
     * @param headIndex 表头行数
     * @return
     */
    public static List<String> listTableHead(File excelFile, Integer headIndex) {
        List<String> result = Lists.newArrayList();
        Sheet sheet = getSheet(excelFile);
        Row row = sheet.getRow(headIndex-1);
        short firstCellNum = row.getFirstCellNum();
        short lastCellNum = row.getLastCellNum();
        for (short i = firstCellNum; i <= lastCellNum; i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                result.add(cell.getStringCellValue());
            }

        }
        return result;
    }


    private static Sheet getSheet(File excelFile) {
        InputStream inputStream = null;
        Sheet sheet = null;
        try {
            inputStream = new FileInputStream(excelFile);
            String fileName = excelFile.getName();
            Workbook wb = null;
            if (fileName.endsWith(".xlsx")) {
                wb = new XSSFWorkbook(inputStream);
            } else if (fileName.endsWith(".xls")) {
                wb = new HSSFWorkbook(inputStream);
            }
            sheet = wb.getSheetAt(0);
        } catch (Exception e) {
            log.error("file not found,", e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sheet;

    }

    private static String getDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

}