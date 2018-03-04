package com.cc.basesql;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class BaseSql {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL_SOURCE = "jdbc:mysql://192.168.0.223:3306/prizeclaw?useSSL=false";
    private static final String USERNAME_SOURCE = "zhoululu";
    private static final String PASSWORD_SOURCE = "Zhuohan123!";
    private static final String URL_DEST = "jdbc:mysql://192.168.0.210:3306/doll?useSSL=false";
    private static final String USERNAME_DEST = "chenchao";
    private static final String PASSWORD_DEST = "aut0test";

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

    private int executeSQL(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = this.getConn(URL_DEST, USERNAME_DEST, PASSWORD_DEST);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        int number = pstmt.executeUpdate();
        this.closeAll(conn, pstmt, null);
        return number;
    }

    private ResultSet querySQL(String sql) throws SQLException, ClassNotFoundException {
        Connection conn = this.getConn(URL_SOURCE, USERNAME_SOURCE, PASSWORD_SOURCE);
        return conn.prepareStatement(sql).executeQuery();
    }

    public <T> ArrayList<T> query(String sql, Class cls) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ResultSet resultSet = querySQL(sql);
        Field[] fields = cls.getDeclaredFields();
        int columnNum = resultSet.getMetaData().getColumnCount();
        ArrayList<Object> objects = new ArrayList<>();
        while (resultSet.next()) {
            Object object = cls.newInstance();
            for (int i = 1; i < columnNum; i++) {
                if (resultSet.getObject(i) == null) {
                    continue;
                }
                int columnType = resultSet.getMetaData().getColumnType(i);
                if (Types.VARCHAR == columnType || Types.LONGVARCHAR == columnType) {
                    try {
                        JSONObject jsonObject = new JSONObject(resultSet.getString(i));
                        parseJson(object, fields, jsonObject);
                    } catch (JSONException e) {
                        // NOTHING
                    }
                }
                String columnName = resultSet.getMetaData().getColumnName(i);
                for (Field field : fields) {
                    if (!field.getName().equals(columnName)) {
                        continue;
                    }
                    field.setAccessible(true);
                    if (Types.INTEGER == columnType || Types.SMALLINT == columnType || Types.BIT == columnType) {
                        field.setInt(object, resultSet.getInt(i));
                    } else if (Types.VARCHAR == columnType || Types.LONGVARCHAR == columnType) {
                        field.set(object, resultSet.getString(i));
                    } else if (Types.TIMESTAMP == columnType) {
                        field.set(object, resultSet.getTimestamp(i));
                    } else if (Types.DECIMAL == columnType) {
                        field.set(object, resultSet.getBigDecimal(i));
                    } else if (Types.BIGINT == columnType) {
                        field.set(object, resultSet.getLong(i));
                    } else {
                        System.out.println(String.format("%s的类型为:%s,代号为:%d", resultSet.getMetaData().getColumnName(i),
                                resultSet.getMetaData().getColumnTypeName(i), resultSet.getMetaData().getColumnType(i)));
                    }
                }
            }
            objects.add(object);
        }
        closeAll(null, null, resultSet);
        return (ArrayList<T>) objects;
    }

    private void parseJson(Object object, Field[] fields, JSONObject jsonObject) throws IllegalAccessException {
        for (String key : jsonObject.keySet()) {
            String type = jsonObject.get(key).getClass().getSimpleName();
            if ("JSONObject".equals(type)) {
                parseJson(object, fields, jsonObject.getJSONObject(key));
            }
            for (Field field : fields) {
                if (!key.equals(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                if ("Integer".equals(type)) {
                    if ("String".equals(field.getType().getSimpleName())) {
                        field.set(object, String.valueOf(jsonObject.getInt(key)));
                    } else {
                        field.setInt(object, jsonObject.getInt(key));
                    }
                } else if ("String".equals(type)) {
                    field.set(object, jsonObject.getString(key));
                } else if ("JSONObject".equals(type)) {
                    field.set(object, jsonObject.getJSONObject(key).toString());
                } else {
                    System.out.println("***" + type);
                }
            }
        }
    }

    public int execute(Object object) throws IllegalAccessException, InstantiationException, SQLException, ClassNotFoundException {
        String sql = "insert into `%s` values (%s)";
        int number = 0;
        Field[] fields = object.getClass().getDeclaredFields();
        String values = "";
        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(object);
            if (o == null) {
                values += "null,";
            } else {
                values += "'" + escapeSql(o.toString()) + "',";
            }
        }
        values = values.substring(0, values.length() - 1);
        String execSql = String.format(sql, object.getClass().getSimpleName().toLowerCase(), values);
        number += executeSQL(execSql);
        return number;
    }

    public int execute(String filePath) throws UnsupportedEncodingException, SQLException, ClassNotFoundException {
        String[] sqls = readText(new File(filePath), "utf-8").split(";");
        int num = 0;
        for (String sql : sqls) {
            num += executeSQL(sql);
        }
        return num;
    }

    private static String readText(File file, String charsetName)
            throws UnsupportedEncodingException {
        FileInputStream fis = null;
        byte[] buffer = null;
        try {
            fis = new FileInputStream(file);
            buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new String(buffer, charsetName);
    }

    private static String escapeSql(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char src = str.charAt(i);
            switch (src) {
                case '\'':
                    sb.append("''");// hibernate转义多个单引号必须用两个单引号
                    break;
                case '\"':
                case '\\':
                    sb.append('\\');
                default:
                    sb.append(src);
                    break;
            }
        }
        return sb.toString();
    }
}