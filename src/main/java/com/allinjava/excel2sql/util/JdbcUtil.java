
package com.allinjava.excel2sql.util;

import com.allinjava.excel2sql.dto.ParseExcelDTO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import java.sql.*;
import java.util.List;

/**
 * @author yuhang   2019/7/26 10:10
 */
@Slf4j
public class JdbcUtil {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    private JdbcUtil() {
    }


    public static List<String> getColumnList(ParseExcelDTO parseExcelDTO) {
        List<String> list = Lists.newArrayList();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DRIVER_NAME);
            connection = DriverManager.getConnection(parseExcelDTO.getUrl(), parseExcelDTO.getUserName(), parseExcelDTO.getPassword());
            String sql = "select * from " + parseExcelDTO.getTableName();
            preparedStatement = connection.prepareStatement(sql);
            ResultSetMetaData metaData = preparedStatement.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                list.add(metaData.getColumnName(i+1));
            }
        } catch (ClassNotFoundException e) {
            log.error("not find driver class,{}",DRIVER_NAME);
            return list;
        } catch (SQLException e) {
            log.error("execute sql error,",e);
            return list;
        } finally {
            try {
                if (preparedStatement != null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
