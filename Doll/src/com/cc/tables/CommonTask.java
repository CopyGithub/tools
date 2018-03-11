package com.cc.tables;

import com.cc.basesql.BaseSql;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommonTask {
    private BaseSql mBaseSql = new BaseSql();
    private String mSqlPath = "C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/tempSql.txt";
    String mSql = "LOAD DATA local INFILE \'" + mSqlPath + "\' INTO TABLE %s;";

    public int insertTable(ArrayList<Object> objects) throws NoSuchFieldException, IllegalAccessException, IOException, SQLException, ClassNotFoundException {
        int num = 0;
        StringBuilder sqls = new StringBuilder();
        for (int i = 0; i < objects.size(); i++) {
            Object object = objects.get(i);
            String tableName = (String) object.getClass().getDeclaredField("table_name").get(object);
            if (i != 0 && ((i % 1000 == 0) || (i + 1 == objects.size()))) {
                writeText(sqls.toString(), new File(mSqlPath), "utf8");
                num += mBaseSql.executeSQL(String.format(mSql, tableName), null);
                sqls.delete(0, sqls.length());
            }
            Field[] fields = object.getClass().getDeclaredFields();
            StringBuilder values = new StringBuilder();
            for (Field field : fields) {
                if ("table_name".equals(field.getName())) {
                    continue;
                }
                field.setAccessible(true);
                Object o = field.get(object);
                if (o == null) {
                    values.append("null\t");
                } else {
                    values.append(mBaseSql.escapeSql(o.toString())).append("\t");
                }
            }
            values.delete(values.length() - 1, values.length());
            sqls.append(values).append("\r\n");
        }
        return num;
    }

    public int executeFile(String filePath) throws IOException, SQLException, ClassNotFoundException {
        String[] sqls = mBaseSql.readText(filePath, "utf-8").split(";");
        int num = 0;
        for (String sql : sqls) {
            num += mBaseSql.executeSQL(sql, null);
        }
        return num;
    }

    private void writeText(String content, File file, String charsetName) throws IOException {
        byte[] buffer = content.getBytes(charsetName);
        FileOutputStream fos = new FileOutputStream(file, false);
        fos.write(buffer);
        fos.close();
    }
}
