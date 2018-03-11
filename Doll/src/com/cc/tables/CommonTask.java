package com.cc.tables;

import com.cc.basesql.BaseSql;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;

public class CommonTask {
    private BaseSql mBaseSql = new BaseSql();

    public int insertTable(Object object) throws IllegalAccessException, InstantiationException, SQLException, ClassNotFoundException, NoSuchFieldException {
        String sql = "insert into `" + object.getClass().getDeclaredField("table_name").get(object) + "` values (%s)";
        Field[] fields = object.getClass().getDeclaredFields();
        StringBuilder values = new StringBuilder();
        for (Field field : fields) {
            if ("table_name".equals(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            Object o = field.get(object);
            if (o == null) {
                values.append("null,");
            } else {
                values.append("'").append(mBaseSql.escapeSql(o.toString())).append("',");
            }
        }
        values.delete(values.length() - 1, values.length());
        return mBaseSql.executeSQL(String.format(sql, values), null);
    }

    public int executeFile(String filePath) throws IOException, SQLException, ClassNotFoundException {
        String[] sqls = mBaseSql.readText(filePath, "utf-8").split(";");
        int num = 0;
        for (String sql : sqls) {
            num += mBaseSql.executeSQL(sql, null);
        }
        return num;
    }
}
