package com.cc;

import com.cc.basesql.BaseSql;
import com.cc.tables.Account;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Business {
    public void getAccount(int number) throws UnsupportedEncodingException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = String.format("select * from claws_account order by created_time desc limit %d", number);
        BaseSql baseSql = new BaseSql();
        baseSql.execute("./src/com/cc/table_sql/account.sql");
        ArrayList<Account> objects = baseSql.query(sql, Account.class);
        for (Account object : objects) {
            baseSql.execute(object);
        }
    }
}
