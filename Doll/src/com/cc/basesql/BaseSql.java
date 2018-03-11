package com.cc.basesql;

import com.cc.Main;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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

    public String readText(String filePath, String charsetName) throws IOException {
        InputStream inputStream = Main.class.getResourceAsStream(filePath);
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        inputStream.close();
        return new String(buffer, charsetName);
    }

    public String escapeSql(String str) {
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
                case '\\':
                    sb.append('\\');
                default:
                    sb.append(src);
                    break;
            }
        }
        return sb.toString();
    }

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

    private ResultSet querySQL(String sql, String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = this.getConn(URL_SOURCE, USERNAME_SOURCE, PASSWORD_SOURCE);
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setString(i + 1, args[i]);
            }
        }
        return preparedStatement.executeQuery();
    }

    public int executeSQL(String sql, String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = this.getConn(URL_DEST, USERNAME_DEST, PASSWORD_DEST);
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setString(i + 1, args[i]);
            }
        }
        int number = preparedStatement.executeUpdate();
        this.closeAll(conn, preparedStatement, null);
        return number;
    }

    public <T> ArrayList<T> query(String sql, String[] args, Class cls) throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ResultSet resultSet = querySQL(sql, args);
        Field[] fields = cls.getDeclaredFields();
        int columnNum = resultSet.getMetaData().getColumnCount();
        ArrayList<Object> objects = new ArrayList<>();
        while (resultSet.next()) {
            Object object = cls.newInstance();
            for (int i = 1; i < columnNum + 1; i++) {
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
                        System.out.println(String.format("%s的类型为:%s,代号为:%d", columnName, resultSet.getMetaData().getColumnTypeName(i), columnType));
                    }
                    break;
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
                String fieldType = field.getType().getSimpleName();
                if ("Integer".equals(type)) {
                    if ("String".equals(fieldType)) {
                        field.set(object, String.valueOf(jsonObject.getInt(key)));
                    } else {
                        field.setInt(object, jsonObject.getInt(key));
                    }
                } else if ("String".equals(type)) {
                    if ("String".equals(fieldType)) {
                        field.set(object, jsonObject.getString(key));
                    } else if ("Long".equals(fieldType)) {
                        field.setLong(object, Long.valueOf(jsonObject.getString(key)));
                    } else {
                        field.setInt(object, Integer.valueOf(jsonObject.getString(key)));
                    }
                } else if ("JSONObject".equals(type)) {
                    field.set(object, jsonObject.getJSONObject(key).toString());
                } else {
                    System.out.println("未处理的JSONObject类型：" + type);
                }
            }
        }
    }
}