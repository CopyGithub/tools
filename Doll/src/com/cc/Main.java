package com.cc;

import com.cc.tables.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, InstantiationException, IOException, SQLException, NoSuchFieldException {
        long start = System.currentTimeMillis();
        convertData();
        long end = System.currentTimeMillis();
        System.out.println("总计耗时：" + (end - start) + "，当前时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end));
    }

    private static void convertData() throws IllegalAccessException, IOException, NoSuchFieldException, SQLException, InstantiationException, ClassNotFoundException {
        Business business = new Business();
        business.dumpDB(Account.class, "claws_account", "account.sql");
        business.dumpDB(PayRecord.class, "claws_payrecord", "pay_record.sql");
        business.dumpDB(CatchRecord.class, "claws_clawrecord", "catch_record.sql");
        business.dumpDB(CreditRecord.class, "claws_creditrecord", "credit_record.sql");
        business.dumpDB(Transaction.class, "claws_transaction", "transaction.sql");
        business.dumpDB(CreditProduct.class, "claws_creditproduct", "credit_product.sql");
        business.dumpDB(Doll.class, "claws_doll", "doll.sql");
        business.dumpDB(ShipOrder.class, "claws_shiporder", "ship_order.sql");
        business.dumpDB(DailyStats.class, "claws_dailystats", "daily_stats.sql");
    }

    private static void kunsun() throws IllegalAccessException, NoSuchFieldException, InstantiationException, IOException, SQLException, ClassNotFoundException {
        Business business = new Business();
        business.readPay();
    }
}
