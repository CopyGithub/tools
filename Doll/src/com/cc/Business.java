package com.cc;

import com.cc.basesql.BaseSql;
import com.cc.tables.CommonTask;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Business {
    private CommonTask mCommonTask = new CommonTask();
    private BaseSql mBaseSql = new BaseSql();

    public void dumpDB(Class cls, String tableName, String sqlName) throws SQLException, IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        String sql = "select * from `" + tableName + "`";
        mCommonTask.executeFile("/com/cc/table_sql/" + sqlName);
        long start = System.currentTimeMillis();
        ArrayList<Object> objects = mBaseSql.query(sql, null, cls);
        long end = System.currentTimeMillis();
        System.out.println(tableName + "查询耗时：" + (end - start));
        start = System.currentTimeMillis();
        mCommonTask.insertTable(objects);
        end = System.currentTimeMillis();
        System.out.println(tableName + "插入耗时：" + (end - start));
    }
}
