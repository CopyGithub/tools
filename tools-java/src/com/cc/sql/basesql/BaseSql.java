package com.cc.sql.basesql;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class BaseSql {
    private Connection mConn = null;
    private PreparedStatement mStatement = null;
    private ResultSet mSet = null;

    /**
     * 初始化MySQL连接
     * 
     * @param url
     *            连接的URL，形式如<code>"jdbc:mysql://192.168.0.223:3306/prizeclaw?useSSL=false"</code>
     * @param userName
     *            连接用户名
     * @param password
     *            连接密码
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public BaseSql(String url, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        mConn = DriverManager.getConnection(url, userName, password);
    }

    /**
     * 关闭连接
     */
    public void close() {
        try {
            if (mSet != null) {
                mSet.close();
            }
            if (mStatement != null) {
                mStatement.close();
            }
            if (mConn != null) {
                mConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            close();
        }
    }

    /**
     * 执行查询语句并返回结果
     * 
     * @param sql
     * @return
     * @throws SQLException
     */
    public ResultSet query(String sql) throws SQLException {
        mStatement = mConn.prepareStatement(sql);
        mSet = mStatement.executeQuery();
        return mSet;
    }

    /**
     * 执行增删改语句
     * 
     * @param sql
     * @return
     * @throws SQLException
     */
    public int execute(String sql) throws SQLException {
        mStatement = mConn.prepareStatement(sql);
        return mStatement.executeUpdate();
    }

    // public String readText(String filePath, String charsetName) throws
    // IOException {
    // InputStream inputStream = Main.class.getResourceAsStream(filePath);
    // byte[] buffer = new byte[inputStream.available()];
    // inputStream.read(buffer);
    // inputStream.close();
    // return new String(buffer, charsetName);
    // }

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
            case '"':
                sb.append('\"');
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

    public ResultSet querySQL(String sql, String[] args) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = mConn.prepareStatement(sql);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setString(i + 1, args[i]);
            }
        }
        return preparedStatement.executeQuery();
    }

    public int executeSQL(String sql, String[] args) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = mConn.prepareStatement(sql);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setString(i + 1, args[i]);
            }
        }
        int number = preparedStatement.executeUpdate();
        return number;
    }

    public <T> ArrayList<T> query(String sql, String[] args, Class cls)
            throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
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
                    } else if (Types.DATE == columnType) {
                        field.set(object, resultSet.getDate(i));
                    } else if (Types.DOUBLE == columnType) {
                        field.set(object, resultSet.getDouble(i));
                    } else {
                        System.out.println(String.format("%s的类型为:%s,代号为:%d", columnName,
                                resultSet.getMetaData().getColumnTypeName(i), columnType));
                    }
                    break;
                }
            }
            objects.add(object);
        }
        return (ArrayList<T>) objects;
    }

    private void parseJson(Object object, Field[] fields, JSONObject jsonObject) throws IllegalAccessException {
        for (String key : jsonObject.keySet()) {
            String type = jsonObject.get(key).getClass().getSimpleName();
            if ("JSONObject".equalsIgnoreCase(type)) {
                parseJson(object, fields, jsonObject.getJSONObject(key));
            }
            for (Field field : fields) {
                if (!key.equals(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                String fieldType = field.getType().getSimpleName();
                if ("Integer".equalsIgnoreCase(type)) {
                    if ("String".equalsIgnoreCase(fieldType)) {
                        field.set(object, String.valueOf(jsonObject.getInt(key)));
                    } else {
                        field.setInt(object, jsonObject.getInt(key));
                    }
                } else if ("Long".equalsIgnoreCase(type)) {
                    if ("String".equalsIgnoreCase(fieldType)) {
                        field.set(object, String.valueOf(jsonObject.getLong(key)));
                    } else {
                        field.setLong(object, jsonObject.getLong(key));
                    }
                } else if ("String".equalsIgnoreCase(type)) {
                    if ("String".equalsIgnoreCase(fieldType)) {
                        field.set(object, jsonObject.getString(key));
                    } else if ("Long".equalsIgnoreCase(fieldType)) {
                        field.setLong(object, Long.valueOf(jsonObject.getString(key)));
                    } else {
                        field.setInt(object, Integer.valueOf(jsonObject.getString(key)));
                    }
                } else if ("JSONObject".equalsIgnoreCase(type)) {
                    field.set(object, jsonObject.getJSONObject(key).toString());
                } else {
                    System.out.println("未处理的JSONObject类型：" + type);
                }
            }
        }
    }
}