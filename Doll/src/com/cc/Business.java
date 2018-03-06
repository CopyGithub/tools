package com.cc;

import com.cc.basesql.BaseSql;
import com.cc.tables.Account;
import com.cc.tables.PayRecord;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Business {
    private BaseSql mBaseSql = new BaseSql();

    public void getAccount(int number) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = String.format("select * from claws_account order by created_time desc limit %d", number);
        mBaseSql.execute("/com/cc/table_sql/account.sql");
        ArrayList<Account> objects = mBaseSql.query(sql, Account.class);
        for (Account object : objects) {
            mBaseSql.execute(object);
        }
    }

    public void getPayRecord() throws SQLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = String.format("select * from claws_payrecord order by created_time desc limit 100000");
        mBaseSql.execute("/com/cc/table_sql/payrecord.sql");
        ArrayList<PayRecord> payRecords = mBaseSql.query(sql, PayRecord.class);
        for (PayRecord payRecord : payRecords) {
            mBaseSql.execute(payRecord);
        }
    }
}
