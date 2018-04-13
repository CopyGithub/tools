package com.cc;

import com.cc.basesql.BaseSql;
import com.cc.tables.AccountSPT;
import com.cc.tables.CommonTask;

import java.io.IOException;
import java.sql.ResultSet;
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

    public void readPay() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {
        String sql = "select account_id,sum(price) pay from claws_payrecord\n" +
                " where `status` = 2\n" +
                " group by account_id \n" +
                " order by pay desc";
        mCommonTask.executeFile("/com/cc/table_sql/account_spt.sql");
        long start = System.currentTimeMillis();
        ArrayList<Object> objects = mBaseSql.query(sql, null, AccountSPT.class);
        long end = System.currentTimeMillis();
        System.out.println("claws_payrecord 查询耗时：" + (end - start));
        start = System.currentTimeMillis();
        mCommonTask.insertTable(objects);
        end = System.currentTimeMillis();
        System.out.println("account_spt 插入耗时：" + (end - start));
    }

    public void readShipOrder() throws SQLException, ClassNotFoundException {
        String sql = "select account_id,credit_product_id,doll_ids from claws_shiporder";
        ResultSet resultSet = mBaseSql.querySQL(sql, null);
        while (resultSet.next()) {
            int account_id = resultSet.getInt(1);
            int product_id = resultSet.getInt(2);
            String[] doll_ids = resultSet.getString(3).split(",");
            // TODO
        }
    }
}
