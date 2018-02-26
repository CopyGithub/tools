package com.cc.basesql;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class BaseSql {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    //    private static final String URL_SOURCE = "jdbc:mysql://192.168.0.223:3306/prizeclaw?useSSL=false";
//    private static final String USERNAME_SOURCE = "zhoululu";
//    private static final String PASSWORD_SOURCE = "Zhuohan123!";
    private static final String URL_SOURCE = "jdbc:mysql://192.168.0.200:3306/prizeclaw?useSSL=false";
    private static final String USERNAME_SOURCE = "zhoululu";
    private static final String PASSWORD_SOURCE = "Zhuohan123!";
    private static final String URL_DEST = "jdbc:mysql://192.168.0.223:3306/prizeclaw?useSSL=false";
    private static final String USERNAME_DEST = "zhoululu";
    private static final String PASSWORD_DEST = "Zhuohan123!";

    private Connection getConn(String url, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        return DriverManager.getConnection(url, userName, password);
    }

    private void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    private int executeSQL(String sql) throws ClassNotFoundException, SQLException {
        Connection conn = this.getConn(URL_SOURCE, USERNAME_SOURCE, PASSWORD_SOURCE);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        int number = pstmt.executeUpdate();
        this.closeAll(conn, pstmt, null);
        return number;
    }

    private ResultSet querySQL(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = this.getConn(URL_SOURCE, USERNAME_SOURCE, PASSWORD_SOURCE);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public <T> ArrayList<T> query(String sql, Class cls) throws IllegalAccessException, InstantiationException, JSONException, SQLException, ClassNotFoundException {
        ResultSet resultSet = querySQL(sql);
        Field[] fields = cls.getDeclaredFields();
        ArrayList<Object> objects = new ArrayList<>();
        while (resultSet.next()) {
            Object object = cls.newInstance();
            for (Field field : fields) {
                String fieldName = field.getName();
                if (!hasResultSetKey(resultSet, fieldName)) {
                    continue;
                }
                String fieldType = field.getType().getSimpleName();
                field.setAccessible(true);
                if ("int".equals(fieldType)) {
                    field.setInt(object, resultSet.getInt(fieldName));
                } else if ("String".equals(fieldType)) {
                    field.set(object, resultSet.getString(fieldName));
                    if ("extend".equals(fieldName)) {
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(field.get(object)));
                            for (Field fieldExtend : fields) {
                                if (!jsonObject.has(fieldExtend.getName())) {
                                    continue;
                                }
                                fieldExtend.setAccessible(true);
                                fieldExtend.set(object, jsonObject.getString(fieldExtend.getName()));
                            }
                        } catch (JSONException e) {
                            // NOTHING
                        }
                    }
                } else if ("Timestamp".equals(fieldType)) {
                    field.set(object, resultSet.getTimestamp(fieldName));
                } else if ("BigDecimal".equals(fieldType)) {
                    field.set(object, resultSet.getBigDecimal(fieldName));
                }
            }
            objects.add(object);
        }
        return (ArrayList<T>) objects;
    }

    private boolean hasResultSetKey(ResultSet resultSet, String fieldName) {
        try {
            if (resultSet.findColumn(fieldName) > 0) {
                return true;
            }
        } catch (SQLException e) {
            // Nothing
        }
        return false;
    }
} 