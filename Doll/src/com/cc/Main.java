package com.cc;

import com.cc.basesql.BaseSql;
import com.cc.basesql.Table2String;
import com.cc.tables.Account;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String sql = "select * from claws_account order by claws_account.created_time desc limit 10";
        Table2String table2String = new Table2String();
        ArrayList<Account> accounts = new BaseSql().query(sql, Account.class);
        System.out.println(table2String.toFieldName(Account.class));
        for (Account account : accounts) {
            System.out.println(table2String.toString(account));
        }
    }
}
