package com.tools.java.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Convert {

    /**
     * 将Resultset转成String显示
     * 
     * @param resultSet
     * @return
     * @throws SQLException
     */

    public String toString(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return "";
        }
        String result = "";
        int number = resultSet.getMetaData().getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i < number + 1; i++) {
                result += resultSet.getString(i);
                result += "\t";
            }
            result += "\r\n";
        }
        return result;
    }
}
