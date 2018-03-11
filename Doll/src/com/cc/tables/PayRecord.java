package com.cc.tables;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PayRecord {
    String table_name = "pay_record";

    long id;
    Timestamp created_time;
    Timestamp updated_time;
    int pay_type;
    BigDecimal price;
    int amount;
    int status;
    int account_id;
}
