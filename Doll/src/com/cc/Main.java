package com.cc;

import com.cc.tables.Account;
import com.cc.tables.PayRecord;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {
        long start = System.currentTimeMillis();
        Business business = new Business();
        business.dumpDB(Account.class, "claws_account", "account.sql");
        business.dumpDB(PayRecord.class, "claws_payrecord", "payrecord.sql");
        long end = System.currentTimeMillis();
        System.out.println("总计耗时：" + (end - start) + "，当前时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end));
    }
}
