package com.cc.tables;

import java.sql.Timestamp;

public class CreditRecord {
    String table_name = "credit_record";

    long id;
    Timestamp created_time;
    Timestamp updated_time;
    int r_type;
    String title;
    int amount;
    int balance;
    int doll_id;
    int product_id;
    int account_id;
}
