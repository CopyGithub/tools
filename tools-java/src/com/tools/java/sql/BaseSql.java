package com.tools.java.sql;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;

public class BaseSql {
    private Connection mConn = null;
    private PreparedStatement mStatement = null;
    public ResultSet mSet = null;

    /**
     * 初始化MySQL连接
     * 
     * @param host     连接的host,形式如{@code 192.168.0.4}
     * @param dataName 需要连接的数据库名
     * @param userName 数据库用户名
     * @param password 数据库密码
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public BaseSql(String host, String dataName, String userName, String password)
            throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://" + host + ":3306/" + dataName + "?useSSL=false&serverTimezone=GMT%2B8";
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
            close();
        }
    }

    /**
     * 根据参数设置组设置{@link PreparedStatement}的参数
     * 
     * @param sql
     * @param args 设置{@code null}则直接执行sql
     * @return
     * @throws SQLException
     */
    public void query(String sql, ArrayList<Object> args) throws SQLException {
        initPreparedStatement(sql, args);
        mSet = mStatement.executeQuery();
    }

    /**
     * 根据参数设置组设置{@link PreparedStatement}的参数
     * 
     * @param sql
     * @param args 设置{@code null}则直接执行sql
     * @return
     * @throws SQLException
     */
    public int execute(String sql, ArrayList<Object> args) throws SQLException {
        initPreparedStatement(sql, args);
        int num = mStatement.executeUpdate();
        close();
        return num;
    }

    /**
     * 设置{@link PreparedStatement}的参数
     * 
     * @param sql
     * @param args
     * @return
     * @throws SQLException
     */
    private void initPreparedStatement(String sql, ArrayList<Object> args) throws SQLException {
        mStatement = mConn.prepareStatement(escapeSql(sql));
        if (args != null) {
            for (int i = 0; i < args.size(); i++) {
                if (args.get(i) instanceof Integer) {
                    mStatement.setInt(i + 1, (int) args.get(i));
                } else if (args.get(i) instanceof String) {
                    mStatement.setString(i + 1, String.valueOf(args.get(i)));
                } else {
                    throw new SQLException("args is not Integer or String");
                }
            }
        }
    }

    /**
     * 转化为安全的SQL语句
     * 
     * @param str
     * @return
     */
    private String escapeSql(String str) {
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

    public <T> ArrayList<T> query(String sql, ArrayList<Object> args, Class<?> cls)
            throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        query(sql, args);
        Field[] fields = cls.getDeclaredFields();
        int columnNum = mSet.getMetaData().getColumnCount();
        ArrayList<Object> objects = new ArrayList<>();
        while (mSet.next()) {
            Object object = cls.newInstance();
            for (int i = 1; i < columnNum + 1; i++) {
                if (mSet.getObject(i) == null) {
                    continue;
                }
                int columnType = mSet.getMetaData().getColumnType(i);
                if (Types.VARCHAR == columnType || Types.LONGVARCHAR == columnType) {
                    try {
                        JSONObject jsonObject = new JSONObject(mSet.getString(i));
                        parseJson(object, fields, jsonObject);
                    } catch (JSONException e) {
                        // NOTHING
                    }
                }
                String columnName = mSet.getMetaData().getColumnName(i);
                for (Field field : fields) {
                    if (!field.getName().equals(columnName)) {
                        continue;
                    }
                    field.setAccessible(true);
                    if (Types.INTEGER == columnType || Types.SMALLINT == columnType || Types.BIT == columnType) {
                        field.setInt(object, mSet.getInt(i));
                    } else if (Types.VARCHAR == columnType || Types.LONGVARCHAR == columnType) {
                        field.set(object, mSet.getString(i));
                    } else if (Types.TIMESTAMP == columnType) {
                        field.set(object, mSet.getTimestamp(i));
                    } else if (Types.DECIMAL == columnType) {
                        field.set(object, mSet.getBigDecimal(i));
                    } else if (Types.BIGINT == columnType) {
                        field.set(object, mSet.getLong(i));
                    } else if (Types.DATE == columnType) {
                        field.set(object, mSet.getDate(i));
                    } else if (Types.DOUBLE == columnType) {
                        field.set(object, mSet.getDouble(i));
                    } else {
                        System.out.println(String.format("%s的类型为:%s,代号为:%d", columnName,
                                mSet.getMetaData().getColumnTypeName(i), columnType));
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