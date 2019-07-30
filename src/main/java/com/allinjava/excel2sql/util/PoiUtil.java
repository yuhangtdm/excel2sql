package com.allinjava.excel2sql.util;

import com.allinjava.excel2sql.dto.ParseExcelDTO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
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
                                HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
                                String value = dataFormatter.formatCellValue(cell);
                                ExcelNumberFormat nf = ExcelNumberFormat.from(cell, null);
                                boolean date = DateUtil.isADateFormat(nf);
                                if (date) {
                                    sql.append(CONNECT_QUOTATION).append(getDate(value))
                                            .append(CONNECT_QUOTATION).append(CONNECT_COMMA);
                                } else {

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
            log.error("catch error,", e);
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return destFile;
    }


    public static List<String> listTableHead(File excelFile, Integer headIndex) {
        List<String> result = Lists.newArrayList();
        Sheet sheet = getSheet(excelFile);
        Row row = sheet.getRow(headIndex);
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

    private static String getDate(String sourceValue) {
        // 7/27/19 15:45
        try {
            //todo 转换时间还有问题 会改变月份
            Date parse = new SimpleDateFormat("MM/DD/yy HH:mm").parse(sourceValue);
            return new SimpleDateFormat("yyyy-MM-DD HH:mm:ss").format(parse);
        } catch (ParseException e) {
            log.error("change date format error,", e);
            return NULL;
        }
    }

    public static void main(String[] args) {
        File file = new File("D:/dept.xlsx");
        ParseExcelDTO parseExcelDTO = new ParseExcelDTO();
        parseExcelDTO.setUrl("jdbc:mysql://106.15.188.249:3306/test");
        parseExcelDTO.setUserName("root");
        parseExcelDTO.setPassword("f088df87e10c40fe804c54e88d54b8e4");
        parseExcelDTO.setTableName("dept");
        List<String> columnList = JdbcUtil.getColumnList(parseExcelDTO);
//        getSqlFile(file, columnList, 2, "test", "dept", "D:");

        List<String> list = listTableHead(file, 0);
        System.out.println(list);


    }

}